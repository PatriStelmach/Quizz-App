package com.pjatk.QuizzApp.Authentication;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class RegistrationRequest
{
    @NotEmpty(message = "Username cannot be null")
    @NotNull(message = "Username cannot be blank")
    private String username;

    @Size(min = 8, message = "Password must contain at least 8 characters")
    @NotEmpty(message = "Password cannot be null")
    @NotNull(message = "Password cannot be blank")
    private String password;

    @NotEmpty(message = "email can't be null")
    @NotNull(message = "email can't be blank")
    @Email(message = "Invalid email address")
    private String email;

    private LocalDate birthDate;
}
