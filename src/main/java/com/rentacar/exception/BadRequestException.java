package com.rentacar.exception;

import java.io.Serial;

/**
 * Description of UpdatePasswordRequestDTO.
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.exception
 * @since 28/11/2023
 */
public class BadRequestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }
}
