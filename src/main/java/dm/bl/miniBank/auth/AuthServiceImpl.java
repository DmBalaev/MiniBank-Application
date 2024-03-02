package dm.bl.miniBank.auth;

import dm.bl.miniBank.client.Client;
import dm.bl.miniBank.client.Email;
import dm.bl.miniBank.client.PhoneNumber;
import dm.bl.miniBank.client.repository.ClientRepository;
import dm.bl.miniBank.client.repository.EmailRepository;
import dm.bl.miniBank.client.repository.PhoneNumberRepository;
import dm.bl.miniBank.exception.ApiException;
import dm.bl.miniBank.payload.AuthRequest;
import dm.bl.miniBank.payload.AuthResponse;
import dm.bl.miniBank.payload.RegistrationRequest;
import dm.bl.miniBank.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ClientRepository clientsRepository;
    private final EmailRepository emailRepository;
    private final PhoneNumberRepository phoneRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthResponse registration(RegistrationRequest request) {
        log.info("Auth service: signup client");
        checkExist(request);

        var client = Client.builder()
                .login(request.login())
                .password(passwordEncoder.encode(request.password()))
                .balance(request.amount())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .sureName(request.surname())
                .birthday(request.birthday())
                .build();

        List<PhoneNumber> phones = new ArrayList<>();
        phones.add(new PhoneNumber(request.phone()));
        phones.forEach(phone -> phone.setClient(client));

        List<Email> emails = new ArrayList<>();
        emails.add(new Email(request.email()));
        emails.forEach(email -> email.setClient(client));

        client.setPhones(phones);
        client.setEmails(emails);

        clientsRepository.save(client);
        var jwt = jwtService.generateToken(client);
        log.info("Created user");
        return new AuthResponse(jwt);
    }

    private void checkExist(RegistrationRequest request){
        if (emailRepository.existsByEmail(request.email())){
            log.warn("Auth service: Email is already taken");
            throw new ApiException("Email is already taken");
        }
        if (clientsRepository.existsByLogin(request.login())){
            log.warn("Auth service: Login is already taken");
            throw new ApiException("Login is already taken");
        }
        if (phoneRepository.existsByPhoneNumber(request.phone())){
            log.warn("Auth service: Phone is already taken");
            throw new ApiException("Phone is already taken");
        }
    }

    @Override
    public AuthResponse signin(AuthRequest request) {
        log.info("Auth service: signin client");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

        var user = clientsRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new IllegalArgumentException("Invalid login or password."));
        var jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }
}