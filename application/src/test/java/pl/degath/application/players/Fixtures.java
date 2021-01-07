package pl.degath.application.players;

import pl.degath.players.player.Player;
import pl.degath.players.team.Team;

import java.time.Year;
import java.time.YearMonth;
import java.util.Currency;
import java.util.UUID;

public class Fixtures {

    private Fixtures() {
    }

    static Team team() {
        return new Team(UUID.randomUUID(), "TeamName", Currency.getInstance("USD"));
    }

    static Player player(UUID teamId) {
        return new Player(UUID.randomUUID(),
                "John Snow",
                teamId,
                Year.of(1985),
                YearMonth.of(2001, 11)
        );
    }
}
