package com.onyourmind.OnYourMind.dto;

import com.onyourmind.OnYourMind.model.Authority;
import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.model.UserTokenState;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private List<String> authorities;
    private UserTokenState token;


    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.token = null;

        this.authorities = user.getAuthorities().stream()
                .map(authority -> ((Authority) authority).getName()).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public UserTokenState getToken() {
        return token;
    }

    public void setToken(UserTokenState token) {
        this.token = token;
    }
}
