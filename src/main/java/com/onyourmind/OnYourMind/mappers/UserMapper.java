package com.onyourmind.OnYourMind.mappers;

import com.onyourmind.OnYourMind.dto.UserDTO;
import com.onyourmind.OnYourMind.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
    }

    public static UserDTO toDto(User user) {
        return new UserDTO(user);
    }

    public static List<UserDTO> toListDto(List<User> users) {
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }
}
