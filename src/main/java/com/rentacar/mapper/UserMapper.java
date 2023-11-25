package com.rentacar.mapper;

import com.rentacar.domain.Role;
import com.rentacar.domain.User;
import com.rentacar.dto.request.RegisterRequestDTO;

import java.util.Set;

public class UserMapper {
    public static User toEntity(RegisterRequestDTO registerRequestDTO, String encodedPassword, Set<Role> roleSet) {
        User user = new User();

        user.setEmail(registerRequestDTO.getEmail());
        user.setFirstName(registerRequestDTO.getFirstName());
        user.setLastName(registerRequestDTO.getLastName());
        user.setPassword(encodedPassword);
        user.setPhoneNumber(registerRequestDTO.getPhoneNumber());
        user.setAddress(registerRequestDTO.getAddress());
        user.setZipCode(registerRequestDTO.getZipCode());
        user.setRoles(roleSet);

        return user;
    }
}
