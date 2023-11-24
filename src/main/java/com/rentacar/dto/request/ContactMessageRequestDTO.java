package com.rentacar.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactMessageRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 50, message = "Your name '${validatedValue}' must be between {min} and {max} chars long!")
    private String name;

    @Email(message = "Please enter an '${validatedValue}' email address in email format")
    private String email;

    @NotBlank(message = "Subject cannot be blank")
    @NotNull(message = "Subject cannot be null")
    @Size(min = 5, max = 50, message = "Your subject '${validatedValue}' must be between {min} and {max} chars long!")
    private String subject;

    @NotBlank(message = "Message body cannot be blank")
    @NotNull(message = "Message body cannot be null")
    @Size(min = 20, max = 200, message = "Your message body '${validatedValue}' must be between {min} and {max} chars long!")
    private String body;
}
