package com.rentacar.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.dto.request
 * @since 28/11/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageRequestDTO {
    @NotBlank(message = "Name is required.")
    @NotNull(message = "Name cannot be null.")
    @Size(min = 1, max = 50, message = "Your name must be between {min} and {max} characters.")
    private String name;

    @Email(message = "Please enter a valid email address.")
    @NotBlank(message = "Email is required.")
    @NotNull(message = "Email cannot be null.")
    private String email;

    @NotBlank(message = "Subject is required.")
    @NotNull(message = "Subject cannot be null.")
    @Size(min = 5, max = 50, message = "Your subject must be between {min} and {max} characters.")
    private String subject;

    @NotBlank(message = "Message body is required.")
    @NotNull(message = "Message body cannot be null.")
    @Size(min = 20, max = 200, message = "Your message body must be between {min} and {max} characters.")
    private String body;
}
