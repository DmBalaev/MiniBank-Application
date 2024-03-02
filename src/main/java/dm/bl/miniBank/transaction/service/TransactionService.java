package dm.bl.miniBank.transaction.service;

import dm.bl.miniBank.transaction.Transaction;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    Transaction createTransactional(UserDetails current, String recipient , BigDecimal amount);

    List<Transaction> getMyTransactions(UserDetails current);
}
