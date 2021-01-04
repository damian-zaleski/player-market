package pl.degath.players.player;

import pl.degath.players.infrastructure.Page;
import pl.degath.players.infrastructure.QueryHandler;
import pl.degath.players.player.query.GetAllPlayers;
import pl.degath.players.port.Repository;

public class GetAllPlayersQueryHandler implements QueryHandler<GetAllPlayers, Page<Player>> {

    private final Repository<Player> playerRepository;

    public GetAllPlayersQueryHandler(Repository<Player> playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Page<Player> handle(GetAllPlayers query) {
        return playerRepository.getAll(query.getPagination());
    }
}
