package com.bbesniner.rssfeedserver.services;

import com.bbesniner.rssfeedserver.entities.exceptions.CreateConflictException;
import com.bbesniner.rssfeedserver.entities.hibernate.User;
import com.bbesniner.rssfeedserver.entities.requestbody.AuthenticationRequest;
import com.bbesniner.rssfeedserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public Optional<User> findByUsername(final String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User <" + username + "> cannot be found"));
    }

    public User createUser(final AuthenticationRequest authenticationRequest) throws CreateConflictException {
        final User user = User.builder()
                .username(authenticationRequest.getUsername())
                .password(authenticationRequest.getPassword())
                .build();

        if (!this.userRepository.findByUsername(user.getUsername()).isPresent()) {
            return this.userRepository.save(user);
        } else {
            throw new CreateConflictException("Cannot create " + user.getUsername() + " because it already exist.");
        }
    }
}
