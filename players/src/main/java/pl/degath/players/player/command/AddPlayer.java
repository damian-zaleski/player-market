package pl.degath.players.player.command;

import pl.degath.players.infrastructure.Command;
import pl.degath.players.infrastructure.Validator;

import java.util.UUID;

public class AddPlayer implements Command {
    private final String name;
    private final UUID teamId;

    public AddPlayer(String name, UUID teamId) {
        this.name = Validator.notBlank(name, "Name of new player has to be specified.");
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public UUID getTeamId() {
        return teamId;
    }
}
