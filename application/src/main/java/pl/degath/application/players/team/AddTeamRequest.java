package pl.degath.application.players.team;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.degath.players.team.command.AddTeam;

public class AddTeamRequest {

    private final String name;

    @JsonCreator
    public AddTeamRequest(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public AddTeam toCommand() {
        return new AddTeam(name);
    }
}
