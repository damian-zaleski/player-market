package pl.degath.players.player.exception;

public class PlayerAlreadyExistsException extends RuntimeException {
    public PlayerAlreadyExistsException(String playerName) {
        super(String.format("Player with name [%s] already exist.", playerName));
    }
}
