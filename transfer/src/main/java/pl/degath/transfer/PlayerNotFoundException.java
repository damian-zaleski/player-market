package pl.degath.transfer;

import java.util.UUID;

class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(UUID notExistingPlayerId) {
        super(String.format("Player with id [%s] not found.", notExistingPlayerId));
    }
}
