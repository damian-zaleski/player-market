package pl.degath.banking;

import pl.degath.banking.port.BankAccountRepository;
import pl.degath.shared.infrastructure.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

class BankAccountBuilder {

    public static final Currency PLN = Currency.getInstance("PLN");
    public static final Currency USD = Currency.getInstance("USD");
    private final BankAccountRepository bankAccountRepository;
    private UUID ownerId = UUID.randomUUID();
    private Money balance = new Money(new BigDecimal("5000000"), PLN);

    public BankAccountBuilder(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;

    }

    BankAccountBuilder withOwnerId(UUID newOwnerId) {
        ownerId = newOwnerId;
        return this;
    }

    BankAccountBuilder withBalance(Money newBalance) {
        balance = newBalance;
        return this;
    }

    private BankAccount build() {
        return new BankAccount(UUID.randomUUID(), ownerId, balance);
    }

    BankAccount inDb() {
        return bankAccountRepository.save(build());
    }
}
