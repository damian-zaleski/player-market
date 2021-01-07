package pl.degath.application.players.team;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.degath.players.team.command.AddTeam;

import java.util.Currency;

public class AddTeamRequest {

    private final String name;
    private final String currency;

    @JsonCreator
    public AddTeamRequest(@JsonProperty("name") String name,
                          @JsonProperty("currency") String currency) {
        this.name = name;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    @JsonIgnore
    public AddTeam toCommand() {
        return new AddTeam(name, Currency.getInstance(currency));
    }
}
