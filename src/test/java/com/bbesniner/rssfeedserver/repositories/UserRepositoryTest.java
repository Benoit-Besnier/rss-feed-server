package com.bbesniner.rssfeedserver.repositories;

import com.bbesniner.rssfeedserver.entities.hibernate.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void assertThatRepositoryProvideCorrectValues() {
        final User user = User.builder().username("Jacquie").password("Michel").build();
        final User savedUser = this.userRepository.save(user);
        final User foundUser = this.userRepository.getOne(savedUser.getId());

        assertEquals(foundUser.getUsername(), user.getUsername());
        assertEquals(foundUser.getPassword(), foundUser.getPassword());
        assertNotNull(foundUser.getId());
        assertTrue(foundUser.getId() > 0);
        assertEquals(foundUser.getId(), savedUser.getId());
    }

}