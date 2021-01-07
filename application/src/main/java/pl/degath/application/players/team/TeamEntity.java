package pl.degath.application.players.team;


import pl.degath.players.team.Team;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Currency;
import java.util.UUID;

@Entity
@Table(name = "teams")
public class TeamEntity {
    @Id
    private String id;
    private String name;
    private String currencyCode;

    public TeamEntity() {
    }

    public TeamEntity(Team team) {
        this.id = team.getId().toString();
        this.name = team.getName();
        this.currencyCode = team.getCurrency().getCurrencyCode();
    }

    public Team toDomain() {
        return new Team(UUID.fromString(id), name, Currency.getInstance(currencyCode));
    }
}
