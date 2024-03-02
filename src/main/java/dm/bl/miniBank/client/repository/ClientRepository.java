package dm.bl.miniBank.client.repository;

import dm.bl.miniBank.client.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByLogin(String login);

    @Query("""
            SELECT c FROM Client c 
            JOIN c.phones p  
            WHERE p.phoneNumber = :phone
            """)
    Optional<Client> findByPhone(@Param("phone") String phone);

    @Query("""
            SELECT c FROM Client c            
            JOIN c.emails e 
            WHERE e.email = :email
            """)
    Optional<Client> findByEmail(@Param("email") String email);

    @Query("""
            SELECT c FROM Client c
            WHERE (COALESCE(:birthDate, c.birthday) = c.birthday OR c.birthday > :birthDate)
            AND (:fullName IS NULL OR CONCAT(c.firstName, ' ', c.lastName, ' ', c.sureName) LIKE :fullName)
            """)
    Page<Client> findBySearch(@Param("birthDate") LocalDate birthDate, @Param("fullName") String fullName, Pageable pageable);


    boolean existsByLogin(String login);
}