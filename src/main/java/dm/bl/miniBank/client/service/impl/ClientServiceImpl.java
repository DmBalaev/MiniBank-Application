package dm.bl.miniBank.client.service.impl;

import dm.bl.miniBank.client.Client;
import dm.bl.miniBank.client.dto.ClientDto;
import dm.bl.miniBank.client.repository.ClientRepository;
import dm.bl.miniBank.client.service.ClientService;
import dm.bl.miniBank.exception.ResourceNotFound;
import dm.bl.miniBank.util.ClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientsRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDto findByEmail(String email) {
        log.info("Client Service: find by email");
        var client = clientsRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("Client not found with email"));

        return clientMapper.map(client);
    }

    @Override
    public ClientDto findByPhone(String phoneNumber) {
        log.info("Client Service: find by phoneNumber");
        var client = clientsRepository.findByPhone(phoneNumber)
                .orElseThrow(() -> new ResourceNotFound("Client not found with phoneNumber"));

        return clientMapper.map(client);
    }

    @Override
    public Page<ClientDto> findBySearch(LocalDate birthDate, String fullName, Pageable pageable) {
        log.info("Client Service: find by search");

        Page<Client> clients = clientsRepository.findBySearch(birthDate, fullName, pageable);
        List<ClientDto> clientDtoList = clientMapper.mapAll(clients.getContent());

        return new PageImpl<>(clientDtoList, pageable, clients.getTotalElements());
    }
}