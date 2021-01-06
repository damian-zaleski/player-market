package pl.degath.players.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.players.PlayerBuilder;
import pl.degath.shared.infrastructure.Page;
import pl.degath.shared.infrastructure.Pagination;
import pl.degath.shared.infrastructure.QueryHandler;
import pl.degath.players.player.query.GetAllPlayers;
import pl.degath.players.port.InMemoryRepository;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class GetAllPlayersQueryHandlerTest {

    private QueryHandler<GetAllPlayers, Page<Player>> getAllPlayersQueryHandler;
    private PlayerBuilder playerBuilder;

    @BeforeEach
    void setUp() {
        Repository<Player> playerRepository = new InMemoryRepository<>();
        Repository<Team> teamRepository = new InMemoryRepository<>();
        getAllPlayersQueryHandler = new GetAllPlayersQueryHandler(playerRepository);
        playerBuilder = new PlayerBuilder(playerRepository, teamRepository);
    }

    @Test
    void getAllPlayers_withMissingPagination_returnsPlayersWithDefaultPagination() {
        this.multiplePlayersInDb(40);
        GetAllPlayers queryWithMissingPagination = new GetAllPlayers(null);

        Page<Player> result = getAllPlayersQueryHandler.handle(queryWithMissingPagination);

        assertThat(result.getCurrentPage()).isEqualTo(1);
        assertThat(result.getPagesCount()).isEqualTo(2);
        assertThat(result.getResult()).hasSize(20);
    }

    @Test
    void getAllPlayers_withCustomPagination_returnsPlayers() {
        this.multiplePlayersInDb(40);
        GetAllPlayers queryWithMissingPagination = new GetAllPlayers(Pagination.perPage(5, 2));

        Page<Player> result = getAllPlayersQueryHandler.handle(queryWithMissingPagination);

        assertThat(result.getCurrentPage()).isEqualTo(2);
        assertThat(result.getPagesCount()).isEqualTo(8);
        assertThat(result.getResult()).hasSize(5);
    }

    @Test
    void getAllPlayers_withRequestedPageOutOfBound_returnsPlayers() {
        this.multiplePlayersInDb(10);
        GetAllPlayers queryWithMissingPagination = new GetAllPlayers(Pagination.perPage(10, 2));

        Page<Player> result = getAllPlayersQueryHandler.handle(queryWithMissingPagination);

        assertThat(result.getCurrentPage()).isEqualTo(2);
        assertThat(result.getPagesCount()).isEqualTo(1);
        assertThat(result.getResult()).hasSize(0);
    }

    @Test
    void getAllPlayers_withCountHigherThenPlayersInDb_returnsPlayers() {
        this.multiplePlayersInDb(10);
        GetAllPlayers queryWithMissingPagination = new GetAllPlayers(Pagination.perPage(15, 1));

        Page<Player> result = getAllPlayersQueryHandler.handle(queryWithMissingPagination);

        assertThat(result.getCurrentPage()).isEqualTo(1);
        assertThat(result.getPagesCount()).isEqualTo(1);
        assertThat(result.getResult()).hasSize(10);
    }


    void multiplePlayersInDb(int players) {
        IntStream.rangeClosed(1, players)
                .forEach(player -> playerBuilder
                        .withName("Player" + player)
                        .inDb());
    }
}