package dm.bl.miniBank.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_sequence")
    @SequenceGenerator(name = "phone_sequence", sequenceName = "phone_sequence", allocationSize = 1)
    private Long id;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public PhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
