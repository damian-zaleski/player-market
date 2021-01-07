package pl.degath.application.players;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.degath.application.banking.TransferMoneyRequest;
import pl.degath.banking.BankAccount;
import pl.degath.banking.port.BankAccountRepository;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;
import pl.degath.shared.infrastructure.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

public class BankingIntegrationTest extends RestIntegrationTest {

    @Autowired
    private Repository<Team> teamRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    void transfer() {
        BankAccount bankAccount = existingBankAccount("First Team");
        BankAccount secondBankAccount = existingBankAccount("Second Team");
        TransferMoneyRequest request = new TransferMoneyRequest(bankAccount.getOwnerId(), secondBankAccount.getOwnerId(), "500", "USD");

        given()
                .contentType(APPLICATION_JSON.toString())
                .body(request)
                .post("/api/v1/accounts/transfer")
                .then()
                .statusCode(200);
    }

    private BankAccount existingBankAccount(String teamName) {
        Team existingTeam = teamRepository.save(new Team(UUID.randomUUID(), teamName, Currency.getInstance("USD")));
        var bankAccount = new BankAccount(UUID.randomUUID(), existingTeam.getId(), new Money(new BigDecimal("500000000"), Currency.getInstance("USD")));
        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }
}
