package pl.degath.transfer;

import pl.degath.shared.infrastructure.Money;
import pl.degath.transfer.port.ExternalBankAccountApi;

import java.util.UUID;

public class FakeExternalBankAccountApi implements ExternalBankAccountApi {

    @Override
    public void transferMoney(UUID from, UUID to, Money amount) {
    }
}
