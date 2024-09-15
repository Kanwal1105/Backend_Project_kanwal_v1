package com.scaler.ekart.dtos.security;

import com.scaler.ekart.models.Roles;
import com.scaler.ekart.models.Users;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private String email;
    private Set<Roles> roles = new HashSet<>();

    public static UserDto from(Users user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}