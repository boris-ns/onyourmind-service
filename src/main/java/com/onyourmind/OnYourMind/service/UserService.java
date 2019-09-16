package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.dto.UserDTO;
import com.onyourmind.OnYourMind.dto.UserRegistrationDTO;
import com.onyourmind.OnYourMind.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);
    User findByUsername(String username);
    List<UserDTO> findAll();
    UserDTO addRegularUser(UserRegistrationDTO userInfo);
    UserDTO addAdminUser(UserRegistrationDTO userInfo);
    void changeUserEnabledStatus(Long id, boolean status);
}
