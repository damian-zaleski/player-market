package pl.degath.players.player.command;

import pl.degath.shared.infrastructure.Command;
import pl.degath.shared.infrastructure.Validator;

import java.time.Year;
import java.time.YearMonth;
import java.util.UUID;

public class AddPlayer implements Command {
    private final String name;
    private final UUID teamId;
    private final YearMonth careerStart;
    private final Year yearOfBirth;

    public AddPlayer(String name, UUID teamId, YearMonth careerStart, Year yearOfBirth) {
        this.name = Validator.notBlank(name, "Name of new player has to be specified.");
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
}
