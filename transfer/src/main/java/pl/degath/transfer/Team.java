package pl.degath.transfer;

import java.util.Currency;
import java.util.UUID;

public class Team {
    private final UUID id;
    private final Currency currency;

    public Team(UUID id, Currency currency) {
        this.id = id;
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }
}
