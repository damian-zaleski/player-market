package pl.degath.players.team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.players.PlayerBuilder;
import pl.degath.players.TeamBuilder;
import pl.degath.shared.infrastructure.CommandHandler;
import pl.degath.players.player.Player;
import pl.degath.players.port.InMemoryRepository;
import pl.degath.players.port.Repository;
import pl.degath.players.team.command.RemoveTeam;
import pl.degath.players.team.exception.TeamHasAssignedPlayersException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RemoveTeamCommandHandlerTest {

    private Repository<Team> teamRepository;
    private Repository<Player> playerRepository;
    private CommandHandler<RemoveTeam> removeTeamCommandHandler;

    @BeforeEach
    void setUp() {
        teamRepository = new InMemoryRepository<>();
        playerRepository = new InMemoryRepository<>();
        removeTeamCommandHandler = new RemoveTeamCommandHandler(teamRepository, playerRepository);
    }

    @Test
    void removeTeam_withoutPlayers_removesTeam() {
        UUID existingTeamId = new TeamBuilder(teamRepository).inDb().getId();
        RemoveTeam commandWithExistingTeamId = new RemoveTeam(existingTeamId);

        removeTeamCommandHandler.handle(commandWithExistingTeamId);

        assertThat(teamRepository.getAll()).hasSize(0);
    }

    @Test
    void removeTeam_withPlayers_throwsException() {
        Player player = new PlayerBuilder(playerRepository, teamRepository).inDb();
        RemoveTeam commandWithTeamThatHasPlayers = new RemoveTeam(player.getTeamId());

        Throwable thrown = catchThrowable(() -> removeTeamCommandHandler.handle(commandWithTeamThatHasPlayers));

        assertThat(thrown)
                .isInstanceOf(TeamHasAssignedPlayersException.class)
                .hasMessage(String.format("Cannot remove team [%s] with players.", player.getTeamId()));
    }
}