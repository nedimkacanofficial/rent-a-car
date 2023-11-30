package com.rentacar.security.service;

import com.rentacar.domain.User;
import com.rentacar.exception.message.ErrorMessage;
import com.rentacar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.security.service
 * @since 28/11/2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Loads a user by their username (email) from the database.
     * <p>
     * This method is part of the Spring Security UserDetailsServiceImpl interface.
     * It retrieves a user from the database based on the provided email.
     * If the user is found, a UserDetailsImpl is created and returned.
     * If the user is not found, a UsernameNotFoundException is thrown.
     *
     * @param email The email (username) of the user to load.
     * @return UserDetailsImpl A UserDetailsImpl object representing the loaded user.
     * @throws UsernameNotFoundException If the user with the specified email is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Attempting to load user by email: {}", email);

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("User with email {} not found in the database.", email);
            return new UsernameNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, email));
        });

        UserDetails userDetails = UserDetailsImpl.build(user);

        log.info("User with email {} successfully loaded.", email);

        return userDetails;
    }
}
