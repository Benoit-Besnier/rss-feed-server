package com.bbesniner.rssfeedserver.config;

import com.bbesniner.rssfeedserver.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600 * 60 * 60; // 1h

    private final UserService userService;

    @PostConstruct
    public void initialization() {
        this.secretKey = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
    }



}
