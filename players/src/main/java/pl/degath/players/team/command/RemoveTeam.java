package pl.degath.players.team.command;

import pl.degath.shared.infrastructure.Command;

import java.util.Objects;
import java.util.UUID;

public class RemoveTeam implements Command {
    private final UUID teamId;

    public RemoveTeam(UUID teamId) {
        this.teamId = Objects.requireNonNull(teamId, "Team id has to be specified.");
    }

    public UUID getTeamId() {
        return teamId;
    }
}
