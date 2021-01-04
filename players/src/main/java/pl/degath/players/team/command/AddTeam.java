package pl.degath.players.team.command;

import pl.degath.players.infrastructure.Command;
import pl.degath.players.infrastructure.Validator;

public class AddTeam implements Command {
    private final String teamName;

    public AddTeam(String teamName) {
        this.teamName = Validator.notBlank(teamName, "Team name has to be specified.");
    }

    public String getTeamName() {
        return teamName;
    }
}
