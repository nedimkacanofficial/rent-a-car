package com.rentacar.controller;

import com.rentacar.dto.request.ContactMessageRequestDTO;
import com.rentacar.dto.response.ContactMessageResponseDTO;
import com.rentacar.dto.response.default_response.DefaultResponseDTO;
import com.rentacar.dto.response.default_response.ResponseMessage;
import com.rentacar.service.ContactMessageService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/contact-messages")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Contact Message Controller")
public class ContactMessageController {
    private final ContactMessageService contactMessageService;

    /**
     * Retrieves a list of all contact messages.
     * <p>
     * This endpoint is designed to fetch all contact messages stored in the system.
     * The response includes a list of ContactMessageResponseDTO objects.
     *
     * @return ResponseEntity<List < ContactMessageResponseDTO>> A ResponseEntity containing a list
     * of ContactMessageResponseDTO objects, indicating the success of the operation.
     * HttpStatus.OK is returned for a successful response.
     */
    @GetMapping
    public ResponseEntity<List<ContactMessageResponseDTO>> getAll() {
        log.info("Fetching all contact messages.");

        List<ContactMessageResponseDTO> contactMessageResponseDTO = this.contactMessageService.getAll();

        return new ResponseEntity<>(contactMessageResponseDTO, HttpStatus.OK);
    }

    /**
     * Retrieves a specific contact message by its unique identifier.
     * <p>
     * This endpoint is designed to fetch a contact message with the given ID.
     * The response includes a ContactMessageResponseDTO representing the requested contact message.
     *
     * @param id The unique identifier of the contact message to retrieve.
     * @return ResponseEntity<ContactMessageResponseDTO> A ResponseEntity containing the
     * ContactMessageResponseDTO representing the requested contact message,
     * indicating the success of the operation. HttpStatus.OK is returned for a successful response.
     * If the specified contact message ID is not found, HttpStatus.NOT_FOUND is returned.
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<ContactMessageResponseDTO> getById(@PathVariable long id) {
        log.info("Fetching contact message with ID: {}", id);

        ContactMessageResponseDTO contactMessageResponseDTO = this.contactMessageService.getById(id);

        return new ResponseEntity<>(contactMessageResponseDTO, HttpStatus.OK);
    }

    /**
     * Creates a new contact message for a visitor.
     * <p>
     * This endpoint is designed to handle the creation of a new contact message based on the provided
     * ContactMessageRequestDTO. The request body should contain valid information about the visitor's message.
     * If the creation is successful, a response with a DefaultResponseDTO indicating success is returned.
     *
     * @param contactMessageRequestDTO The ContactMessageRequestDTO containing information about the visitor's message.
     *                                 It should be a valid and well-formed JSON representing the visitor's input.
     * @return ResponseEntity<DefaultResponseDTO> A ResponseEntity containing a DefaultResponseDTO indicating
     * the success of the creation operation. HttpStatus.CREATED is returned for a successful response.
     * If the input data is invalid or the creation fails, HttpStatus.BAD_REQUEST is returned.
     */
    @PostMapping(path = "/visitors")
    public ResponseEntity<DefaultResponseDTO> create(@Valid @RequestBody ContactMessageRequestDTO contactMessageRequestDTO) {
        log.info("Creating a new contact message for visitor with email: {}", contactMessageRequestDTO.getEmail());

        this.contactMessageService.create(contactMessageRequestDTO);

        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO(true, ResponseMessage.CREATED_SUCCESS_RESPONSE_MESSAGE);

        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Updates an existing contact message based on its unique identifier.
     * <p>
     * This endpoint is designed to handle the update of an existing contact message with the specified ID.
     * The request body should contain a valid ContactMessageRequestDTO with updated information.
     * If the update is successful, a response with a DefaultResponseDTO indicating success is returned.
     *
     * @param id                       The unique identifier of the contact message to update.
     * @param contactMessageRequestDTO The ContactMessageRequestDTO containing updated information
     *                                 about the contact message. It should be a valid and well-formed JSON
     *                                 representing the updated data.
     * @return ResponseEntity<DefaultResponseDTO> A ResponseEntity containing a DefaultResponseDTO indicating
     * the success of the update operation. HttpStatus.OK is returned for a successful response.
     * If the specified contact message ID is not found, HttpStatus.NOT_FOUND is returned.
     * If the input data is invalid, HttpStatus.BAD_REQUEST is returned.
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<DefaultResponseDTO> update(@PathVariable long id, @Valid @RequestBody ContactMessageRequestDTO contactMessageRequestDTO) {
        log.info("Updating contact message with ID: {}", id);

        this.contactMessageService.update(id, contactMessageRequestDTO);

        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO(true, ResponseMessage.UPDATED_SUCCESS_RESPONSE_MESSAGE);

        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.OK);
    }

    /**
     * Deletes a contact message based on its unique identifier.
     * <p>
     * This endpoint is designed to handle the deletion of a contact message with the specified ID.
     * If the deletion is successful, a response with a DefaultResponseDTO indicating success is returned.
     *
     * @param id The unique identifier of the contact message to delete.
     * @return ResponseEntity<DefaultResponseDTO> A ResponseEntity containing a DefaultResponseDTO indicating
     * the success of the deletion operation. HttpStatus.OK is returned for a successful response.
     * If the specified contact message ID is not found, HttpStatus.NOT_FOUND is returned.
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<DefaultResponseDTO> deleteById(@PathVariable long id) {
        log.info("Deleting contact message with ID: {}", id);

        this.contactMessageService.deleteById(id);

        DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO(true, ResponseMessage.DELETED_SUCCESS_RESPONSE_MESSAGE);

        return new ResponseEntity<>(defaultResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Retrieves a paginated list of contact messages.
     * <p>
     * This endpoint is designed to fetch a paginated list of contact messages based on the specified
     * page number, page size, sorting field, and sorting direction.
     *
     * @param page      The page number (1-based) of the results to retrieve.
     * @param size      The number of contact messages to include on each page.
     * @param sortField The field by which the contact messages should be sorted.
     * @param direction The sorting direction, which can be either "ASC" (ascending) or "DESC" (descending).
     *                  Default value is "DESC".
     * @return ResponseEntity<Page < ContactMessageResponseDTO>> A ResponseEntity containing a Page of
     * ContactMessageResponseDTO objects, representing the paginated list of contact messages.
     * HttpStatus.OK is returned for a successful response.
     * If the specified page is out of bounds or the input parameters are invalid,
     * HttpStatus.BAD_REQUEST is returned.
     */
    @GetMapping(path = "/pages")
    public ResponseEntity<Page<ContactMessageResponseDTO>> getAllWithPage(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size, @RequestParam(value = "sort") String sortField, @Schema(defaultValue = "DESC", allowableValues = {"DESC", "ASC"}) @RequestParam(value = "direction", defaultValue = "DESC") Direction direction) {
        int adjustedPageNumber = page - 1;
        Direction checkDirection = Direction.ASC;
        if ("desc".equalsIgnoreCase(direction.name())) {
            checkDirection = Direction.DESC;
        }

        Pageable pageable = PageRequest.of(adjustedPageNumber, size, Sort.by(checkDirection, sortField));

        log.info("Fetching paginated list of contact messages (Page {}, Size {}, SortField {}, Direction {}).", page, size, sortField, direction);

        Page<ContactMessageResponseDTO> contactMessageResponseDTOPage = this.contactMessageService.getAllWithPage(pageable);

        return new ResponseEntity<>(contactMessageResponseDTOPage, HttpStatus.OK);
    }
}
