package com.onyourmind.OnYourMind.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
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

}
