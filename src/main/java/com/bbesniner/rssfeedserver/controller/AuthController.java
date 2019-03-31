package com.bbesniner.rssfeedserver.controller;

import com.bbesniner.rssfeedserver.entities.exceptions.CreateConflictException;
import com.bbesniner.rssfeedserver.entities.hibernate.User;
import com.bbesniner.rssfeedserver.entities.requestbody.UserCredentials;
import com.bbesniner.rssfeedserver.security.jwt.JwtTokenProvider;
import com.bbesniner.rssfeedserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AuthController.PATH)
@RequiredArgsConstructor
public class AuthController {

    static final String PATH = "/auth";

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody final UserCredentials data) {
        final Map<String, String> model = new HashMap<>();

        try {
            final String username = data.getUsername();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            final String token = jwtTokenProvider.createToken(username, this.userService.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());

            model.put("username", username);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody final UserCredentials userCredentials,
                                     final HttpServletRequest request)
            throws CreateConflictException, URISyntaxException {
        final User saved = this.userService.createUser(userCredentials);
        final URI location = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path(FeedController.PATH + "/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(this.userService.findAllUsers());
    }
}
