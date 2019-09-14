package com.onyourmind.OnYourMind.service.impl;

import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.repository.UserRepository;
import com.onyourmind.OnYourMind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) throws AccessDeniedException {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() throws AccessDeniedException {
        return userRepository.findAll();
    }
}
