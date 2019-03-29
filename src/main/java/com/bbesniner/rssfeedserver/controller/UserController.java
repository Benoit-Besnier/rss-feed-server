package com.bbesniner.rssfeedserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(UserController.PATH)
@RequiredArgsConstructor
public class UserController {

    static final String PATH = "/users";

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        final Map<Object, Object> userInformation = new HashMap<>();

        userInformation.put("username", userDetails.getUsername());
        userInformation.put("roles", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
        );
        return ResponseEntity.ok(userInformation);
    }

}
