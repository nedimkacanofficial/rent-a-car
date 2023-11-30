package com.rentacar.controller;

import com.rentacar.dto.request.LoginRequestDTO;
import com.rentacar.dto.request.RegisterRequestDTO;
import com.rentacar.dto.response.UserJWTResponseDTO;
import com.rentacar.dto.response.default_response.DefaultResponseDTO;
import com.rentacar.dto.response.default_response.ResponseMessage;
import com.rentacar.security.jwt.JwtUtilsService;
import com.rentacar.service.UserJWTService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.controller
 * @since 28/11/2023
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User JWT Controller")
public class UserJWTController {
    private final UserJWTService userService;

    private final AuthenticationProvider authenticationProvider;

    private final JwtUtilsService jwtUtilsService;

    /**
     * Registers a new user.
     * <p>
     * This endpoint is designed to handle the registration of a new user based on the provided
     * RegisterRequestDTO. The request body should contain valid information about the new user.
     * If the registration is successful, a response with a DefaultResponseDTO indicating success is returned.
     *
     * @param registerRequestDTO The RegisterRequestDTO containing information about the new user.
     *                           It should be a valid and well-formed JSON representing the user's registration data.
     * @return ResponseEntity<DefaultResponseDTO> A ResponseEntity containing a DefaultResponseDTO indicating
     * the success of the registration operation. HttpStatus.CREATED is returned for a successful response.
     * If the input data is invalid or the registration fails, HttpStatus.BAD_REQUEST is returned.
     */
    @PostMapping("/register")
    public ResponseEntity<DefaultResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        log.info("Register method called for user with email: {}", registerRequestDTO.getEmail());

        this.userService.register(registerRequestDTO);

        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO(true, ResponseMessage.CREATED_SUCCESS_RESPONSE_MESSAGE);

        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Authenticates a user and generates a JWT token upon successful login.
     * <p>
     * This endpoint is designed to authenticate a user based on the provided LoginRequestDTO.
     * If the authentication is successful, a JWT token is generated and returned in a LoginResponseDTO.
     *
     * @param loginRequestDTO The LoginRequestDTO containing the user's email and password.
     *                        It should be a valid and well-formed JSON representing the user's login credentials.
     * @return ResponseEntity<LoginResponseDTO> A ResponseEntity containing a LoginResponseDTO with the generated JWT token,
     * indicating the success of the login operation. HttpStatus.OK is returned for a successful response.
     * If the login credentials are invalid or the authentication fails, HttpStatus.UNAUTHORIZED is returned.
     */
    @PostMapping("/login")
    public ResponseEntity<UserJWTResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        log.info("Login method called for user with email: {}", loginRequestDTO.getEmail());

        Authentication authentication = this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

        String token = this.jwtUtilsService.generateJwtToken(authentication);

        UserJWTResponseDTO loginResponseDTO = new UserJWTResponseDTO(token);

        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }
}
