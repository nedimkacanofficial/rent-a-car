package com.rentacar.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Description of UpdatePasswordRequestDTO.
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
public class RegisterRequestDTO {
    @Size(min = 1, max = 50, message = "First name must be between {min} and {max} characters.")
    @NotBlank(message = "First name is required.")
    @NotNull(message = "First name cannot be null.")
    private String firstName;

    @Size(min = 1, max = 50, message = "Last name must be between {min} and {max} characters.")
    @NotBlank(message = "Last name is required.")
    @NotNull(message = "Last name cannot be null.")
    private String lastName;

    @Email(message = "Please enter a valid email address.")
    @Size(min = 5, max = 50, message = "Email must be between {min} and {max} characters.")
    @NotBlank(message = "Email is required.")
    @NotNull(message = "Email cannot be null.")
    private String email;

    @Size(min = 4, max = 50, message = "Password must be between {min} and {max} characters.")
    @NotBlank(message = "Password is required.")
    @NotNull(message = "Password cannot be null.")
    private String password;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "Please provide a valid phone number.")
    @Size(min = 14, max = 14, message = "Phone number must be {max} characters long.")
    @NotBlank(message = "Phone number is required.")
    @NotNull(message = "Phone number cannot be null.")
    private String phoneNumber;

    @Size(max = 255, message = "Address must be up to {max} characters long.")
    @NotBlank(message = "Address is required.")
    @NotNull(message = "Address cannot be null.")
    private String address;

    @Size(min = 1, max = 50, message = "Zip code must be between {min} and {max} characters.")
    @NotBlank(message = "Zip code is required.")
    @NotNull(message = "Zip code cannot be null.")
    private String zipCode;
}
