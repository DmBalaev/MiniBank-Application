package dm.bl.miniBank.util;

import dm.bl.miniBank.client.Client;
import dm.bl.miniBank.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceIncrementScheduler {
    private final ClientRepository clientsRepository;

    @Scheduled(fixedRate = 60000)
    public void increaseBalances() {
        List<Client> clients = clientsRepository.findAll();

        for (Client client : clients) {
            BigDecimal currentBalance = client.getBalance();
            BigDecimal newBalance = currentBalance.multiply(BigDecimal.valueOf(1.05));

            BigDecimal initialDeposit = client.getInitialBalance();
            BigDecimal maxBalance = initialDeposit.multiply(BigDecimal.valueOf(2.07));
            if (newBalance.compareTo(maxBalance) > 0) {
                newBalance = maxBalance;
            }

            client.setBalance(newBalance);
            clientsRepository.save(client);
        }
        log.info("Balances increased successfully");
    }
}