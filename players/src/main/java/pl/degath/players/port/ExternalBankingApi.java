package pl.degath.players.port;

import pl.degath.shared.infrastructure.Money;

import java.util.UUID;

public interface ExternalBankingApi {

    void createAccount(UUID ownerId, Money money);
}
