package pl.degath.players;

import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;

public class TeamBuilder {

    private final Repository<Team> teamRepository;
    private String teamName = "Best Team Ever!";

    public TeamBuilder(Repository<Team> teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamBuilder withName(String newName) {
        this.teamName = newName;
        return this;
    }

    private Team build() {
        return new Team(teamName);
    }

    public Team inDb() {
        return teamRepository.save(build());
    }
}
