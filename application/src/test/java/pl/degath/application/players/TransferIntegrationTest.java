package pl.degath.application.players;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.degath.application.transfer.StartNewTransferRequest;
import pl.degath.banking.BankAccount;
import pl.degath.banking.port.BankAccountRepository;
import pl.degath.players.player.Player;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;
import pl.degath.shared.infrastructure.Money;

import java.math.BigDecimal;
import java.time.Year;
import java.time.YearMonth;
import java.util.Currency;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

public class TransferIntegrationTest extends RestIntegrationTest {

    @Autowired
    private Repository<Team> teamRepository;

    @Autowired
    private Repository<Player> playerRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    void startNewTransfer() {
        Team buyerTeam = teamRepository.save(new Team(UUID.randomUUID(), "Buyer Team", Currency.getInstance("USD")));
        bankAccountRepository.save(new BankAccount(UUID.randomUUID(), buyerTeam.getId(), new Money(new BigDecimal("5000000"), Currency.getInstance("USD"))));
        Team actualPlayerTeam = teamRepository.save(new Team(UUID.randomUUID(), "Different Team", Currency.getInstance("PLN")));
        bankAccountRepository.save(new BankAccount(UUID.randomUUID(), actualPlayerTeam.getId(), new Money(new BigDecimal("5000000"), Currency.getInstance("USD"))));
        Player player = playerRepository.save(new Player(UUID.randomUUID(), "John Smith", actualPlayerTeam.getId(), Year.of(2000), YearMonth.of(2015, 11)));
        bankAccountRepository.save(new BankAccount(UUID.randomUUID(), player.getId(), new Money(new BigDecimal("5000000"), Currency.getInstance("USD"))));

        StartNewTransferRequest request = new StartNewTransferRequest(buyerTeam.getId(), player.getId(), "10");

        given()
                .contentType(APPLICATION_JSON.toString())
                .body(request)
                .post("/api/v1/transfers/start")
                .then()
                .statusCode(200);
    }
}
