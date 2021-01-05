package pl.degath.transfer.command;

import java.math.BigDecimal;
import java.util.UUID;

public class StartNewTransfer {
    private final UUID buyerId;
    private final UUID playerId;
    private final BigDecimal commissionPercentage;

    public StartNewTransfer(UUID buyerId, UUID playerId, BigDecimal commissionPercentage) {
        this.buyerId = buyerId;
        this.playerId = playerId;
        this.commissionPercentage = commissionPercentage;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public BigDecimal getCommissionPercentage() {
        return commissionPercentage;
    }
}
