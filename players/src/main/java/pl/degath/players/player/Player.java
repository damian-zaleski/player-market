package pl.degath.players.player;

import pl.degath.players.infrastructure.Entity;
import pl.degath.players.infrastructure.Validator;

import java.util.UUID;

public class Player extends Entity {
    private String name;
    private UUID teamId;

    public Player(String name, UUID teamId) {
        this.name = Validator.notBlank(name, "name has to be specified");
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }
}
