package pl.degath.banking.port;

import pl.degath.banking.BankAccount;

import java.util.Optional;
import java.util.UUID;

public interface BankAccountRepository {

    Optional<BankAccount> getByOwnerId(UUID ownerId);

    BankAccount save(BankAccount bankAccount);
}
