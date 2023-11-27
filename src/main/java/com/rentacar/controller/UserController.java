package com.rentacar.controller;

import com.rentacar.dto.response.UserResponseDTO;
import com.rentacar.security.service.UserDetailsImpl;
import com.rentacar.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Controller")
public class UserController {
    private final UserService userService;

    /**
     * Retrieves a list of all users in the system.
     * <p>
     * This endpoint is designed to fetch all users stored in the system.
     * The response includes a list of UserResponseDTO objects representing the users.
     * Access to this endpoint is restricted to users with the 'ADMIN' role.
     *
     * @return ResponseEntity<List < UserResponseDTO>> A ResponseEntity containing a list
     * of UserResponseDTO objects, indicating the success of the operation.
     * HttpStatus.OK is returned for a successful response.
     * If the authenticated user does not have the 'ADMIN' role, HttpStatus.FORBIDDEN is returned.
     */
    @GetMapping("/auth/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        log.info("Fetching all users.");

        List<UserResponseDTO> userResponseDTOList = this.userService.getAll();

        return new ResponseEntity<>(userResponseDTOList, HttpStatus.OK);
    }

    /**
     * Retrieves information about the authenticated user based on their ID.
     * <p>
     * This endpoint is designed to fetch details about the authenticated user using their ID.
     * The response includes a UserResponseDTO object representing the user.
     * Access to this endpoint is restricted to users with either the 'ADMIN' or 'CUSTOMER' role.
     *
     * @param httpServletRequest The HttpServletRequest containing the user's ID as an attribute.
     * @return ResponseEntity<UserResponseDTO> A ResponseEntity containing a UserResponseDTO object,
     * indicating the success of the operation. HttpStatus.OK is returned for a successful response.
     * If the authenticated user does not have the 'ADMIN' or 'CUSTOMER' role, HttpStatus.FORBIDDEN is returned.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<UserResponseDTO> getById(HttpServletRequest httpServletRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        // Burada istersek aşağıdaki gibi gelen requesten id değerini alabiliriz.
        // Ama biz diğer yolu yani authentication içinden aldık.
        // Long id = (Long) httpServletRequest.getAttribute("id");

        log.info("Fetching user with ID: {}", id);

        UserResponseDTO userResponseDTO = this.userService.getById(id);

        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
}
