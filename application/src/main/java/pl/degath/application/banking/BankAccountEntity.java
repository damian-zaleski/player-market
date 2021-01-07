package pl.degath.application.banking;

import pl.degath.banking.BankAccount;
import pl.degath.shared.infrastructure.Money;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;


@Entity
@Table(name = "accounts")
public class BankAccountEntity {
    @Id
    private String id;
    private String ownerId;
    private String amount;
    private String currency;

    public BankAccountEntity() {
    }

    public BankAccountEntity(BankAccount bankAccount) {
        this.id = bankAccount.getId().toString();
        this.ownerId = bankAccount.getOwnerId().toString();
        this.amount = bankAccount.getBalance().getAmount().toString();
        this.currency = bankAccount.getBalance().getCurrency().getCurrencyCode();
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public BankAccount toDomain() {
        return new BankAccount(UUID.fromString(id), UUID.fromString(ownerId), new Money(new BigDecimal(amount), Currency.getInstance(currency)));
    }
}