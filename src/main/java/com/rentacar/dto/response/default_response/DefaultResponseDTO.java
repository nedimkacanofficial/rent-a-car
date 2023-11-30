package com.rentacar.dto.response.default_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.dto.response.default_response
 * @since 28/11/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultResponseDTO {
    boolean success;
    String message;
}
