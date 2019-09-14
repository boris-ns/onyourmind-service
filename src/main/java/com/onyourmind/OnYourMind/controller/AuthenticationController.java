package com.onyourmind.OnYourMind.controller;

import com.onyourmind.OnYourMind.dto.UserDTO;
import com.onyourmind.OnYourMind.exception.ApiRequestException;
import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.model.UserTokenState;
import com.onyourmind.OnYourMind.security.TokenUtils;
import com.onyourmind.OnYourMind.security.auth.JwtAuthenticationRequest;
import com.onyourmind.OnYourMind.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                             HttpServletResponse response)
            throws AuthenticationException {

        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ApiRequestException("Credentials are not valid!");
        }

        // Insert username and password into context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create token
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        UserDTO userDto = new UserDTO(user);
        userDto.setToken(new UserTokenState(jwt, expiresIn));

        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {
        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userDetailsService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();
            UserTokenState newToken = new UserTokenState(refreshedToken, expiresIn);

            return new ResponseEntity<>(newToken, HttpStatus.OK);
        } else {
            UserTokenState emptyToken = new UserTokenState();
            return new ResponseEntity<>(emptyToken, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasRole('ROLE_USER')") // @NOTE: Maybe you will need to change this
    public ResponseEntity changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        return ResponseEntity.ok().build();
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }
}
