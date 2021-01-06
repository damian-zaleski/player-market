package pl.degath.banking;

import pl.degath.shared.infrastructure.Entity;
import pl.degath.shared.infrastructure.Money;

import java.util.Currency;
import java.util.UUID;

public class BankAccount extends Entity {
    private final UUID ownerId;
    private Money balance;

    public BankAccount(UUID ownerId, Money balance) {
        this.ownerId = ownerId;
        this.balance = balance;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public Money getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return balance.getCurrency();
    }

    public boolean isAffordable(Money transferValue) {
        return transferValue.getAmount().compareTo(this.balance.getAmount()) < 0;
    }

    public void withdraw(Money balance) {
        this.balance = new Money(this.balance.getAmount().subtract(balance.getAmount()), balance.getCurrency());
    }

    public void deposit(Money balance) {
        this.balance = new Money(this.balance.getAmount().add(balance.getAmount()), balance.getCurrency());
    }
}