package pl.degath.players.team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.players.PlayerBuilder;
import pl.degath.players.infrastructure.QueryHandler;
import pl.degath.players.player.Player;
import pl.degath.players.player.exception.PlayerNotFoundException;
import pl.degath.players.port.InMemoryRepository;
import pl.degath.players.port.Repository;
import pl.degath.players.team.query.GetPlayersTeams;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class GetPlayersTeamsQueryHandlerTest {

    Repository<Team> teamRepository;
    Repository<Player> playerRepository;
    QueryHandler<GetPlayersTeams, Set<PlayersTeamQueryResult>> getPlayersTeamsQueryHandler;
    private PlayerBuilder playerBuilder;

    @BeforeEach
    void setUp() {
        teamRepository = new InMemoryRepository<>();
        playerRepository = new InMemoryRepository<>();
        playerBuilder = new PlayerBuilder(playerRepository, teamRepository);
        getPlayersTeamsQueryHandler = new GetPlayersTeamsQueryHandler(teamRepository, playerRepository);
    }

    @Test
    void getPlayersTeams_withValidPlayerIds_returnsPlayersWithTheirTeams() {
        var existingPlayerIds = this.multipleExistingPlayerIds(10);

        Set<PlayersTeamQueryResult> result = getPlayersTeamsQueryHandler.handle(new GetPlayersTeams(existingPlayerIds));

        assertThat(result).hasSize(10);
    }

    @Test
    void getPlayersTeams_withInvalidPlayerIds_throwsException() {
        Set<UUID> withOneNotExistingId = this.multipleExistingPlayerIds(5);
        withOneNotExistingId.add(UUID.fromString("5e847ade-43ad-40eb-baea-4d26d8f106fc"));

        Throwable thrown = catchThrowable(() -> getPlayersTeamsQueryHandler.handle(new GetPlayersTeams(withOneNotExistingId)));

        assertThat(thrown)
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessage("Player with id [5e847ade-43ad-40eb-baea-4d26d8f106fc] does not exist.");
    }

    private Set<UUID> multipleExistingPlayerIds(int players) {
        return IntStream.rangeClosed(1, players)
                .mapToObj(player -> playerBuilder
                        .withName("Player" + player)
                        .inDb()
                        .getId())
                .collect(Collectors.toSet());
    }

    @Test
    void getPlayersTeams_withPlayerWithoutTeam_returnsPlayerWithoutTeam() {
        UUID playerIdWithMissingTeam = playerBuilder.withTeamId(null).inDb().getId();
        Set<UUID> playerIdsWithMissingTeam = Set.of(playerIdWithMissingTeam);

        Set<PlayersTeamQueryResult> results = getPlayersTeamsQueryHandler.handle(new GetPlayersTeams(playerIdsWithMissingTeam));

        assertThat(results).hasSize(1);
        assertThat(getFirstQueryResult(results).getPlayerId()).isEqualTo(playerIdWithMissingTeam);
        assertThat(getFirstQueryResult(results).getTeam()).isNull();
    }

    private PlayersTeamQueryResult getFirstQueryResult(Set<PlayersTeamQueryResult> results) {
        return results.stream()
                .findFirst()
                .orElseThrow();
    }
}