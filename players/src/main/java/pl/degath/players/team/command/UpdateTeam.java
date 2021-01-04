package pl.degath.players.team.command;

import pl.degath.players.infrastructure.Command;

import java.util.Objects;
import java.util.UUID;

public class UpdateTeam implements Command {

    private final UUID teamId;
    private final String teamName;

    public UpdateTeam(UUID teamId, String teamName) {
        this.teamId = Objects.requireNonNull(teamId, "Team id has to be specified.");
        this.teamName = teamName;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }
}
