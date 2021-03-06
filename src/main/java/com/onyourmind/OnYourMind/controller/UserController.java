package com.onyourmind.OnYourMind.controller;

import com.onyourmind.OnYourMind.dto.UserDTO;
import com.onyourmind.OnYourMind.dto.UserRegistrationDTO;
import com.onyourmind.OnYourMind.mappers.UserMapper;
import com.onyourmind.OnYourMind.model.User;
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

    @GetMapping("/public/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(UserMapper.toListDto(users), HttpStatus.OK);
    }

    @PostMapping("/public/add-user")
    public ResponseEntity<UserDTO> addRegularUser(@Valid @RequestBody UserRegistrationDTO userInfo) {
        User user = userService.addRegularUser(userInfo);
        return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO user) {
        User newUserInfo = userService.editUser(user);
        return new ResponseEntity<>(UserMapper.toDto(newUserInfo), HttpStatus.OK);
    }

    @PostMapping("/add-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> addAdminUser(@RequestBody UserRegistrationDTO userInfo) {
        User user = userService.addAdminUser(userInfo);
        return new ResponseEntity<>(UserMapper.toDto(user), HttpStatus.OK);
    }

    @GetMapping("/public/verify/{token}")
    public void verifyAccount(@PathVariable String token) {
        userService.verifyUserAccount(token);
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
