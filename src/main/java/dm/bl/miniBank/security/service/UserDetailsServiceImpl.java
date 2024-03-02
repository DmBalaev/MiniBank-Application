package dm.bl.miniBank.security.service;

import dm.bl.miniBank.client.repository.ClientRepository;
import dm.bl.miniBank.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ClientRepository clientsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientsRepository.findByLogin(username)
                .orElseThrow(()-> new ApiException("Security: User not found"));
    }
}
