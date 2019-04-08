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
                .uuid("fe8e5858-ab8b-42bb-ba66-d109296d2654")
                .sourceFeedUrl("http://www.leparisien.fr/actualites-a-la-une.rss.xml")
                .link("http://www.leparisien.fr")
                .title("Le Parisien - l'actu")
                .description("Le feed du 'Le Parisien' comme example.")
                .build());

        this.feedRepository.save(Feed.builder()
                .uuid("fb55615d-3c7c-472c-80b9-d7ecba8fb0e4")
                .sourceFeedUrl("http://feeds.bbci.co.uk/news/world/rss.xml")
                .link("https://www.bbc.co.uk/news/")
                .title("BBC News - World")
                .description("Un feed de la 'BBC News' sur leur channel 'World'")
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