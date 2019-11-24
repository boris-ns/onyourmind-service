package com.onyourmind.OnYourMind.repository;

import com.onyourmind.OnYourMind.constants.UserConstants;
import com.onyourmind.OnYourMind.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByUsernameReturnUser() {
        User foundUser = userRepository.findByUsername(UserConstants.DB_USER_USERNAME);
        assertEquals(UserConstants.DB_USER_USERNAME, foundUser.getUsername());
    }

    @Test
    public void whenFindByUsernameReturnNull() {
        User foundUser = userRepository.findByUsername(UserConstants.DB_USER_NON_EXISTING);
        assertNull(foundUser);
    }
}
