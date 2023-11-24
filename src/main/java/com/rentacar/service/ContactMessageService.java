package com.rentacar.service;

import com.rentacar.domain.ContactMessage;
import com.rentacar.dto.request.ContactMessageRequestDTO;
import com.rentacar.dto.response.ContactMessageResponseDTO;
import com.rentacar.exception.ResourceNotFoundException;
import com.rentacar.exception.message.ErrorMessage;
import com.rentacar.mapper.ContactMessageMapper;
import com.rentacar.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;

    @Transactional(readOnly = true)
    public List<ContactMessageResponseDTO> getAll() {
        List<ContactMessage> contactMessageList = this.contactMessageRepository.findAll();
        return ContactMessageMapper.toDTOList(contactMessageList);
    }

    @Transactional(readOnly = true)
    public ContactMessageResponseDTO getById(Long id) throws ResourceNotFoundException {
        ContactMessage contactMessage = this.contactMessageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
        return ContactMessageMapper.toDTO(contactMessage);
    }

    public void create(ContactMessageRequestDTO contactMessageRequestDTO) {
        this.contactMessageRepository.save(ContactMessageMapper.toEntity(contactMessageRequestDTO));
    }

    public void update(Long id, ContactMessageRequestDTO contactMessageRequestDTO) {
        ContactMessage messageToUpdate = this.contactMessageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
        messageToUpdate.setName(contactMessageRequestDTO.getName());
        messageToUpdate.setEmail(contactMessageRequestDTO.getEmail());
        messageToUpdate.setSubject(contactMessageRequestDTO.getSubject());
        messageToUpdate.setBody(contactMessageRequestDTO.getBody());

        this.contactMessageRepository.save(messageToUpdate);
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        ContactMessage contactMessage = this.contactMessageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
        this.contactMessageRepository.deleteById(contactMessage.getId());
    }

    public Page<ContactMessageResponseDTO> getAllWithPage(Pageable pageable) {
        Page<ContactMessage> page = contactMessageRepository.findAll(pageable);

        return page.map(ContactMessageMapper::toDTO);
    }
}
