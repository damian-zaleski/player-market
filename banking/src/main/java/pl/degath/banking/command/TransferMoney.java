package pl.degath.banking.command;

import pl.degath.shared.infrastructure.Command;
import pl.degath.shared.infrastructure.Money;

import java.util.UUID;

public class TransferMoney implements Command {
    private final UUID fromOwnerId;
    private final UUID toOwnerId;
    private final Money amount;

    public TransferMoney(UUID fromOwnerId, UUID toOwnerId, Money amount) {
        this.fromOwnerId = fromOwnerId;
        this.toOwnerId = toOwnerId;
        this.amount = amount;
    }

    public UUID getFromOwnerId() {
        return fromOwnerId;
    }

    public UUID getToOwnerId() {
        return toOwnerId;
    }

    public Money getAmount() {
        return amount;
    }
}
