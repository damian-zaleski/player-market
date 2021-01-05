package pl.degath.transfer;

import pl.degath.shared.infrastructure.Money;
import pl.degath.transfer.command.StartNewTransfer;
import pl.degath.transfer.port.ExternalBankAccountApi;
import pl.degath.transfer.port.ExternalPlayerApi;
import pl.degath.transfer.port.ExternalTeamApi;
import pl.degath.transfer.port.TransferRepository;

public class TransferService implements TransferApi {

    private final ExternalBankAccountApi externalBankAccountApi;
    private final ExternalPlayerApi externalPlayerApi;
    private final ExternalTeamApi externalTeamApi;
    private final TransferRepository transferRepository;

    public TransferService(ExternalBankAccountApi externalBankAccountApi,
                           ExternalPlayerApi externalPlayerApi,
                           ExternalTeamApi externalTeamApi,
                           TransferRepository transferRepository) {
        this.externalBankAccountApi = externalBankAccountApi;
        this.externalPlayerApi = externalPlayerApi;
        this.externalTeamApi = externalTeamApi;
        this.transferRepository = transferRepository;
    }

    @Override
    public void startNewTransfer(StartNewTransfer command) {
        Transfer transfer = this.prepareTransfer(command);
        this.payCommissionFee(transfer);
        this.payTransferFee(transfer);
        this.changePlayerTeam(transfer);
        transferRepository.save(transfer);
    }

    private Transfer prepareTransfer(StartNewTransfer command) {
        Player player = this.getPlayer(command);
        Team buyer = this.getBuyer(command);
        ContractFee contractFee = this.getContract(command, player, buyer);
        return new Transfer(player, buyer, contractFee);
    }

    private ContractFee getContract(StartNewTransfer command, Player player, Team buyer) {
        return new ContractFee(player, command.getCommissionPercentage(), buyer.getCurrency());
    }

    private Team getBuyer(StartNewTransfer command) {
        return externalTeamApi.get(command.getBuyerId())
                .orElseThrow(() -> new BuyerNotFoundException(command.getBuyerId()));
    }

    private Player getPlayer(StartNewTransfer command) {
        return externalPlayerApi.get(command.getPlayerId())
                .orElseThrow(() -> new PlayerNotFoundException(command.getPlayerId()));
    }

    private void payCommissionFee(Transfer transfer) {
        if (!transfer.isSellerGotPaid()) {
            Money commission = new Money(transfer.getContractFee().getCommissionFee(), transfer.getContractFee().getCurrency());
            externalBankAccountApi.transferMoney(transfer.getBuyer().getId(), transfer.getPlayer().getTeamId(), commission);
            transfer.setSellerGotPaid(true);
        }
    }

    private void payTransferFee(Transfer transfer) {
        if (!transfer.isPlayerGotPaid()) {
            Money transferFee = new Money(transfer.getContractFee().getTransferFee(), transfer.getContractFee().getCurrency());
            externalBankAccountApi.transferMoney(transfer.getBuyer().getId(), transfer.getPlayer().getId(), transferFee);
            transfer.setPlayerGotPaid(true);
        }
    }

    private void changePlayerTeam(Transfer transfer) {
        if (!transfer.isPlayerSold()) {
            externalPlayerApi.changeTeam(transfer.getPlayer().getId(), transfer.getBuyer().getId());
            transfer.setPlayerSold(true);
        }
    }
}
