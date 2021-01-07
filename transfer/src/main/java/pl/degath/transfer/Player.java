package pl.degath.transfer;

import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class Player {
    private final UUID id;
    private final UUID teamId;
    private final Year yearOfBirth;
    private final YearMonth careerStart;

    public Player(UUID id, UUID teamId, Year yearOfBirth, YearMonth careerStart) {
        this.id = id;
        this.teamId = teamId;
        this.yearOfBirth = Objects.requireNonNull(yearOfBirth, "Year of birth has to be specified.");
        this.careerStart = Objects.requireNonNull(careerStart, "Career start has to be specified.");
    }

    public UUID getId() {
        return id;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public Year getYearOfBirth() {
        return yearOfBirth;
    }

    public YearMonth getCareerStart() {
        return careerStart;
    }

    public long getMonthsOfExperience() {
        return ChronoUnit.MONTHS.between(careerStart, YearMonth.now());
    }

    public long getAge() {
        return ChronoUnit.YEARS.between(yearOfBirth, Year.now());
    }
}
