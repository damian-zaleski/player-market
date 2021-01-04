package pl.degath.players;

import pl.degath.players.player.Player;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;

import java.util.UUID;

public final class PlayerBuilder {

    private final Repository<Player> playerRepository;
    private final Repository<Team> teamRepository;
    private final Team team = new Team("Best Team Ever");
    private UUID teamId = team.getId();

    private String playerName = "Jan Kowalski";

    public PlayerBuilder(Repository<Player> playerRepository, Repository<Team> teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public PlayerBuilder withName(String newName) {
        this.playerName = newName;
        return this;
    }

    public PlayerBuilder withTeamId(UUID teamId) {
        this.teamId = teamId;
        return this;
    }

    private Player build() {
        return new Player(playerName, teamId);
    }

    public Player inDb() {
        teamRepository.save(team);
        return playerRepository.save(build());
    }
}
