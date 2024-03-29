package dm.bl.miniBank.client.repository;

import dm.bl.miniBank.client.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    boolean existsByPhoneNumber(String PhoneNumber);
}