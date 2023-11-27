package com.rentacar.service;

import com.rentacar.domain.User;
import com.rentacar.dto.response.UserResponseDTO;
import com.rentacar.exception.ResourceNotFoundException;
import com.rentacar.exception.message.ErrorMessage;
import com.rentacar.mapper.UserMapper;
import com.rentacar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Retrieves a list of all users from the database.
     * <p>
     * This method fetches all user records stored in the database
     * using the userRepository. The retrieved users are then mapped
     * to a list of UserResponseDTO objects before being returned.
     *
     * @return List<UserResponseDTO> A list of UserResponseDTO objects
     * representing all users in the database.
     */
    public List<UserResponseDTO> getAll() {
        log.info("Fetching all users from the database.");

        List<User> userList = this.userRepository.findAll();

        return UserMapper.toDTOList(userList);
    }

    /**
     * Retrieves a specific user by their unique identifier from the database.
     * <p>
     * This method attempts to find a user in the database with the specified ID.
     * If the user is found, it is mapped to a UserResponseDTO and returned.
     * If the user is not found, a ResourceNotFoundException is thrown.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return UserResponseDTO A UserResponseDTO object representing the retrieved user.
     * @throws ResourceNotFoundException If the user with the specified ID is not found in the database.
     */
    public UserResponseDTO getById(Long id) {
        log.info("Fetching user with ID: {} the database", id);

        User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        return UserMapper.toDTO(user);
    }
}
