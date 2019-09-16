package com.onyourmind.OnYourMind.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotNull(message = "Username must be provided")
    private String username;

    @NotNull(message = "First name must be provided")
    private String firstName;

    @NotNull(message = "Last name must be provided")
    private String lastName;

    @NotNull(message = "Password must be provided")
    @Size(min = 3, message = "Password must be at least 3 characters long")
    private String password;

    @NotNull(message = "Email must be provided")
    @Email
    private String email;


    public UserRegistrationDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
