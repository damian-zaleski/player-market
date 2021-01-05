package pl.degath.transfer;

import java.util.UUID;

public class Transfer {

    private final UUID id;
    private final Player player;
    private final Team buyer;
    private final ContractFee contractFee;
    private boolean sellerGotPaid = false;
    private boolean playerGotPaid = false;
    private boolean playerSold = false;

    public Transfer(Player player, Team buyer, ContractFee contractFee) {
        this.id = UUID.randomUUID();
        this.player = player;
        this.buyer = buyer;
        this.contractFee = contractFee;
    }

    public UUID getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Team getBuyer() {
        return buyer;
    }

    public ContractFee getContractFee() {
        return contractFee;
    }

    public boolean isSellerGotPaid() {
        return sellerGotPaid;
    }

    public boolean isPlayerGotPaid() {
        return playerGotPaid;
    }

    public boolean isPlayerSold() {
        return playerSold;
    }

    public void setSellerGotPaid(boolean sellerGotPaid) {
        this.sellerGotPaid = sellerGotPaid;
    }

    public void setPlayerGotPaid(boolean playerGotPaid) {
        this.playerGotPaid = playerGotPaid;
    }

    public void setPlayerSold(boolean playerSold) {
        this.playerSold = playerSold;
    }
}
