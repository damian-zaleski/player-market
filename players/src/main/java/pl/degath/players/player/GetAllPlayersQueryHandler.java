package pl.degath.players.player;

import pl.degath.shared.infrastructure.Page;
import pl.degath.shared.infrastructure.QueryHandler;
import pl.degath.players.player.query.GetAllPlayers;
import pl.degath.players.port.Repository;

import java.util.Objects;

public class GetAllPlayersQueryHandler implements QueryHandler<GetAllPlayers, Page<Player>> {

    private final Repository<Player> playerRepository;

    public GetAllPlayersQueryHandler(Repository<Player> playerRepository) {
        this.playerRepository = Objects.requireNonNull(playerRepository, "Player repository has to be specified.");
    }

    @Override
    public Page<Player> handle(GetAllPlayers query) {
        return playerRepository.getAll(query.getPagination());
    }
}
