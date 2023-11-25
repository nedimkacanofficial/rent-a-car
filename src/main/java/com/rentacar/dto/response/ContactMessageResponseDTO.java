package com.rentacar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String subject;
    private String body;
}
