package pl.degath.players.infrastructure;

public class Validator {
    private Validator() {
        throw new AssertionError();
    }

    public static String notBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(message);
        }
        return value;
    }
}
