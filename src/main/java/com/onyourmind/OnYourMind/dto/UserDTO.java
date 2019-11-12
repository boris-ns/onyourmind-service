package com.onyourmind.OnYourMind.dto;

import com.onyourmind.OnYourMind.model.Authority;
import com.onyourmind.OnYourMind.model.User;
import com.onyourmind.OnYourMind.model.UserTokenState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private List<String> authorities;
    private UserTokenState token;
    private String profileImagePath;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.token = null;
        this.profileImagePath = user.getProfileImagePath();

        this.authorities = user.getAuthorities().stream()
                .map(authority -> ((Authority) authority).getName()).collect(Collectors.toList());
    }
}
