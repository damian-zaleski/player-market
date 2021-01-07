package pl.degath.application.banking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.degath.banking.BankAccount;

import java.util.Optional;

@Repository
public interface SpringBankAccountRepository extends JpaRepository<BankAccountEntity, String> {
    Optional<BankAccount> findByOwnerId(String ownerId);
}
