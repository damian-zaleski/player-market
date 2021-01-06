package pl.degath.players.player.command;

import pl.degath.shared.infrastructure.Command;

import java.util.Objects;
import java.util.UUID;

public class UpdatePlayer implements Command {
    private final UUID playerId;
    private final String playerName;
    private final UUID teamId;

    public UpdatePlayer(UUID playerId, String playerName, UUID teamId) {
        this.playerId = Objects.requireNonNull(playerId, "Player id has to be specified.");
        this.playerName = playerName;
        this.teamId = teamId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public UUID getTeamId() {
        return teamId;
    }
}
