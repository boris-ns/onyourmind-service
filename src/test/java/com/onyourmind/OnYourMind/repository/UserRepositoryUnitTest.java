package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUsernameReturnUser() {
        final String username = "alex";
        User user = new User();
        user.setUsername(username);
        user.setEmail("random@email.com");
        user.setProfileImagePath("");
        user.setFirstName("Alex");
        user.setLastName("Alexey");
        user.setEnabled(true);
        user.setPassword("123");

        entityManager.persist(user);
        entityManager.flush();

        User foundUser = userRepository.findByUsername(username);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    public void whenFindByUsernameReturnNull() {
        User foundUser = userRepository.findByUsername("alex");
        assertNull(foundUser);
    }
}
