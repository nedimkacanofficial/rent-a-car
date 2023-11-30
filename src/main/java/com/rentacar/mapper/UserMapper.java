package com.rentacar.mapper;

import com.rentacar.domain.Role;
import com.rentacar.domain.User;
import com.rentacar.dto.request.RegisterRequestDTO;
import com.rentacar.dto.response.UserResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.mapper
 * @since 28/11/2023
 */
public class UserMapper {
    public static User toEntity(RegisterRequestDTO registerRequestDTO, String encodedPassword, Set<Role> roleSet) {
        User user = new User();

        user.setFirstName(registerRequestDTO.getFirstName());
        user.setLastName(registerRequestDTO.getLastName());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(encodedPassword);
        user.setPhoneNumber(registerRequestDTO.getPhoneNumber());
        user.setAddress(registerRequestDTO.getAddress());
        user.setZipCode(registerRequestDTO.getZipCode());
        user.setRoles(roleSet);

        return user;
    }

    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhoneNumber(user.getPhoneNumber());
        userResponseDTO.setAddress(user.getAddress());
        userResponseDTO.setBuiltIn(user.getBuiltIn());
        userResponseDTO.setZipCode(user.getZipCode());
        userResponseDTO.setRoles(user.getRoles());

        return userResponseDTO;
    }

    public static List<UserResponseDTO> toDTOList(List<User> userList) {
        return userList.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }
}
