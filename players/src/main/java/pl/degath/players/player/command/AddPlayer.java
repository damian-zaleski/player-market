package pl.degath.players.player.command;

import pl.degath.shared.infrastructure.Command;
import pl.degath.shared.infrastructure.Validator;

import java.time.Year;
import java.time.YearMonth;
import java.util.Currency;
import java.util.UUID;

public class AddPlayer implements Command {
    private final String name;
    private final UUID teamId;
    private final YearMonth careerStart;
    private final Year yearOfBirth;
    private final Currency currency;

    public AddPlayer(String name, UUID teamId, YearMonth careerStart, Year yearOfBirth, Currency currency) {
        this.name = Validator.notBlank(name, "Name of new player has to be specified.");
        this.teamId = teamId;
        this.careerStart = careerStart;
        this.yearOfBirth = yearOfBirth;
        this.currency = currency;
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

    public Currency getCurrency() {
        return currency;
    }
}
