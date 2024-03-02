package dm.bl.miniBank.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_sequence")
    @SequenceGenerator(name = "email_sequence", sequenceName = "email_sequence", allocationSize = 1)
    private Long id;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public Email(String email) {
        this.email = email;
    }
}