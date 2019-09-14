package com.onyourmind.OnYourMind.service;

import com.onyourmind.OnYourMind.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
}
