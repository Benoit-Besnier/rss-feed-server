package com.bbesniner.rssfeedserver.controller;

import com.bbesniner.rssfeedserver.entities.exceptions.CreateConflictException;
import com.bbesniner.rssfeedserver.entities.exceptions.ResourceNotFound;
import com.bbesniner.rssfeedserver.entities.hibernate.User;
import com.bbesniner.rssfeedserver.entities.requestbody.UserCredentials;
import com.bbesniner.rssfeedserver.security.jwt.JwtTokenProvider;
import com.bbesniner.rssfeedserver.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(AuthController.PATH)
@RequiredArgsConstructor
public class AuthController {

    static final String PATH = "/auth";

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody final UserCredentials data) throws ResourceNotFound {
        final Map<String, String> model = new HashMap<>();
        log.info("[DEBUG] - POST /auth/signin received {}", data);

        try {
            final String username = data.getUsername();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            final String token = jwtTokenProvider.createToken(
                    username, this.userService.findByUsername(username).getRoles());

            model.put("username", username);
            model.put("token", token);
            log.info("[DEBUG] - POST /auth/signin result to {}", model);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody final UserCredentials userCredentials,
                                     final HttpServletRequest request)
            throws CreateConflictException, URISyntaxException {
        this.userService.createUser(userCredentials);
        return ResponseEntity.created(URI.create(AuthController.PATH + "/register")).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(this.userService.findAllUsers());
    }
}
