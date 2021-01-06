package pl.degath.banking;

import pl.degath.banking.command.TransferMoney;
import pl.degath.shared.infrastructure.Money;

import java.util.UUID;

public interface BankingApi {


    void transferMoney(TransferMoney transferMoney);
}
