package com.onyourmind.OnYourMind.service.impl;

import com.onyourmind.OnYourMind.common.TimeProvider;
import com.onyourmind.OnYourMind.common.UserHelper;
import com.onyourmind.OnYourMind.common.consts.UserRoles;
import com.onyourmind.OnYourMind.dto.UserDTO;
import com.onyourmind.OnYourMind.dto.UserRegistrationDTO;
import com.onyourmind.OnYourMind.exception.ApiRequestException;
import com.onyourmind.OnYourMind.exception.ResourceNotFoundException;
import com.onyourmind.OnYourMind.exception.UserNotFoundException;
import com.onyourmind.OnYourMind.model.Authority;
import com.onyourmind.OnYourMind.model.ConfirmationToken;
import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.repository.AuthorityRepository;
import com.onyourmind.OnYourMind.repository.ConfirmationTokenRepository;
import com.onyourmind.OnYourMind.repository.UserRepository;
import com.onyourmind.OnYourMind.service.UserService;
import com.onyourmind.OnYourMind.service.email.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private MailSenderService mailSenderService;

    @Value("${user.default-profile-image}")
    private String defaultProfileImage;


    @Override
    public User findById(Long id) {
        try {
            User user = userRepository.findById(id).get();
            return user;
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException("User with ID " + id + " doesn't exist");
        }
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null)
            throw new UserNotFoundException("User with username '" + username + "' doesn't exist.");

        return user;
    }

    @Override
    public List<User> findAll() throws AccessDeniedException {
        return userRepository.findAll();
    }

    @Override
    public User addRegularUser(UserRegistrationDTO userInfo) {
        User user = this.createNewUserObject(userInfo, UserRoles.ROLE_USER);
        userRepository.save(user);

        ConfirmationToken token = this.createConfirmationToken(user);
        mailSenderService.sendMailForRegistration(user, token);

        return user;
    }

    @Override
    public User addAdminUser(UserRegistrationDTO userInfo) {
        User user = this.createNewUserObject(userInfo, UserRoles.ROLE_ADMIN);
        userRepository.save(user);

        ConfirmationToken token = this.createConfirmationToken(user);
        mailSenderService.sendMailForRegistration(user, token);

        return user;
    }

    private ConfirmationToken createConfirmationToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        return confirmationTokenRepository.save(confirmationToken);
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
        user.setEnabled(false);
        user.setLastPasswordResetDate(timeProvider.nowTimestamp());
        user.setProfileImagePath(defaultProfileImage);

        Authority regularUserAuthority = authorityRepository.findByName(roleName);
        user.getUsersAuthorities().add(regularUserAuthority);

        return user;
    }

    @Override
    public void verifyUserAccount(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);

        if (confirmationToken == null) {
            throw new ResourceNotFoundException("Requested token doesn't exist.");
        }

        User user = confirmationToken.getUser();
        Date now = timeProvider.now();
        long timeDifference = now.getTime() - confirmationToken.getCreatedDatetime().getTime();
        long timeDifferenceMinutes = timeDifference / (60 * 1000);

        if (timeDifferenceMinutes < 15) {
            user.setEnabled(true);
            userRepository.save(user);
        } else {
            confirmationTokenRepository.delete(confirmationToken);
            userRepository.delete(user);
            throw new ApiRequestException("Confirmation token timed out.");
        }
    }

    @Override
    public void changeUserEnabledStatus(Long id, boolean status) {
        User user = this.findById(id);
        user.setEnabled(status);
        userRepository.save(user);
    }

    @Override
    public User editUser(UserDTO user) {
        User userInfo = userHelper.getCurrentUser();

        userInfo.setFirstName(user.getFirstName());
        userInfo.setLastName(user.getLastName());
        userInfo.setEmail(user.getEmail());

        return userInfo;
    }

    @Override
    public void setProfileImage(String imagePath) {
        User user = userHelper.getCurrentUser();
        user.setProfileImagePath(imagePath);
        userRepository.save(user);
    }
}
