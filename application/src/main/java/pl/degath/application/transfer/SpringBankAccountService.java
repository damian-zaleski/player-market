package pl.degath.application.transfer;

import org.springframework.stereotype.Service;
import pl.degath.banking.BankingApi;
import pl.degath.banking.command.TransferMoney;
import pl.degath.shared.infrastructure.Money;
import pl.degath.transfer.port.ExternalBankAccountApi;

import java.util.UUID;

@Service
public class SpringBankAccountService implements ExternalBankAccountApi {

    private final BankingApi bankingApi;

    public SpringBankAccountService(BankingApi bankingApi) {
        this.bankingApi = bankingApi;
    }

    @Override
    public void transferMoney(UUID from, UUID to, Money amount) {
        bankingApi.transferMoney(new TransferMoney(from, to, amount));
    }
}
