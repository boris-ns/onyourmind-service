package com.onyourmind.OnYourMind.controller;

import com.onyourmind.OnYourMind.dto.UserDTO;
import com.onyourmind.OnYourMind.dto.UserRegistrationDTO;
import com.onyourmind.OnYourMind.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/public/add-user")
    public ResponseEntity<UserDTO> addRegularUser(@Valid @RequestBody UserRegistrationDTO userInfo) {
        UserDTO user = userService.addRegularUser(userInfo);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> addAdminUser(@RequestBody UserRegistrationDTO userInfo) {
        UserDTO user = userService.addAdminUser(userInfo);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void activateUser(@PathVariable Long id) {
        userService.changeUserEnabledStatus(id, true);
    }

    @PutMapping("/deactivate/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deactivateUser(@PathVariable Long id) {
        userService.changeUserEnabledStatus(id, false);
    }
}
