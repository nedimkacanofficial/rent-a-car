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

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;

    /**
     * Retrieves a list of all contact messages from the database.
     * <p>
     * This method fetches all contact messages stored in the database
     * using the contactMessageRepository. The retrieved contact messages
     * are then mapped to ContactMessageResponseDTO objects before being returned.
     *
     * @return List<ContactMessageResponseDTO> A list of ContactMessageResponseDTO objects
     * representing all contact messages in the database.
     */
    public List<ContactMessageResponseDTO> getAll() {
        List<ContactMessage> contactMessageList = this.contactMessageRepository.findAll();

        return ContactMessageMapper.toDTOList(contactMessageList);
    }

    /**
     * Retrieves a specific contact message by its ID from the database.
     * <p>
     * This method attempts to find a contact message in the database with the specified ID.
     * If the contact message is found, it is mapped to a ContactMessageResponseDTO and returned.
     * If the contact message is not found, a ResourceNotFoundException is thrown.
     *
     * @param id The ID of the contact message to retrieve.
     * @return ContactMessageResponseDTO A ContactMessageResponseDTO object representing the retrieved contact message.
     * @throws ResourceNotFoundException If the contact message with the specified ID is not found in the database.
     */
    public ContactMessageResponseDTO getById(Long id) throws ResourceNotFoundException {
        ContactMessage contactMessage = this.contactMessageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        return ContactMessageMapper.toDTO(contactMessage);
    }

    /**
     * Creates a new contact message in the database based on the provided DTO.
     * <p>
     * This method takes a ContactMessageRequestDTO as input, maps it to a ContactMessage entity,
     * and saves the entity to the database using the contactMessageRepository.
     *
     * @param contactMessageRequestDTO The ContactMessageRequestDTO containing information for creating a new contact message.
     */
    public void create(ContactMessageRequestDTO contactMessageRequestDTO) {
        this.contactMessageRepository.save(ContactMessageMapper.toEntity(contactMessageRequestDTO));
    }

    /**
     * Updates an existing contact message in the database based on the provided ID and DTO.
     * <p>
     * This method attempts to find a contact message in the database with the specified ID.
     * If the contact message is found, its fields are updated with the values from the provided
     * ContactMessageRequestDTO, and the updated entity is saved back to the database.
     * If the contact message is not found, a ResourceNotFoundException is thrown.
     *
     * @param id                       The ID of the contact message to update.
     * @param contactMessageRequestDTO The ContactMessageRequestDTO containing updated information.
     * @throws ResourceNotFoundException If the contact message with the specified ID is not found in the database.
     */
    public void update(Long id, ContactMessageRequestDTO contactMessageRequestDTO) {
        ContactMessage messageToUpdate = this.contactMessageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        messageToUpdate.setName(contactMessageRequestDTO.getName());
        messageToUpdate.setEmail(contactMessageRequestDTO.getEmail());
        messageToUpdate.setSubject(contactMessageRequestDTO.getSubject());
        messageToUpdate.setBody(contactMessageRequestDTO.getBody());

        this.contactMessageRepository.save(messageToUpdate);
    }

    /**
     * Deletes a contact message from the database based on the provided ID.
     * <p>
     * This method attempts to find a contact message in the database with the specified ID.
     * If the contact message is found, it is deleted from the database using the ID.
     * If the contact message is not found, a ResourceNotFoundException is thrown.
     *
     * @param id The ID of the contact message to delete.
     * @throws ResourceNotFoundException If the contact message with the specified ID is not found in the database.
     */
    public void deleteById(Long id) throws ResourceNotFoundException {
        ContactMessage contactMessage = this.contactMessageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        this.contactMessageRepository.deleteById(contactMessage.getId());
    }

    /**
     * Retrieves a paginated list of contact messages from the database.
     * <p>
     * This method fetches a page of contact messages from the database based on the provided Pageable object.
     * The retrieved page is then mapped to a Page of ContactMessageResponseDTO using ContactMessageMapper.
     *
     * @param pageable The Pageable object specifying the pagination parameters.
     * @return Page<ContactMessageResponseDTO> A paginated list of ContactMessageResponseDTO objects.
     */
    public Page<ContactMessageResponseDTO> getAllWithPage(Pageable pageable) {
        Page<ContactMessage> page = contactMessageRepository.findAll(pageable);

        return page.map(ContactMessageMapper::toDTO);
    }
}
