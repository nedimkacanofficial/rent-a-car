package com.rentacar.controller;

import com.rentacar.dto.request.ContactMessageRequestDTO;
import com.rentacar.dto.response.ContactMessageResponseDTO;
import com.rentacar.service.ContactMessageService;
import io.swagger.v3.oas.annotations.media.Schema;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contactmessages")
@RequiredArgsConstructor
@Slf4j
public class ContactMessageController {
    private final ContactMessageService contactMessageService;

    @GetMapping
    public ResponseEntity<List<ContactMessageResponseDTO>> getAll() {
        List<ContactMessageResponseDTO> contactMessageResponseDTO = this.contactMessageService.getAll();
        return new ResponseEntity<>(contactMessageResponseDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ContactMessageResponseDTO> getById(@PathVariable long id) {
        ContactMessageResponseDTO contactMessageResponseDTO = this.contactMessageService.getById(id);
        return new ResponseEntity<>(contactMessageResponseDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/visitors")
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody ContactMessageRequestDTO contactMessageRequestDTO) {
        this.contactMessageService.create(contactMessageRequestDTO);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Contact Message has been created successfully.");
        responseMap.put("status", "true");

        return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable long id, @Valid @RequestBody ContactMessageRequestDTO contactMessageRequestDTO) {
        this.contactMessageService.update(id, contactMessageRequestDTO);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Contact Message has been updated successfully.");
        responseMap.put("status", "true");

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Map<String, String>> deleteById(@PathVariable long id) {
        this.contactMessageService.deleteById(id);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Contact Message has been deleted successfully.");
        responseMap.put("status", "true");

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @GetMapping(path = "/pages")
    public ResponseEntity<Page<ContactMessageResponseDTO>> getAllWithPage(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size, @RequestParam(value = "sort") String sortField, @Schema(defaultValue = "DESC", allowableValues = {"DESC", "ASC"}) @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        int adjustedPageNumber = page - 1;
        Direction checkDirection = Direction.ASC;
        if ("desc".equalsIgnoreCase(direction)) {
            checkDirection = Direction.DESC;
        }

        Pageable pageable = PageRequest.of(adjustedPageNumber, size, Sort.by(checkDirection, sortField));
        Page<ContactMessageResponseDTO> contactMessageResponseDTOPage = this.contactMessageService.getAllWithPage(pageable);

        return new ResponseEntity<>(contactMessageResponseDTOPage, HttpStatus.OK);
    }
}
