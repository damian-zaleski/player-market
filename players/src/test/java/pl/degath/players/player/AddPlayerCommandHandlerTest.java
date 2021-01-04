package pl.degath.players.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.players.PlayerBuilder;
import pl.degath.players.TeamBuilder;
import pl.degath.players.infrastructure.CommandHandler;
import pl.degath.players.player.command.AddPlayer;
import pl.degath.players.player.exception.PlayerAlreadyExistsException;
import pl.degath.players.port.InMemoryRepository;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;
import pl.degath.players.team.exception.TeamNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AddPlayerCommandHandlerTest {

    private static final UUID NOT_EXISTING_TEAM_ID = UUID.fromString("ac8fad99-43c5-4c1a-90e1-b9e8c12bbfae");
    private Repository<Player> playerRepository;
    private Repository<Team> teamRepository;
    private CommandHandler<AddPlayer> addPlayerHandler;

    @BeforeEach
    void setUp() {
        playerRepository = new InMemoryRepository<>();
        teamRepository = new InMemoryRepository<>();
        addPlayerHandler = new AddPlayerCommandHandler(playerRepository, teamRepository);
    }

    @Test
    void addPlayer_withValidExistingTeam_addsPlayer() {
        AddPlayer commandWithValidParams = new AddPlayer("name", this.existingTeamId());

        addPlayerHandler.handle(commandWithValidParams);

        assertThat(playerRepository.getAll()).hasSize(1);
    }

    @Test
    void addPlayer_withNotExistingTeam_throwsException() {
        AddPlayer playerToAddWithNotExistingTeamId = new AddPlayer("name", NOT_EXISTING_TEAM_ID);

        Throwable thrown = catchThrowable(() -> addPlayerHandler.handle(playerToAddWithNotExistingTeamId));

        assertThat(thrown)
                .isInstanceOf(TeamNotFoundException.class)
                .hasMessage("Team with id [ac8fad99-43c5-4c1a-90e1-b9e8c12bbfae] does not exist.");
    }


    @Test
    void addPlayer_withAlreadyExistingPlayerName_throwsException() {
        this.existingPlayer();
        AddPlayer commandWithAlreadyExistingPlayerName = new AddPlayer("Robert Lewandowski", NOT_EXISTING_TEAM_ID);

        Throwable thrown = catchThrowable(() -> addPlayerHandler.handle(commandWithAlreadyExistingPlayerName));

        assertThat(thrown)
                .isInstanceOf(PlayerAlreadyExistsException.class)
                .hasMessage("Player with name [Robert Lewandowski] already exist.");
    }

    private void existingPlayer() {
        new PlayerBuilder(playerRepository, teamRepository)
                .withName("Robert Lewandowski")
                .inDb();
    }

    private UUID existingTeamId() {
        return new TeamBuilder(teamRepository).inDb().getId();
    }
}