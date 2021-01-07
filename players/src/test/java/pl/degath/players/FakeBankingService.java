package pl.degath.players;

import pl.degath.players.port.ExternalBankingApi;
import pl.degath.shared.infrastructure.Money;

import java.util.UUID;

public class FakeBankingService implements ExternalBankingApi {
    @Override
    public void createAccount(UUID ownerId, Money money) {

    }
}
