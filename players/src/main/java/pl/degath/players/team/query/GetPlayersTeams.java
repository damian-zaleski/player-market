package pl.degath.players.team.query;

import pl.degath.shared.infrastructure.Query;

import java.util.Set;
import java.util.UUID;

public class GetPlayersTeams implements Query {
    private final Set<UUID> playerIds;

    public GetPlayersTeams(Set<UUID> playerIds) {
        this.playerIds = Set.copyOf(playerIds);
    }

    public Set<UUID> getPlayerIds() {
        return playerIds;
    }
}
