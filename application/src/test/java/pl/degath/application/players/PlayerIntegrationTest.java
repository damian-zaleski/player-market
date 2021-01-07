package pl.degath.application.players;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.degath.application.players.player.AddPlayerRequest;
import pl.degath.application.players.player.UpdatePlayerRequest;
import pl.degath.players.player.Player;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;
import pl.degath.shared.infrastructure.Money;

import java.time.Year;
import java.time.YearMonth;
import java.util.Currency;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

public class PlayerIntegrationTest extends RestIntegrationTest {

    @Autowired
    private Repository<Player> playerRepository;

    @Autowired
    private Repository<Team> teamRepository;

    @Test
    void add() {
        Team existingTeam = teamRepository.save(new Team(UUID.randomUUID(), "TeamName", Currency.getInstance("USD")));
        AddPlayerRequest request = new AddPlayerRequest(
                "John Snow",
                existingTeam.getId(),
                YearMonth.of(2001, 11),
                Year.of(1985),
                Money.USD.getCurrencyCode());

        given()
                .contentType(APPLICATION_JSON.toString())
                .body(request)
                .post("/api/v1/players")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    void update() {
        Player existingPlayer = this.existingPlayer();
        UpdatePlayerRequest request = new UpdatePlayerRequest("New Name", existingPlayer.getTeamId());

        given()
                .contentType(APPLICATION_JSON.toString())
                .body(request)
                .pathParam("playerId", existingPlayer.getId())
                .put("/api/v1/players/{playerId}")
                .then()
                .statusCode(200);
    }

    @Test
    void remove() {
        Player existingPlayer = this.existingPlayer();

        given()
                .pathParam("playerId", existingPlayer.getId())
                .delete("/api/v1/players/{playerId}")
                .then()
                .statusCode(200);

    }

    @Test
    void getAll() {
        this.existingPlayer();

        given()
                .get("/api/v1/players")
                .then()
                .statusCode(200);
    }

    private Player existingPlayer() {
        var existingTeam = teamRepository.save(Fixtures.team());
        return playerRepository.save(Fixtures.player(existingTeam.getId()));
    }
}
