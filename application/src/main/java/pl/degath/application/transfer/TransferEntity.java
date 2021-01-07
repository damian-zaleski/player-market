package pl.degath.application.transfer;

import pl.degath.transfer.ContractFee;
import pl.degath.transfer.Player;
import pl.degath.transfer.Team;
import pl.degath.transfer.Transfer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;
import java.util.Currency;
import java.util.UUID;

@Entity
@Table(name = "transfers")
public class TransferEntity {
    @Id
    private String id;
    private String playerId;
    private String playerTeamId;
    private String playerYearOfBirth;
    private String playerYearOfCareerStart;
    private String playerMonthOfCareerStart;
    private String buyerId;
    private String buyerName;
    private String contractFeeTransferFee;
    private String contractFeeCommissionFee;
    private String contractFeeCurrency;
    private String sellerGotPaid;
    private String playerGotPaid;
    private String playerSold;

    public TransferEntity() {
    }

    public TransferEntity(Transfer transfer) {
        this.id = transfer.getId().toString();
        this.playerId = transfer.getPlayer().getId().toString();
        this.playerTeamId = transfer.getPlayer().getTeamId().toString();
        this.playerYearOfBirth = transfer.getPlayer().getYearOfBirth().toString();
        this.playerYearOfCareerStart = String.valueOf(transfer.getPlayer().getCareerStart().getMonth().getValue());
        this.playerMonthOfCareerStart = String.valueOf(transfer.getPlayer().getCareerStart().getYear());
        this.buyerId = transfer.getBuyer().getId().toString();
        this.buyerName = transfer.getBuyer().getId().toString();
        this.contractFeeTransferFee = transfer.getContractFee().getTransferFee().toString();
        this.contractFeeCommissionFee = transfer.getContractFee().getCommissionFee().toString();
        this.contractFeeCurrency = transfer.getContractFee().getCurrency().getCurrencyCode();
        this.sellerGotPaid = String.valueOf(transfer.isSellerGotPaid());
        this.playerGotPaid = String.valueOf(transfer.isPlayerGotPaid());
        this.playerSold = String.valueOf(transfer.isPlayerSold());
    }

    public String getId() {
        return id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerTeamId() {
        return playerTeamId;
    }

    public String getPlayerYearOfBirth() {
        return playerYearOfBirth;
    }

    public String getPlayerYearOfCareerStart() {
        return playerYearOfCareerStart;
    }

    public String getPlayerMonthOfCareerStart() {
        return playerMonthOfCareerStart;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getContractFeeTransferFee() {
        return contractFeeTransferFee;
    }

    public String getContractFeeCommissionFee() {
        return contractFeeCommissionFee;
    }

    public String getContractFeeCurrency() {
        return contractFeeCurrency;
    }

    public String getSellerGotPaid() {
        return sellerGotPaid;
    }

    public String getPlayerGotPaid() {
        return playerGotPaid;
    }

    public String getPlayerSold() {
        return playerSold;
    }

    public Transfer toDomain() {
        Player player = new Player(
                UUID.fromString(playerId),
                UUID.fromString(playerTeamId),
                Year.of(Integer.parseInt(playerYearOfBirth)),
                YearMonth.of(Integer.parseInt(playerYearOfCareerStart), Integer.parseInt(playerMonthOfCareerStart)));
        Team buyer = new Team(UUID.fromString(buyerId), Currency.getInstance(contractFeeCurrency));
        ContractFee contract = new ContractFee(new BigDecimal(contractFeeTransferFee), new BigDecimal(contractFeeCommissionFee), Currency.getInstance(contractFeeCurrency));
        return new Transfer(player, buyer, contract);
    }
}
