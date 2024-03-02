package dm.bl.miniBank.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @NotBlank
    private String login;
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}