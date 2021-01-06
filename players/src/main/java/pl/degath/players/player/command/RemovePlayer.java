package pl.degath.players.player.command;

import pl.degath.shared.infrastructure.Command;

import java.util.Objects;
import java.util.UUID;

public class RemovePlayer implements Command {
    private final UUID playerId;

    public RemovePlayer(UUID playerId) {
        this.playerId = Objects.requireNonNull(playerId, "Player id has to be specified.");
    }

    public UUID getPlayerId() {
        return playerId;
    }
}
