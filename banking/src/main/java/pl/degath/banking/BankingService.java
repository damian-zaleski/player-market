package pl.degath.banking;

import pl.degath.banking.command.CreateAccount;
import pl.degath.banking.command.TransferMoney;
import pl.degath.banking.exception.NotEnoughMoneyException;
import pl.degath.banking.exception.OwnerNotFoundException;
import pl.degath.banking.port.BankAccountRepository;
import pl.degath.banking.port.ExternalCurrencyExchangeApi;
import pl.degath.banking.port.ExternalPlayerApi;
import pl.degath.banking.port.ExternalTeamApi;
import pl.degath.shared.infrastructure.Money;

import java.math.BigDecimal;
import java.util.UUID;

public class BankingService implements BankingApi {
    private final BankAccountRepository bankAccountRepository;

    private final ExternalPlayerApi playerApi;
    private final ExternalTeamApi teamApi;
    private final ExternalCurrencyExchangeApi externalCurrencyExchangeApi;

    public BankingService(BankAccountRepository bankAccountRepository,
                          ExternalPlayerApi playerApi,
                          ExternalTeamApi teamApi,
                          ExternalCurrencyExchangeApi externalCurrencyExchangeApi) {
        this.bankAccountRepository = bankAccountRepository;
        this.playerApi = playerApi;
        this.teamApi = teamApi;
        this.externalCurrencyExchangeApi = externalCurrencyExchangeApi;
    }

    @Override
    public void createAccount(CreateAccount createAccount) {
        this.validateOwnerExistence(createAccount.getOwnerId());
        bankAccountRepository.save(new BankAccount(UUID.randomUUID(), createAccount.getOwnerId(), createAccount.getMoney()));
    }

    @Override
    public void transferMoney(TransferMoney command) {
        BankAccount fromAccount = this.getBankAccount(command.getFromOwnerId());
        BankAccount toAccount = this.getBankAccount(command.getToOwnerId());
        this.withdraw(command.getAmount(), fromAccount);
        this.deposit(command.getAmount(), toAccount);
    }

    private BankAccount getBankAccount(UUID ownerId) {
        this.validateOwnerExistence(ownerId);
        return bankAccountRepository.getByOwnerId(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException(ownerId));
    }

    private void validateOwnerExistence(UUID id) {
        if (!this.ownerExists(id)) {
            throw new OwnerNotFoundException(id);
        }
    }

    private boolean ownerExists(UUID id) {
        return playerApi.existsBy(id) || teamApi.existsBy(id);
    }

    private void withdraw(Money money, BankAccount fromAccount) {
        if (!fromAccount.isAffordable(money)) {
            throw new NotEnoughMoneyException();
        }
        fromAccount.withdraw(money);
        bankAccountRepository.save(fromAccount);
    }

    private void deposit(Money money, BankAccount toAccount) {
        Money toDeposit = this.applyCurrencyChange(money, toAccount);
        toAccount.deposit(toDeposit);
        bankAccountRepository.save(toAccount);
    }

    private Money applyCurrencyChange(Money money, BankAccount toAccount) {
        if (this.differentCurrencies(money, toAccount)) {
            BigDecimal rate = externalCurrencyExchangeApi.rate(money.getCurrency(), toAccount.getCurrency());
            return new Money(rate.multiply(money.getAmount()), toAccount.getCurrency());
        }
        return money;
    }


    private boolean differentCurrencies(Money money, BankAccount toAccount) {
        return !money.getCurrency().equals(toAccount.getCurrency());
    }
}
