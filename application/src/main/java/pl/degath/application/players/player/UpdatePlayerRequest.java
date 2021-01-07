package pl.degath.application.players.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UpdatePlayerRequest {
    private final String playerName;
    private final UUID teamId;

    @JsonCreator
    public UpdatePlayerRequest(@JsonProperty("playerName") String playerName,
                               @JsonProperty("teamId") UUID teamId) {
        this.playerName = playerName;
        this.teamId = teamId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public UUID getTeamId() {
        return teamId;
    }
}
