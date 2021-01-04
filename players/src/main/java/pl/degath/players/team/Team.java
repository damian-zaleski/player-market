package pl.degath.players.team;

import pl.degath.players.infrastructure.Entity;
import pl.degath.players.infrastructure.Validator;

public class Team extends Entity {
    private String name;

    public Team(String name) {
        this.name = Validator.notBlank(name, "Team name has to be specified.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
