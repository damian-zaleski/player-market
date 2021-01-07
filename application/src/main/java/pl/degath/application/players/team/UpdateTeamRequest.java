package pl.degath.application.players.team;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateTeamRequest {
    private final String teamName;

    @JsonCreator
    public UpdateTeamRequest(@JsonProperty("teamName") String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }
}
