package com.rentacar.mapper;

import com.rentacar.domain.ContactMessage;
import com.rentacar.dto.request.ContactMessageRequestDTO;
import com.rentacar.dto.response.ContactMessageResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Description of UpdatePasswordRequestDTO.
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.mapper
 * @since 28/11/2023
 */
public class ContactMessageMapper {
    public static ContactMessageResponseDTO toDTO(ContactMessage contactMessage) {
        ContactMessageResponseDTO responseDTO = new ContactMessageResponseDTO();

        responseDTO.setId(contactMessage.getId());
        responseDTO.setName(contactMessage.getName());
        responseDTO.setEmail(contactMessage.getEmail());
        responseDTO.setSubject(contactMessage.getSubject());
        responseDTO.setBody(contactMessage.getBody());

        return responseDTO;
    }

    public static ContactMessage toEntity(ContactMessageRequestDTO requestDTO) {
        ContactMessage contactMessage = new ContactMessage();

        contactMessage.setName(requestDTO.getName());
        contactMessage.setEmail(requestDTO.getEmail());
        contactMessage.setSubject(requestDTO.getSubject());
        contactMessage.setBody(requestDTO.getBody());

        return contactMessage;
    }

    public static List<ContactMessageResponseDTO> toDTOList(List<ContactMessage> contactMessages) {
        return contactMessages.stream().map(ContactMessageMapper::toDTO).collect(Collectors.toList());
    }
}
