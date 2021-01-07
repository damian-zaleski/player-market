package pl.degath.players.team;

import pl.degath.shared.infrastructure.Entity;
import pl.degath.shared.infrastructure.Validator;

import java.util.Currency;
import java.util.UUID;

public class Team extends Entity {
    private String name;
    private Currency currency;

    public Team(UUID id, String name) {
        super(id);
        this.name = Validator.notBlank(name, "Team name has to be specified.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
