package com.rentacar.exception.message;

/**
 * Description of UpdatePasswordRequestDTO.
 *
 * @author github.com/nedimkacanofficial
 * @version 1.0
 * @package com.rentacar.exception.message
 * @since 28/11/2023
 */
public class ErrorMessage {
    public static final String RESOURCE_NOT_FOUND_MESSAGE = "Resource with id: %s not found!";
    public static final String USER_NOT_FOUND_MESSAGE = "User with email: %s not found!";
    public static final String EMAIL_ALREADY_EXIST_MESSAGE = "Email already exist: %s!";
    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You don't have any permission to change this value!";
    public static final String PASSWORD_NOT_MATCHED = "Your password are not matched!";
}