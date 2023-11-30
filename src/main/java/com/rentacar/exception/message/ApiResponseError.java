package com.rentacar.exception.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * We wrote an error class for the message to be sent to the client when we throw the resulting exceptions.
 */

/**
 * Description of UpdatePasswordRequestDTO.
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.exception.message
 * @since 28/11/2023
 */
@Getter
public class ApiResponseError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private String message;
    private String requestUri;

    private ApiResponseError() {
        timestamp = LocalDateTime.now();
    }

    public ApiResponseError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiResponseError(HttpStatus status, String message, String requestUri) {
        this(status);
        this.message = message;
        this.requestUri = requestUri;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }
}
