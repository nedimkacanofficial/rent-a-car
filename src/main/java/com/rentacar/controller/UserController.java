package com.rentacar.controller;

import com.rentacar.dto.request.UpdatePasswordRequestDTO;
import com.rentacar.dto.response.UserResponseDTO;
import com.rentacar.dto.response.default_response.DefaultResponseDTO;
import com.rentacar.dto.response.default_response.ResponseMessage;
import com.rentacar.security.service.UserDetailsImpl;
import com.rentacar.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description of UpdatePasswordRequestDTO.
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.controller
 * @since 28/11/2023
 */
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
     * Retrieves user information for a specific user identified by their ID.
     * <p>
     * This endpoint is intended for use by administrators to fetch detailed information
     * about a user. The user's information is retrieved based on the provided user ID.
     *
     * @param id The unique identifier of the user whose information is to be fetched.
     * @return ResponseEntity<UserResponseDTO> A ResponseEntity containing the UserResponseDTO
     * representing the user's information if found, or an HTTP error response if the
     * user is not authorized or if the user ID is not valid.
     * @throws RuntimeException If an unexpected error occurs during the retrieval process.
     * @apiNote This method requires the caller to have the 'ADMIN' role to access the user's information.
     * The user information includes details such as username, email, etc., encapsulated in the
     * UserResponseDTO object.
     * @see UserResponseDTO
     * @see ResponseEntity
     * @see PreAuthorize
     * @see UserService # getById(long)
     * @since 1.0
     */
    // Admin uses this method to fetch any user's information.
    @GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getByUserId(@PathVariable long id) {
        log.info("Fetching contact message with ID: {}", id);

        UserResponseDTO userResponseDTO = this.userService.getById(id);

        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
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
    // Any registered user in the system brings his or her own information.
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

    /**
     * Retrieves a paginated list of user information based on the specified page, size, sorting field, and direction.
     * <p>
     * This endpoint is intended for use by administrators to fetch a paginated list of user information.
     * The result is sorted based on the provided sort field and direction. Pagination parameters, such as
     * page number, page size, sorting field, and sorting direction, are specified as request parameters.
     *
     * @param page      The page number (1-indexed) of the result set to retrieve.
     * @param size      The number of user records to include in each page.
     * @param sortField The field by which the result should be sorted.
     * @param direction The sorting direction, which can be either 'ASC' (ascending) or 'DESC' (descending).
     * @return ResponseEntity<Page < UserResponseDTO>> A ResponseEntity containing a Page of UserResponseDTO objects
     * representing the paginated list of user information.
     * @throws IllegalArgumentException If the provided page number or size is less than or equal to zero.
     * @throws RuntimeException         If an unexpected error occurs during the retrieval process.
     * @apiNote This method requires the caller to have the 'ADMIN' role to access the paginated list of user information.
     * The user information includes details such as username, email, etc., encapsulated in the Page<UserResponseDTO> object.
     * @see UserResponseDTO
     * @see Page
     * @see ResponseEntity
     * @see PreAuthorize
     * @see Direction
     * @see UserService#getAllWithPage(Pageable)
     * @since 1.0
     */
    @GetMapping("/auth/pages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponseDTO>> getAllWithPage(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size, @RequestParam(value = "sort") String sortField, @Schema(defaultValue = "DESC", allowableValues = {"DESC", "ASC"}) @RequestParam(value = "direction", defaultValue = "DESC") Direction direction) {
        int adjustedPageNumber = page - 1;
        Direction checkDirection = Direction.ASC;
        if ("desc".equalsIgnoreCase(direction.name())) {
            checkDirection = Direction.DESC;
        }

        Pageable pageable = PageRequest.of(adjustedPageNumber, size, Sort.by(checkDirection, sortField));

        log.info("Fetching paginated list of user (Page {}, Size {}, SortField {}, Direction {}).", page, size, sortField, direction);

        Page<UserResponseDTO> userResponseDTOPage = this.userService.getAllWithPage(pageable);

        return new ResponseEntity<>(userResponseDTOPage, HttpStatus.OK);
    }

    /**
     * Updates the password for the currently authenticated user.
     * <p>
     * This endpoint allows both administrators and customers to update their passwords.
     * The user's ID is retrieved from the request attribute, and the password is updated
     * based on the information provided in the UpdatePasswordRequestDTO.
     *
     * @param request                  The HttpServletRequest containing the user's ID as an attribute.
     * @param updatePasswordRequestDTO The request body containing the new password information.
     * @return ResponseEntity<DefaultResponseDTO> A ResponseEntity containing a DefaultResponseDTO
     * indicating the success of the password update operation.
     * @throws IllegalArgumentException If the provided updatePasswordRequestDTO is invalid or missing required fields.
     * @throws RuntimeException         If an unexpected error occurs during the password update process.
     * @apiNote This method requires the caller to have either the 'ADMIN' or 'CUSTOMER' role to update the password.
     * The update is performed based on the user's ID retrieved from the request attribute.
     * The response includes a DefaultResponseDTO indicating the success of the password update.
     * @see UpdatePasswordRequestDTO
     * @see DefaultResponseDTO
     * @see ResponseEntity
     * @see PreAuthorize
     * @see UserService#updatePassword(Long, UpdatePasswordRequestDTO)
     * @since 1.0
     */
    @PatchMapping("/auth")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<DefaultResponseDTO> updatePassword(HttpServletRequest request, @RequestBody UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        Long id = (Long) request.getAttribute("id");

        this.userService.updatePassword(id, updatePasswordRequestDTO);

        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO(true, ResponseMessage.UPDATED_SUCCESS_RESPONSE_MESSAGE);

        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.OK);
    }
}
