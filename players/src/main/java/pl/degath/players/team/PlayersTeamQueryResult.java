package pl.degath.players.team;

import java.util.UUID;

public class PlayersTeamQueryResult {
    private final UUID playerId;
    private final Team team;

    public PlayersTeamQueryResult(UUID playerId, Team team) {
        this.playerId = playerId;
        this.team = team;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Team getTeam() {
        return team;
    }
}
