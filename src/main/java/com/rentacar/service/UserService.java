package com.rentacar.service;

import com.rentacar.domain.User;
import com.rentacar.dto.request.UpdatePasswordRequestDTO;
import com.rentacar.dto.response.UserResponseDTO;
import com.rentacar.exception.BadRequestException;
import com.rentacar.exception.ResourceNotFoundException;
import com.rentacar.exception.message.ErrorMessage;
import com.rentacar.mapper.UserMapper;
import com.rentacar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description of UpdatePasswordRequestDTO.
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.service
 * @since 28/11/2023
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    /**
     * Updates the password for a user identified by the given ID.
     * <p>
     * This method retrieves the user from the repository based on the provided ID,
     * validates the old password, checks for built-in users, and updates the password
     * if all conditions are met.
     *
     * @param id                       The unique identifier of the user whose password is to be updated.
     * @param updatePasswordRequestDTO The request body containing the old and new password information.
     * @throws ResourceNotFoundException If no user is found with the provided ID.
     * @throws BadRequestException       If the old password does not match the user's current password,
     *                                   or if the operation is not permitted for built-in users.
     * @throws RuntimeException          If an unexpected error occurs during the password update process.
     * @apiNote This method is typically called to update a user's password. It first checks the existence
     * of the user based on the provided ID. If the user is a built-in user, the password update
     * operation is not permitted. The old password is then verified, and if successful, the user's
     * password is updated and saved to the repository after hashing.
     * @see User
     * @see UpdatePasswordRequestDTO
     * @see ResourceNotFoundException
     * @see BadRequestException
     * @see UserRepository
     * @see BCrypt
     * @see PasswordEncoder
     * @since 1.0
     */
    public void updatePassword(Long id, UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        log.info("Updating password for user with ID: {} the database", id);

        User user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        if (!BCrypt.hashpw(updatePasswordRequestDTO.getOldPassword(), user.getPassword()).equals(user.getPassword())) {
            throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED);
        }

        String hashedPassword = this.passwordEncoder.encode(updatePasswordRequestDTO.getNewPassword());

        user.setPassword(hashedPassword);

        this.userRepository.save(user);
    }

    /**
     * Retrieves a paginated list of user information from the database.
     * <p>
     * This method fetches a page of users from the repository based on the provided pageable object,
     * converts the resulting Page<User> to a Page<UserResponseDTO> using the UserMapper, and returns
     * the paginated list of user information.
     *
     * @param pageable The Pageable object specifying the pagination parameters (e.g., page number,
     *                 page size, sorting).
     * @return Page<UserResponseDTO> A Page containing UserResponseDTO objects representing the paginated
     * list of user information.
     * @throws RuntimeException If an unexpected error occurs during the retrieval process.
     * @apiNote This method is typically used to retrieve a paginated list of user information from the
     * database. It logs information about the operation and delegates the mapping of User entities
     * to UserResponseDTO objects to the UserMapper class.
     * @see UserResponseDTO
     * @see User
     * @see Pageable
     * @see Page
     * @see UserMapper
     * @see UserRepository
     * @since 1.0
     */
    public Page<UserResponseDTO> getAllWithPage(Pageable pageable) {
        log.info("Fetching a paginated list of user from the database.");

        Page<User> page = this.userRepository.findAll(pageable);

        return page.map(UserMapper::toDTO);
    }
}
