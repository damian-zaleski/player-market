package pl.degath.players.player.exception;

import java.util.UUID;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(UUID playerId) {
        super(String.format("Player with id [%s] does not exist.", playerId));
    }
}