package ex.QuizzApp.Authentication;

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
    @NotBlank(message = "Username cannot be blank")
    @Column(unique = true, nullable = false)
    private String username;

    @Size(min = 8, message = "Password must contain at least 8 characters")
    @NotEmpty(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Column(nullable = false)
    private String password;

    @NotEmpty(message = "email can't be null")
    @NotBlank(message = "email can't be blank")
    @Column(unique = true, nullable = false)
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "birthdate can't be null")
    @NotBlank(message = "birthdate can't be blank")
    @Column(nullable = false)
    private LocalDate birthDate;
}
