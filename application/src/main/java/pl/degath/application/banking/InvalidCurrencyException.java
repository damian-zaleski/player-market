package pl.degath.application.banking;

public class InvalidCurrencyException extends RuntimeException {
    public InvalidCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
