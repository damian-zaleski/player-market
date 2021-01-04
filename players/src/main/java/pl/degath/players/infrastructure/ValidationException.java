package pl.degath.players.infrastructure;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}