package dm.bl.miniBank.transaction.service.impl;

import dm.bl.miniBank.client.Client;
import dm.bl.miniBank.client.repository.ClientRepository;
import dm.bl.miniBank.exception.InsufficientAmountException;
import dm.bl.miniBank.transaction.Transaction;
import dm.bl.miniBank.transaction.TransactionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private ClientRepository clientsRepository;
    @Mock
    private TransactionalRepository transactionalRepository;
    @InjectMocks
    private TransactionServiceImpl transactionService;


    @Test
    public void CreateTransactional_SuccessfulTransfer() {
        Client sender = createClient(1L, "sender", new BigDecimal("100.00"));
        Client recipient = createClient(2L, "recipient", new BigDecimal("50.00"));
        String recipientPhone = "1234567890";
        BigDecimal amount = new BigDecimal("20.00");

        when(clientsRepository.findByLogin(sender.getLogin())).thenReturn(Optional.of(sender));
        when(clientsRepository.findByPhone(recipientPhone)).thenReturn(Optional.of(recipient));
        when(clientsRepository.save(sender)).thenReturn(sender);

        Transaction transaction = transactionService.createTransactional(sender, recipientPhone, amount);

        verify(clientsRepository, times(1)).findByPhone(recipientPhone);
        verify(clientsRepository, times(2)).save(any());
        verify(transactionalRepository, times(1)).save(transaction);
        assertEquals(new BigDecimal("80.00"), sender.getBalance());
        assertEquals(new BigDecimal("70.00"), recipient.getBalance());
    }

    @Test
    public void CreateTransactional_InsufficientFunds_ExceptionThrown() {
        Client sender = createClient(1L, "sender", new BigDecimal("100.00"));
        BigDecimal amount = new BigDecimal("200.00");

        when(clientsRepository.findByLogin(sender.getLogin())).thenReturn(Optional.of(sender));

        assertThrows(InsufficientAmountException.class, ()-> transactionService.createTransactional(sender, "recipient", amount));
    }

    @Test
    public void CreateTransactional_NegativeAmount_ExceptionThrown() {
        Client sender = createClient(1L, "sender", new BigDecimal("100.00"));
        BigDecimal amount = new BigDecimal("-1.00");

        assertThrows(IllegalArgumentException.class, () -> transactionService.createTransactional(sender, "recipient", amount));
    }

    private Client createClient(Long id, String login, BigDecimal balance) {
        return Client.builder()
                .id(id)
                .login(login)
                .balance(balance)
                .build();
    }
}