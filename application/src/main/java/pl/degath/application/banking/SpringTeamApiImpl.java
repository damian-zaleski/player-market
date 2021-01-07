package pl.degath.application.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.degath.banking.port.ExternalTeamApi;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;

import java.util.UUID;

@Service
public class SpringTeamApiImpl implements ExternalTeamApi {

    //todo it shouldn't be repo, it should be dedicated service from players module
    //like fineById or existsById
    @Autowired
    private Repository<Team> repository;

    @Override
    public boolean existsBy(UUID id) {
        return repository.existsBy(team -> team.getId().equals(id));
    }
}
