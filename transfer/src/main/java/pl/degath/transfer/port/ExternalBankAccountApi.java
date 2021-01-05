package pl.degath.transfer.port;

import pl.degath.shared.infrastructure.Money;

import java.util.UUID;

public interface ExternalBankAccountApi {
    void transferMoney(UUID from, UUID to, Money amount);
}
