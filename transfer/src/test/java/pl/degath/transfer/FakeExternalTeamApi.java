package pl.degath.transfer;

import pl.degath.transfer.port.ExternalTeamApi;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class FakeExternalTeamApi implements ExternalTeamApi {

    private Set<Team> teams = new HashSet<>();

    Team add(Team team) {
        teams.add(team);
        return team;
    }

    @Override
    public Optional<Team> get(UUID id) {
        return teams.stream()
                .filter(team -> team.getId().equals(id))
                .findFirst();
    }
}
