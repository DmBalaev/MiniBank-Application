package dm.bl.miniBank.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionalRequest(
        @NotBlank(message = "Phone must not be blank")
        String recipientPhoneNumber,
        @NotNull(message = "Amount must not be null")
        BigDecimal amount
) {
}