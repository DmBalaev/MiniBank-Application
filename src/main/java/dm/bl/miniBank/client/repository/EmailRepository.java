package dm.bl.miniBank.client.repository;

import dm.bl.miniBank.client.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    boolean existsByEmail(String email);
}