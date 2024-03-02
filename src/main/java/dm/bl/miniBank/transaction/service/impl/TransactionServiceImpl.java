package dm.bl.miniBank.transaction.service.impl;

import dm.bl.miniBank.client.Client;
import dm.bl.miniBank.client.repository.ClientRepository;
import dm.bl.miniBank.exception.InsufficientAmountException;
import dm.bl.miniBank.exception.ResourceNotFound;
import dm.bl.miniBank.transaction.Transaction;
import dm.bl.miniBank.transaction.TransactionalRepository;
import dm.bl.miniBank.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final ClientRepository clientsRepository;
    private final TransactionalRepository transactionalRepository;

    @Override
    public Transaction createTransactional(UserDetails current, String recipient, BigDecimal amount) {
        log.info("Transaction service: start transaction");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Invalid amount");
            throw new IllegalArgumentException("Invalid amount");
        }

        var senderClient = findCurrentClient(current);

        var senderBalance = senderClient.getBalance();
        if (senderBalance.compareTo(amount) < 0) {
            log.warn("Insufficient amount");
            throw new InsufficientAmountException("Insufficient amount");
        }

        var recipientClient = clientsRepository.findByPhone(recipient)
                .orElseThrow(() -> new ResourceNotFound("Recipient not found"));

        var senderNewBalance = senderBalance.subtract(amount);
        var recipientNewBalance = recipientClient.getBalance().add(amount);

        senderClient.setBalance(senderNewBalance);
        recipientClient.setBalance(recipientNewBalance);

        var transactional = Transaction.builder()
                .sender(senderClient)
                .receiver(recipientClient)
                .amount(amount)
                .dateTime(LocalDateTime.now())
                .build();

        clientsRepository.save(senderClient);
        clientsRepository.save(recipientClient);
        transactionalRepository.save(transactional);

        log.info("Transfer completed successfully");
        return transactional;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getMyTransactions(UserDetails current) {
        log.info("Transaction service: start find my transaction");
        var senderClient = findCurrentClient(current);

        return transactionalRepository.findByOwner(senderClient.getLogin());
    }

    private Client findCurrentClient(UserDetails current) {
        return clientsRepository.findByLogin(current.getUsername())
                .orElseThrow(() -> new ResourceNotFound("Sender not found"));
    }
}
