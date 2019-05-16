package com.bbesniner.rssfeedserver.controller;

import com.bbesniner.rssfeedserver.entities.exceptions.ResourceNotFound;
import com.bbesniner.rssfeedserver.entities.hibernate.Feed;
import com.bbesniner.rssfeedserver.entities.hibernate.User;
import com.bbesniner.rssfeedserver.entities.requestbody.PreferredFeeds;
import com.bbesniner.rssfeedserver.services.FeedService;
import com.bbesniner.rssfeedserver.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@RequestMapping(UserController.PATH)
@RequiredArgsConstructor
public class UserController {

    static final String PATH = "/users";

    private final UserService userService;

    private final FeedService feedService;

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal final UserDetails userDetails) throws ResourceNotFound {
        final Map<Object, Object> userInformation = new HashMap<>();
        log.info("[DEBUG] - GET /users/me received {}", userDetails);
        final User user = this.userService.findByUsername(userDetails.getUsername());

        userInformation.put("username", userDetails.getUsername());
        userInformation.put("roles", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
        );
        userInformation.put("preferredFeeds", user.getPreferredFeedUuid());

        return ResponseEntity.ok(userInformation);
    }

    @PutMapping("/me/feeds")
    public ResponseEntity setPreferredFeed(@AuthenticationPrincipal final UserDetails userDetails,
                                           @RequestBody final PreferredFeeds preferredFeeds) throws ResourceNotFound {
        List<String> feedsUuid = this.feedService.findAll().stream()
                .map(Feed::getUuid)
                .filter(uuid -> preferredFeeds.getPreferredFeeds().contains(uuid))
                .collect(Collectors.toList());

        this.userService.updatePreferredFeeds(userDetails.getUsername(), feedsUuid);
        return ResponseEntity.noContent().build();
    }

}
