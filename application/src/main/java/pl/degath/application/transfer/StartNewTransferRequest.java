package pl.degath.application.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.degath.transfer.command.StartNewTransfer;

import java.math.BigDecimal;
import java.util.UUID;

public class StartNewTransferRequest {
    private final UUID buyerId;
    private final UUID playerId;
    private final BigDecimal commissionPercentage;

    public StartNewTransferRequest(UUID buyerId, UUID playerId, String commissionPercentage) {
        this.buyerId = buyerId;
        this.playerId = playerId;
        this.commissionPercentage = new BigDecimal(commissionPercentage);
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

    @JsonIgnore
    public StartNewTransfer toCommand() {
        return new StartNewTransfer(buyerId, playerId, commissionPercentage);
    }
}
