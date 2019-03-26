package com.bbesniner.rssfeedserver.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody AuthenticationRequest request) {
        // Do something...
        return ResponseEntity.ok().build();
    }

}
