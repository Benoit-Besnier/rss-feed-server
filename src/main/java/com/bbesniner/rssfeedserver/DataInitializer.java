package com.bbesniner.rssfeedserver;

import com.bbesniner.rssfeedserver.entities.hibernate.Feed;
import com.bbesniner.rssfeedserver.entities.hibernate.User;
import com.bbesniner.rssfeedserver.repositories.FeedRepository;
import com.bbesniner.rssfeedserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final FeedRepository feedRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(final String... args) {

        log.debug("initializing feeds data...");

        this.feedRepository.save(Feed.builder()
                .id(10000L)
                .title("Test_premier")
                .link("lien-premier")
                .description("le premier test")
                .build());

        this.feedRepository.save(Feed.builder()
                .id(20000L)
                .title("Test-2")
                .link("go-to-2")
                .description("Et ça c est le second")
                .build());

        log.debug("printing all feeds...");
        this.feedRepository.findAll().forEach(feed -> log.debug(" Feed :" + feed.toString()));

        log.debug("initializing users data...");

        this.userRepository.save(User.builder()
                .username("user")
                .password(this.passwordEncoder.encode("password"))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        this.userRepository.save(User.builder()
                .username("admin")
                .password(this.passwordEncoder.encode("password"))
                .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                .build());

        log.debug("printing all userRepository...");
        this.userRepository.findAll().forEach(user -> log.debug(" User :" + user.toString()));
    }
}