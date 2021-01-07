package pl.degath.banking.adapters;

import pl.degath.banking.BankAccount;
import pl.degath.banking.port.BankAccountRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryBankAccountRepository implements BankAccountRepository {

    private final Map<UUID, BankAccount> entities;

    public InMemoryBankAccountRepository() {
        this.entities = new HashMap<>();
    }

    @Override
    public Optional<BankAccount> getByOwnerId(UUID ownerId) {
        return entities.values()
                .stream()
                .filter(bankAccount -> bankAccount.getOwnerId().equals(ownerId))
                .findFirst();
    }

    @Override
    public BankAccount save(BankAccount entity) {
        entities.put(entity.getId(), entity);
        return entity;
    }

    public List<BankAccount> getAll() {
        return List.copyOf(entities.values());
    }
}
