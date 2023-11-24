package com.rentacar.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "tbl_contact_message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 1, max = 50, message = "Your name '${validatedValue}' must be between {min} and {max} chars long!")
    @NotNull(message = "Please provide your name!")
    @Column(length = 50, nullable = false)
    private String name;

    @Email(message = "Please enter an '${validatedValue}' email address in email format")
    @Column(length = 50, nullable = false)
    private String email;

    @Size(min = 5, max = 50, message = "Your subject '${validatedValue}' must be between {min} and {max} chars long!")
    @NotNull(message = "Please provide message subject!")
    @Column(length = 50, nullable = false)
    private String subject;

    @Size(min = 20, max = 200, message = "Your message body '${validatedValue}' must be between {min} and {max} chars long!")
    @NotNull(message = "Please provide message body!")
    @Column(length = 200, nullable = false)
    private String body;
}
