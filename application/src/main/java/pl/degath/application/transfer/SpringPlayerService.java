package pl.degath.application.transfer;

import org.springframework.stereotype.Service;
import pl.degath.players.player.UpdatePlayerCommandHandler;
import pl.degath.players.player.command.UpdatePlayer;
import pl.degath.players.port.Repository;
import pl.degath.transfer.Player;
import pl.degath.transfer.port.ExternalPlayerApi;

import java.util.Optional;
import java.util.UUID;

@Service
public class SpringPlayerService implements ExternalPlayerApi {

    private final Repository<pl.degath.players.player.Player> playerRepository;
    private final UpdatePlayerCommandHandler updatePlayerCommandHandler;

    public SpringPlayerService(Repository<pl.degath.players.player.Player> playerRepository, UpdatePlayerCommandHandler updatePlayerCommandHandler) {
        this.playerRepository = playerRepository;
        this.updatePlayerCommandHandler = updatePlayerCommandHandler;
    }

    @Override
    public Optional<Player> get(UUID id) {
        return playerRepository.get(id)
                .map(player -> new Player(player.getId(), player.getTeamId(), player.getYearOfBirth(), player.getCareerStart()));
    }

    @Override
    public void changeTeam(UUID playerId, UUID teamId) {
        updatePlayerCommandHandler.handle(new UpdatePlayer(playerId, null, teamId));
    }
}
