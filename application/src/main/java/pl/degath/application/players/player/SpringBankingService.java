package pl.degath.application.players.player;

import org.springframework.stereotype.Service;
import pl.degath.banking.BankingApi;
import pl.degath.banking.command.CreateAccount;
import pl.degath.players.port.ExternalBankingApi;
import pl.degath.shared.infrastructure.Money;

import java.util.Objects;
import java.util.UUID;

@Service
public class SpringBankingService implements ExternalBankingApi {

    private final BankingApi bankingApi;

    public SpringBankingService(BankingApi bankingApi) {
        this.bankingApi = Objects.requireNonNull(bankingApi, "Banking api has to be specified.");
    }

    @Override
    public void createAccount(UUID ownerId, Money money) {
        bankingApi.createAccount(new CreateAccount(ownerId, money));
    }
}
