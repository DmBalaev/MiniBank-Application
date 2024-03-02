package dm.bl.miniBank.client.service;

import dm.bl.miniBank.client.dto.ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ClientService {
    ClientDto findByEmail(String email);
    ClientDto findByPhone(String phoneNumber);
    Page<ClientDto> findBySearch(LocalDate birthDate, String fullName, Pageable pageable);
}
