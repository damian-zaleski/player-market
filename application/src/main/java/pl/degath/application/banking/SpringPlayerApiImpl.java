package pl.degath.application.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.degath.banking.port.ExternalPlayerApi;
import pl.degath.players.player.Player;
import pl.degath.players.port.Repository;

import java.util.UUID;

@Service
public class SpringPlayerApiImpl implements ExternalPlayerApi {

    //todo it shouldn't be repo, it should be dedicated service from players module
    //like fineById or existsById
    @Autowired
    private Repository<Player> repository;

    @Override
    public boolean existsBy(UUID id) {
        return repository.existsBy(player -> player.getId().equals(id));
    }
}
