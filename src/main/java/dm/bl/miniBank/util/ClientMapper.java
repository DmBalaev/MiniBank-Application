package dm.bl.miniBank.util;

import dm.bl.miniBank.client.Client;
import dm.bl.miniBank.client.Email;
import dm.bl.miniBank.client.PhoneNumber;
import dm.bl.miniBank.client.dto.ClientDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientMapper {

    public ClientDto map(Client client) {
        List<String> emails = client.getEmails().stream()
                .map(Email::getEmail)
                .toList();

        List<String> phones = client.getPhones().stream()
                .map(PhoneNumber::getPhoneNumber)
                .toList();

        return ClientDto.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .sureName(client.getSureName())
                .emails(emails)
                .phonesNumbers(phones)
                .build();
    }

    public List<ClientDto> mapAll(List<Client> clients) {
        return clients.stream()
                .map(this::map)
                .toList();
    }
}