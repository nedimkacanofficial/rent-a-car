package com.rentacar.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class LoginRequestDTO {
    @Email(message = "Please enter a valid email address.")
    @NotBlank(message = "Email is required.")
    @NotNull(message = "Email cannot be null.")
    private String email;

    @NotBlank(message = "Password is required.")
    @NotNull(message = "Password cannot be null.")
    private String password;
}
