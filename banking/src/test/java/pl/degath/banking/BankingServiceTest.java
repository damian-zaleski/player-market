package pl.degath.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.degath.banking.command.TransferMoney;
import pl.degath.banking.exception.NotEnoughMoneyException;
import pl.degath.banking.exception.OwnerNotFoundException;
import pl.degath.banking.port.BankAccountRepository;
import pl.degath.banking.port.ExternalCurrencyExchangeApi;
import pl.degath.banking.port.ExternalPlayerApi;
import pl.degath.banking.port.ExternalTeamApi;
import pl.degath.shared.infrastructure.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static pl.degath.banking.BankAccountBuilder.PLN;
import static pl.degath.banking.BankAccountBuilder.USD;

class BankingServiceTest {

    private BankingApi bankingApi;
    private BankAccountRepository bankAccountRepository;
    private BankAccountBuilder bankAccountBuilder;

    @BeforeEach
    void setUp() {
        ExternalCurrencyExchangeApi externalCurrencyExchangeApi = new FakeCurrencyConverterService();
        bankAccountRepository = new InMemoryBankAccountRepository();
        ExternalPlayerApi externalPlayerApi = new FakeExternalPlayerService();
        ExternalTeamApi externalTeamApi = new FakeExternalTeamService();
        bankingApi = new BankingService(bankAccountRepository, externalPlayerApi, externalTeamApi, externalCurrencyExchangeApi);
        bankAccountBuilder = new BankAccountBuilder(bankAccountRepository);
    }

    @Test
    @DisplayName("Should be able to transfer money with currency change.")
    void transferMoney_withCurrencyChange() {
        BankAccount fromOwner = bankAccountBuilder
                .withOwnerId(UUID.fromString("8bedaa6d-558f-40da-95cb-c8c4f03be310"))
                .withBalance(of("100", USD))
                .inDb();
        BankAccount toOwner = bankAccountBuilder
                .withOwnerId(UUID.fromString("0a898bc4-d64d-4151-9182-c9f10882718f"))
                .withBalance(of("500", PLN))
                .inDb();
        TransferMoney withCurrencyChange = new TransferMoney(fromOwner.getOwnerId(), toOwner.getOwnerId(), new Money(new BigDecimal(10), USD));

        bankingApi.transferMoney(withCurrencyChange);

        assertThat(this.getAmount(fromOwner)).isEqualTo("90.00");
        assertThat(this.getAmount(toOwner)).isEqualTo("536.77");
    }

    @Test
    @DisplayName("Should be able to transfer money without currency change.")
    void transferMoney_withoutCurrencyChange() {
        BankAccount fromOwner = bankAccountBuilder
                .withOwnerId(UUID.fromString("8bedaa6d-558f-40da-95cb-c8c4f03be310"))
                .withBalance(of("100", USD))
                .inDb();
        BankAccount toOwner = bankAccountBuilder
                .withOwnerId(UUID.fromString("0a898bc4-d64d-4151-9182-c9f10882718f"))
                .withBalance(of("500", USD))
                .inDb();
        TransferMoney withoutCurrencyChange = new TransferMoney(fromOwner.getOwnerId(), toOwner.getOwnerId(), new Money(new BigDecimal(10), USD));

        bankingApi.transferMoney(withoutCurrencyChange);

        assertThat(this.getAmount(fromOwner)).isEqualTo("90.00");
        assertThat(this.getAmount(toOwner)).isEqualTo("510.00");
    }


    private BigDecimal getAmount(BankAccount bankAccount) {
        return bankAccountRepository.getByOwnerId(bankAccount.getOwnerId())
                .orElseThrow().getBalance().getAmount();
    }

    @Test
    @DisplayName("Cannot transfer money if not enough money.")
    void transferMoney_notEnoughMoney() {
        BankAccount fromOwner = bankAccountBuilder
                .withOwnerId(UUID.fromString("8bedaa6d-558f-40da-95cb-c8c4f03be310"))
                .withBalance(of("100", USD))
                .inDb();
        BankAccount toOwner = bankAccountBuilder
                .withOwnerId(UUID.fromString("0a898bc4-d64d-4151-9182-c9f10882718f"))
                .withBalance(of("500", USD))
                .inDb();
        TransferMoney withNotEnoughMoney = new TransferMoney(fromOwner.getOwnerId(), toOwner.getOwnerId(), new Money(new BigDecimal("101"), USD));

        Throwable thrown = catchThrowable(() -> bankingApi.transferMoney(withNotEnoughMoney));

        assertThat(thrown)
                .isInstanceOf(NotEnoughMoneyException.class);
    }

    @Test
    @DisplayName("Cannot transfer money if not recognized bank owner.")
    void transferMoney_notRecognizedBankAccountOwner() {
        BankAccount fromOwner = bankAccountBuilder
                .withBalance(of("100", USD))
                .inDb();
        BankAccount toOwner = bankAccountBuilder
                .withOwnerId(UUID.fromString("0a898bc4-d64d-4151-9182-c9f10882718f"))
                .withBalance(of("500", USD))
                .inDb();
        var command = new TransferMoney(fromOwner.getOwnerId(), toOwner.getOwnerId(), new Money(new BigDecimal(101), USD));

        Throwable thrown = catchThrowable(() -> bankingApi.transferMoney(command));

        assertThat(thrown)
                .isInstanceOf(OwnerNotFoundException.class);
    }

    private Money of(String amount, Currency currency) {
        return new Money(new BigDecimal(amount), currency);
    }
}