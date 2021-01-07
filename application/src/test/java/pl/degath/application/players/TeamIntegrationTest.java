package pl.degath.application.players;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.degath.application.players.team.AddTeamRequest;
import pl.degath.application.players.team.UpdateTeamRequest;
import pl.degath.players.player.Player;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;

import static io.restassured.RestAssured.given;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

public class TeamIntegrationTest extends RestIntegrationTest {

    @Autowired
    private Repository<Player> playerRepository;

    @Autowired
    private Repository<Team> teamRepository;

    @Test
    void add() {
        AddTeamRequest request = new AddTeamRequest("Super Team", "USD");

        given()
                .contentType(APPLICATION_JSON.toString())
                .body(request)
                .post("/api/v1/teams")
                .then()
                .statusCode(200);
    }


    @Test
    void update() {
        Team existingTeam = teamRepository.save(Fixtures.team());
        UpdateTeamRequest request = new UpdateTeamRequest("New Team Name");

        given()
                .contentType(APPLICATION_JSON.toString())
                .body(request)
                .pathParam("teamId", existingTeam.getId())
                .put("/api/v1/teams/{teamId}")
                .then()
                .statusCode(200);
    }

    @Test
    void remove() {
        Team existingTeam = teamRepository.save(Fixtures.team());

        given()
                .pathParam("teamId", existingTeam.getId())
                .delete("/api/v1/teams/{teamId}")
                .then()
                .statusCode(200);

    }

    @Test
    void getPlayersTeams() {
        Player player = this.existingPlayer();

        given()
                .param("playersIds", player.getId())
                .get("/api/v1/teams")
                .then()
                .statusCode(200);
    }

    private Player existingPlayer() {
        var existingTeam = teamRepository.save(Fixtures.team());
        return playerRepository.save(Fixtures.player(existingTeam.getId()));
    }
}
