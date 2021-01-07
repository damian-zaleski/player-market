package pl.degath.application.banking;

import org.springframework.beans.factory.annotation.Autowired;
import pl.degath.banking.BankAccount;
import pl.degath.banking.port.BankAccountRepository;

import java.util.Optional;
import java.util.UUID;

public class SpringBankAccountRepositoryImpl implements BankAccountRepository {

    @Autowired
    private SpringBankAccountRepository springBankAccountRepository;

    @Override
    public Optional<BankAccount> getByOwnerId(UUID ownerId) {
        return springBankAccountRepository.findByOwnerId(ownerId.toString())
                .map(BankAccountEntity::toDomain);
    }

    @Override
    public BankAccount save(BankAccount entity) {
        BankAccountEntity saved = springBankAccountRepository.save(new BankAccountEntity(entity));
        return saved.toDomain();
    }
}
