package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.exception.UserNotFoundException;
import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepositoryMocked;

    @Before
    public void setUp() {
        User user = new User();
        user.setUsername("alex");
        user.setId(1L);
        Mockito.when(userRepositoryMocked.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepositoryMocked.findByUsername("alex")).thenReturn(user);
    }

    @Test
    public void whenFindByIdReturnUser() {
        Long requestedId = 1L;
        User user = userService.findById(requestedId);
        assertEquals(requestedId, user.getId());
    }

    @Test(expected = UserNotFoundException.class)
    public void whenFindByIdThrowException() {
        User user = userService.findById(12345L);
    }

    @Test
    public void whenFindByUsernameReturnUser() {
        User user = userService.findByUsername("alex");
        assertEquals("alex", user.getUsername());
    }

    @Test(expected = UserNotFoundException.class)
    public void whenFindByUsernameThrowException() {
        User user = userService.findByUsername("this one doesn't exist");
    }
}
