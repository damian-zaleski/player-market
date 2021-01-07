package pl.degath.application.banking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringBankAccountRepository extends JpaRepository<BankAccountEntity, String> {
    Optional<BankAccountEntity> findByOwnerId(String ownerId);
}
