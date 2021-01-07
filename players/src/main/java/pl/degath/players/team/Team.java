package pl.degath.players.team;

import pl.degath.shared.infrastructure.Entity;
import pl.degath.shared.infrastructure.Validator;

import java.util.Currency;
import java.util.UUID;

public class Team extends Entity {
    private String name;
    private Currency currency;

    public Team(UUID id, String name, Currency currency) {
        super(id);
        this.name = Validator.notBlank(name, "Team name has to be specified.");
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
