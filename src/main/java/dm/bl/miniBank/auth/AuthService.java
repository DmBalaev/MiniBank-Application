package dm.bl.miniBank.auth;

import dm.bl.miniBank.payload.AuthRequest;
import dm.bl.miniBank.payload.AuthResponse;
import dm.bl.miniBank.payload.RegistrationRequest;

public interface AuthService {
    AuthResponse registration(RegistrationRequest request);
    AuthResponse signin(AuthRequest request);
}
