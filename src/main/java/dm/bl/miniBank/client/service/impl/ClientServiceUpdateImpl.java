package dm.bl.miniBank.client.service.impl;

import dm.bl.miniBank.client.Client;
import dm.bl.miniBank.client.Email;
import dm.bl.miniBank.client.PhoneNumber;
import dm.bl.miniBank.client.repository.ClientRepository;
import dm.bl.miniBank.client.repository.EmailRepository;
import dm.bl.miniBank.client.repository.PhoneNumberRepository;
import dm.bl.miniBank.client.service.ClientUpdateService;
import dm.bl.miniBank.exception.DuplicateException;
import dm.bl.miniBank.payload.ResponseApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceUpdateImpl implements ClientUpdateService {
    private final ClientRepository clientsRepository;
    private final PhoneNumberRepository phoneRepository;
    private final EmailRepository emailRepository;

    @Override
    public ResponseApi addPhone(UserDetails details, String phone) {
        log.info("Client update service: add phone");
        isPhoneExist(phone);
        var client = getClient(details);

        client.getPhones().add(new PhoneNumber(phone));
        updateClient(client);

        return new ResponseApi("Successful add phone");
    }

    @Override
    public ResponseApi removePhone(UserDetails details, String phone) {
        log.info("Client update service: remove phone");
        var client = getClient(details);
        List<PhoneNumber> phones = client.getPhones();

        PhoneNumber phoneToRemove = phones.stream()
                .filter(p -> p.getPhoneNumber().equals(phone))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Phone number not found"));

        if (phones.size() == 1) {
            log.warn("Client update service: phone deletion failed, at least one phone number must remain");
            throw new IllegalArgumentException("Can't delete last phone number");
        }

        phones.remove(phoneToRemove);
        updateClient(client);

        return new ResponseApi("Successful remove phone");
    }

    @Override
    public ResponseApi addEmail(UserDetails details, String email) {
        log.info("Client update service: add email");
        isEmailExist(email);
        var client = getClient(details);

        client.getEmails().add(new Email(email));
        updateClient(client);

        return new ResponseApi("Successful add email");
    }

    @Override
    public ResponseApi removeEmail(UserDetails details, String email) {
        log.info("Client update service: remove email");
        var client = getClient(details);
        List<Email> emails = client.getEmails();

        Email emailToRemove = emails.stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));

        if (emails.size() == 1) {
            log.warn("Client update service: email deletion failed, at least one email must remain");
            throw new IllegalArgumentException("Can't delete last email");
        }

        emails.remove(emailToRemove);
        updateClient(client);

        return new ResponseApi("Successful remove email");
    }

    private void isPhoneExist(String phone) {
        if (phoneRepository.existsByPhoneNumber(phone)){
            log.warn("Client update service: the phone is already in use");
            throw new DuplicateException("The phone is already in use");
        }
    }

    private void isEmailExist(String email) {
        if (emailRepository.existsByEmail(email)){
            log.warn("Client update service: the email is already in use");
            throw new DuplicateException("The email is already in use");
        }
    }

    private Client getClient(UserDetails details) {
        return clientsRepository.findByLogin(details.getUsername())
                .orElseThrow();
    }

    private void updateClient(Client client) {
        clientsRepository.save(client);
    }
}