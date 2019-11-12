package com.onyourmind.OnYourMind.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PasswordChanger {

    private String oldPassword;
    private String newPassword;
}
