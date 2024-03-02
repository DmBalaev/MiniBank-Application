package dm.bl.miniBank.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionalRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            SELECT t FROM Transaction t 
            WHERE t.sender.login = :login
            OR t.receiver.login = :login
            """)
    List<Transaction> findByOwner(@Param("login") String login);
}
