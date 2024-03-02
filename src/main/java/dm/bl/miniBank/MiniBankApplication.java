package dm.bl.miniBank;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Mini Bank API", version = "1.0", description = "API for Mini Bank application"),
		tags = {
				@Tag(name = "Registration and authentication", description = "Endpoints for registration and authentication"),
				@Tag(name = "Transactional management", description = "Endpoints for transaction management"),
				@Tag(name = "Client management", description = "Endpoints for client management")
		}
)
@SecurityScheme(
		name = "BearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT",
		description = "JWT bearer token for authentication",
		in = SecuritySchemeIn.HEADER
)
public class MiniBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniBankApplication.class, args);
	}

}
