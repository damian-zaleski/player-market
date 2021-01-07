package pl.degath.application.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.degath.players.port.Repository;
import pl.degath.transfer.Team;
import pl.degath.transfer.port.ExternalTeamApi;

import java.util.Optional;
import java.util.UUID;

@Service
public class SpringTeamService implements ExternalTeamApi {

    @Autowired
    Repository<pl.degath.players.team.Team> teamRepository;

    @Override
    public Optional<Team> get(UUID id) {
        return teamRepository.get(id)
                .map(team -> new Team(team.getId(), team.getCurrency()));
    }
}
