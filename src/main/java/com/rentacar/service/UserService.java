package com.rentacar.service;

import com.rentacar.domain.Role;
import com.rentacar.domain.User;
import com.rentacar.domain.enums.RoleType;
import com.rentacar.dto.request.RegisterRequestDTO;
import com.rentacar.exception.ConflictException;
import com.rentacar.exception.ResourceNotFoundException;
import com.rentacar.exception.message.ErrorMessage;
import com.rentacar.mapper.UserMapper;
import com.rentacar.repository.RoleRepository;
import com.rentacar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user based on the provided registration request DTO.
     * <p>
     * This method checks if a user with the specified email already exists in the database.
     * If a user with the email exists, a ConflictException is thrown.
     * If the email is unique, the provided password is encoded, and a new user entity is created
     * with the encoded password and the default role of ROLE_CUSTOMER. The user is then saved to the database.
     *
     * @param registerRequestDTO The RegisterRequestDTO containing information for user registration.
     * @throws ConflictException         If a user with the specified email already exists in the database.
     * @throws ResourceNotFoundException If the default role ROLE_CUSTOMER is not found in the database.
     */
    public void register(RegisterRequestDTO registerRequestDTO) {
        if (this.userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, registerRequestDTO.getEmail()));
        }

        String encodedPassword = this.passwordEncoder.encode(registerRequestDTO.getPassword());

        Role role = this.roleRepository.findByName(RoleType.ROLE_CUSTOMER).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, RoleType.ROLE_CUSTOMER.name())));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        User user = UserMapper.toEntity(registerRequestDTO, encodedPassword, roleSet);

        this.userRepository.save(user);
    }
}
