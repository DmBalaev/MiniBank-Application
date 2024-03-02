package dm.bl.miniBank.client.service;

import dm.bl.miniBank.payload.ResponseApi;
import org.springframework.security.core.userdetails.UserDetails;

public interface ClientUpdateService {
    ResponseApi addPhone(UserDetails details, String phone);
    ResponseApi removePhone(UserDetails details, String phone);
    ResponseApi addEmail(UserDetails details, String email);
    ResponseApi removeEmail(UserDetails details, String email);
}
