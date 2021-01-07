package pl.degath.players.player;

import pl.degath.shared.infrastructure.Entity;
import pl.degath.shared.infrastructure.Validator;

import java.time.Year;
import java.time.YearMonth;
import java.util.Objects;
import java.util.UUID;

public class Player extends Entity {
    private String name;
    private UUID teamId;
    private final Year yearOfBirth;
    private final YearMonth careerStart;

    public Player(UUID id, String name, UUID teamId, Year yearOfBirth, YearMonth careerStart) {
        super(id);
        this.name = Validator.notBlank(name, "name has to be specified");
        this.teamId = teamId;
        this.yearOfBirth = Objects.requireNonNull(yearOfBirth, "Year of birth has to be specified.");
        this.careerStart = Objects.requireNonNull(careerStart, "Career start has to be specified.");
    }

    public YearMonth getCareerStart() {
        return careerStart;
    }

    public Year getYearOfBirth() {
        return yearOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }
}
