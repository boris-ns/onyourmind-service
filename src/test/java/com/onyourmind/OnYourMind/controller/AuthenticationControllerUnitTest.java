package com.onyourmind.OnYourMind.controller;

import com.onyourmind.OnYourMind.dto.UserDTO;
import com.onyourmind.OnYourMind.security.auth.JwtAuthenticationRequest;
import com.onyourmind.OnYourMind.service.impl.CustomUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerUnitTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CustomUserDetailsService userService;

    @Before
    public void setUp() {
        UserDTO user = new UserDTO();
        user.setUsername("alex");
        Mockito.when(userService.login(Mockito.anyObject())).thenReturn(user);
    }

    @Test
    public void loginSuccessfull() {
        JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest("alex", "123");
        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/auth/login", loginDto, UserDTO.class);
        UserDTO responseUser = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseUser);
        assertEquals("alex", responseUser.getUsername());
    }
}
