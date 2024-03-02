package dm.bl.miniBank.transaction;

import dm.bl.miniBank.client.Client;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
    @SequenceGenerator(name = "transaction_sequence", sequenceName = "transaction_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client receiver;

    private BigDecimal amount;
    private LocalDateTime dateTime;
}
