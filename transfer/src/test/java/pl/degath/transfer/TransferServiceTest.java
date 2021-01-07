package pl.degath.transfer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.degath.transfer.adapters.InMemoryTransferRepository;
import pl.degath.transfer.command.StartNewTransfer;

import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;
import java.util.Currency;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class TransferServiceTest {

    private TransferApi transferApi;
    private FakeExternalBankAccountApi fakeBankAccountApi;
    private FakeExternalPlayerApi fakePlayerApi;
    private FakeExternalTeamApi fakeTeamApi;
    private InMemoryTransferRepository transferRepository;

    @BeforeEach
    void setUp() {
        fakeBankAccountApi = new FakeExternalBankAccountApi();
        fakePlayerApi = new FakeExternalPlayerApi();
        fakeTeamApi = new FakeExternalTeamApi();
        transferRepository = new InMemoryTransferRepository();
        transferApi = new TransferService(fakeBankAccountApi, fakePlayerApi, fakeTeamApi, transferRepository);
    }

    @Test
    @DisplayName("Should be able to finalize transfer if everything is OK.")
    void startNewTransfer() {
        Team buyer = this.existingTeam();
        Player player = this.existingPlayer();
        BigDecimal commissionPercentage = new BigDecimal(10);
        StartNewTransfer validCommand = new StartNewTransfer(buyer.getId(), player.getId(), commissionPercentage);

        transferApi.startNewTransfer(validCommand);

        Transfer result = this.getFirst();
        assertThat(result.isPlayerGotPaid()).isTrue();
        assertThat(result.isSellerGotPaid()).isTrue();
        assertThat(result.isPlayerSold()).isTrue();
    }

    @Test
    @DisplayName("Cannot start transfer when buyer does not exist.")
    void startNewTransfer_missingBuyer() {
        UUID notExistingBuyerId = UUID.randomUUID();
        Player player = this.existingPlayer();
        BigDecimal commissionPercentage = new BigDecimal(10);
        StartNewTransfer withNotExistingBuyerId = new StartNewTransfer(notExistingBuyerId, player.getId(), commissionPercentage);

        Throwable thrown = catchThrowable(() -> transferApi.startNewTransfer(withNotExistingBuyerId));

        assertThat(thrown)
                .isInstanceOf(BuyerNotFoundException.class)
                .hasMessage(String.format("Buyer with id [%s] not found.", notExistingBuyerId));
    }

    @Test
    @DisplayName("Cannot start transfer when player does not exist.")
    void startNewTransfer_missingPlayer() {
        Team buyer = this.existingTeam();
        UUID notExistingPlayerId = UUID.randomUUID();
        BigDecimal commissionPercentage = new BigDecimal(10);
        StartNewTransfer withNotExistingPlayerId = new StartNewTransfer(buyer.getId(), notExistingPlayerId, commissionPercentage);

        Throwable thrown = catchThrowable(() -> transferApi.startNewTransfer(withNotExistingPlayerId));

        assertThat(thrown)
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessage(String.format("Player with id [%s] not found.", notExistingPlayerId));
    }

    @Test
    @DisplayName("Cannot start transfer when commission over 10 percent")
    void startNewTransfer_commissionPercentageOver10Percent(){
        Team buyer = this.existingTeam();
        Player player = this.existingPlayer();
        BigDecimal commissionPercentage = new BigDecimal(15);
        var withCommissionPercentOver10 = new StartNewTransfer(buyer.getId(), player.getId(), commissionPercentage);

        Throwable thrown = catchThrowable(() -> transferApi.startNewTransfer(withCommissionPercentOver10));

        assertThat(thrown)
                .isInstanceOf(DoNotBeTooGreedyException.class)
                .hasMessage("Commission percentage has to be <= 10%.");
    }

    private Transfer getFirst() {
        return transferRepository.getAll().stream().findFirst().orElseThrow();
    }

    private Player existingPlayer() {
        return fakePlayerApi.add(new Player(UUID.randomUUID(), existingTeam().getId(), Year.of(1999), YearMonth.of(2015, 11)));
    }

    private Team existingTeam() {
        return fakeTeamApi.add(new Team(UUID.randomUUID(), Currency.getInstance("USD")));
    }
}