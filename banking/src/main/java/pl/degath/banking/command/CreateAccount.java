package pl.degath.banking.command;

import pl.degath.shared.infrastructure.Command;
import pl.degath.shared.infrastructure.Money;

import java.util.UUID;

public class CreateAccount implements Command {
    private final UUID ownerId;
    private final Money money;

    public CreateAccount(UUID ownerId, Money money) {
        this.ownerId = ownerId;
        this.money = money;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public Money getMoney() {
        return money;
    }
}

