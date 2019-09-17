package com.onyourmind.OnYourMind.service.impl;

import com.onyourmind.OnYourMind.common.TimeProvider;
import com.onyourmind.OnYourMind.common.consts.UserRoles;
import com.onyourmind.OnYourMind.dto.UserDTO;
import com.onyourmind.OnYourMind.dto.UserRegistrationDTO;
import com.onyourmind.OnYourMind.exception.ApiRequestException;
import com.onyourmind.OnYourMind.exception.ResourceNotFoundException;
import com.onyourmind.OnYourMind.model.Authority;
import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.repository.AuthorityRepository;
import com.onyourmind.OnYourMind.repository.UserRepository;
import com.onyourmind.OnYourMind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TimeProvider timeProvider;


    @Override
    public UserDTO findById(Long id) {
        return new UserDTO(getUserFromRepository(id));
    }

    @Override
    public UserDTO findByUsername(String username) throws ApiRequestException {
        User user = userRepository.findByUsername(username);

        if (user == null)
            throw new ApiRequestException("User with username '" + username + "' doesn't exist.");

        return new UserDTO(user);
    }

    @Override
    public List<UserDTO> findAll() throws AccessDeniedException {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user)).collect(Collectors.toList());
    }

    @Override
    public UserDTO addRegularUser(UserRegistrationDTO userInfo) {
        User user = this.createNewUserObject(userInfo, UserRoles.ROLE_USER);
        userRepository.save(user);

        return new UserDTO(user);
    }

    @Override
    public UserDTO addAdminUser(UserRegistrationDTO userInfo) {
        User user = this.createNewUserObject(userInfo, UserRoles.ROLE_ADMIN);
        userRepository.save(user);

        return new UserDTO(user);
    }

    private User createNewUserObject(UserRegistrationDTO userInfo, String roleName) throws ApiRequestException {
        if (userRepository.findByUsername(userInfo.getUsername()) != null)
            throw new ApiRequestException("Username '" + userInfo.getUsername() + "' is taken.");

        User user = new User();
        user.setUsername(userInfo.getUsername());
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setEmail(userInfo.getEmail());
        user.setEnabled(true);
        user.setLastPasswordResetDate(timeProvider.nowTimestamp());

        Authority regularUserAuthority = authorityRepository.findByName(roleName);
        user.getUsersAuthorities().add(regularUserAuthority);

        return user;
    }

    @Override
    public void changeUserEnabledStatus(Long id, boolean status) {
        User user = this.getUserFromRepository(id);
        user.setEnabled(status);
        userRepository.save(user);
    }

    public User getUserFromRepository(Long id) throws ResourceNotFoundException {
        try {
            User user = userRepository.findById(id).get();
            return user;
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("User with ID " + id + " doesn't exist");
        }
    }
}
