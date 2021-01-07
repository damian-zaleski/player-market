package pl.degath.application.players.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.degath.players.player.command.AddPlayer;

import java.time.Year;
import java.time.YearMonth;
import java.util.UUID;

public class AddPlayerRequest {
    private final String name;
    private final UUID teamId;
    private final YearMonth careerStart;
    private final Year yearOfBirth;

    @JsonCreator
    public AddPlayerRequest(@JsonProperty("name") String name,
                            @JsonProperty("teamId") UUID teamId,
                            @JsonProperty("careerStart") YearMonth careerStart,
                            @JsonProperty("yearOfBirth") Year yearOfBirth) {
        this.name = name;
        this.teamId = teamId;
        this.careerStart = careerStart;
        this.yearOfBirth = yearOfBirth;
    }

    public String getName() {
        return name;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public YearMonth getCareerStart() {
        return careerStart;
    }

    public Year getYearOfBirth() {
        return yearOfBirth;
    }

    @JsonIgnore
    public AddPlayer toCommand() {
        return new AddPlayer(name, teamId, careerStart, yearOfBirth);
    }
}
