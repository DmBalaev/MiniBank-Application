package dm.bl.miniBank.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegistrationRequest(
        @NotBlank(message = "Login must not be blank")
        String login,
        @NotBlank(message = "Password must not be blank")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password,
        @NotNull(message = "Amount must not be null")
        BigDecimal amount,
        @NotBlank(message = "Phone must not be blank")
        String phone,
        @Email(message = "Invalid email address")
        String email,
        @NotBlank(message = "First name must not be blank")
        String firstName,
        @NotBlank(message = "Last name must not be blank")
        String lastName,
        @NotBlank(message = "Surname must not be blank")
        String surname,
        @NotNull(message = "Birthday must not be null")
        LocalDate birthday
) {
}