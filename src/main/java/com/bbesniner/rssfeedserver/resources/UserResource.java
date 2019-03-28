package com.bbesniner.rssfeedserver.resources;

import com.bbesniner.rssfeedserver.services.UserService;
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
@RequestMapping(UserResource.PATH)
@RequiredArgsConstructor
public class UserResource {

    static final String PATH = "/users";

    private final UserService userService;

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
