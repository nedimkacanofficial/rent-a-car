package com.rentacar.controller;

import com.rentacar.dto.request.ContactMessageRequestDTO;
import com.rentacar.dto.response.ContactMessageResponseDTO;
import com.rentacar.service.ContactMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody ContactMessageRequestDTO contactMessageRequestDTO) {
        this.contactMessageService.create(contactMessageRequestDTO);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Contact Message has been created successfully.");
        responseMap.put("status", "true");
        responseMap.put("date", LocalDateTime.now().toString());

        return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
    }
}
