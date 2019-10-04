package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.dto.UserDTO;
import com.onyourmind.OnYourMind.dto.UserRegistrationDTO;

import java.util.List;

public interface UserService {

    UserDTO findById(Long id);
    UserDTO findByUsername(String username);
    List<UserDTO> findAll();
    UserDTO addRegularUser(UserRegistrationDTO userInfo);
    UserDTO addAdminUser(UserRegistrationDTO userInfo);
    void changeUserEnabledStatus(Long id, boolean status);
    UserDTO editUser(UserDTO user);
}
