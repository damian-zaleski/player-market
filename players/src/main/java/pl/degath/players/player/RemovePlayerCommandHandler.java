package pl.degath.players.player;

import pl.degath.shared.infrastructure.CommandHandler;
import pl.degath.players.player.command.RemovePlayer;
import pl.degath.players.port.Repository;

import java.util.Objects;

public class RemovePlayerCommandHandler implements CommandHandler<RemovePlayer> {

    private final Repository<Player> playerRepository;

    public RemovePlayerCommandHandler(Repository<Player> playerRepository) {
        this.playerRepository = Objects.requireNonNull(playerRepository, "PlayerRepository has to be specified");
    }

    @Override
    public void handle(RemovePlayer command) {
        playerRepository.remove(command.getPlayerId());
    }
}
