package com.bbesniner.rssfeedserver.resources;

import com.bbesniner.rssfeedserver.entities.exceptions.CreateConflictException;
import com.bbesniner.rssfeedserver.entities.hibernate.User;
import com.bbesniner.rssfeedserver.entities.requestbody.AuthenticationRequest;
import com.bbesniner.rssfeedserver.security.jwt.JwtTokenProvider;
import com.bbesniner.rssfeedserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(AuthResource.PATH)
@RequiredArgsConstructor
public class AuthResource {

    static final String PATH = "/auth";

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody AuthenticationRequest data) {
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
    public ResponseEntity createUser(@RequestBody final AuthenticationRequest request)
            throws CreateConflictException, URISyntaxException {
        final User user = this.userService.createUser(request);

        return ResponseEntity.created(new URI(UserResource.PATH + "/" + user.getId())).build();
    }
}
