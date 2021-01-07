package pl.degath.players.team.command;

import pl.degath.shared.infrastructure.Command;
import pl.degath.shared.infrastructure.Validator;

import java.util.Currency;
import java.util.Objects;

public class AddTeam implements Command {
    private final String teamName;
    private final Currency currency;

    public AddTeam(String teamName, Currency currency) {
        this.teamName = Validator.notBlank(teamName, "Team name has to be specified.");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
    }

    public String getTeamName() {
        return teamName;
    }

    public Currency getCurrency() {
        return currency;
    }
}
