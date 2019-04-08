package com.bbesniner.rssfeedserver.services;

import com.bbesniner.rssfeedserver.entities.exceptions.CreateConflictException;
import com.bbesniner.rssfeedserver.entities.exceptions.ResourceNotFound;
import com.bbesniner.rssfeedserver.entities.hibernate.User;
import com.bbesniner.rssfeedserver.entities.requestbody.UserCredentials;
import com.bbesniner.rssfeedserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public List<User> findAllUsers() { return this.userRepository.findAll(); }

    public User findByUsername(final String username) throws ResourceNotFound {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFound(username, User.class));
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws ResourceNotFound {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFound(username, User.class));
    }

    public User createUser(final UserCredentials userCredentials) throws CreateConflictException {
        final User user = User.builder()
                .username(userCredentials.getUsername())
                .password(this.passwordEncoder.encode(userCredentials.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        if (!this.userRepository.findByUsername(user.getUsername()).isPresent()) {
            return this.userRepository.save(user);
        } else {
            throw new CreateConflictException("Cannot create " + user.getUsername() + " because it already exist.");
        }
    }

    public void updatePreferredFeeds(final String username, final List<String> preferredFeeds) {
        final User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFound(username, User.class));

        user.setPreferredFeedUuid(preferredFeeds);
        this.userRepository.save(user);
    }
}
