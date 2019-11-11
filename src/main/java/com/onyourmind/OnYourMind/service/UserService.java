package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.dto.UserDTO;
import com.onyourmind.OnYourMind.dto.UserRegistrationDTO;
import com.onyourmind.OnYourMind.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
    User addRegularUser(UserRegistrationDTO userInfo);
    User addAdminUser(UserRegistrationDTO userInfo);
    void changeUserEnabledStatus(Long id, boolean status);
    User editUser(UserDTO user);
    void setProfileImage(String imagePath);
    void verifyUserAccount(String token);
}
