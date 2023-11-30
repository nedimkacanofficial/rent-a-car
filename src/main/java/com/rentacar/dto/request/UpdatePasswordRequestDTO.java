package com.rentacar.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UpdatePasswordRequestDTO {
    @Size(min = 4, max = 50, message = "Password must be between {min} and {max} characters.")
    @NotBlank(message = "Password is required.")
    @NotNull(message = "Password cannot be null.")
    private String oldPassword;

    @Size(min = 4, max = 50, message = "Password must be between {min} and {max} characters.")
    @NotBlank(message = "Password is required.")
    @NotNull(message = "Password cannot be null.")
    private String newPassword;
}
