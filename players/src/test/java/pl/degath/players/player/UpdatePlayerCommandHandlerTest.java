package pl.degath.players.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.players.PlayerBuilder;
import pl.degath.players.TeamBuilder;
import pl.degath.players.infrastructure.CommandHandler;
import pl.degath.players.player.command.UpdatePlayer;
import pl.degath.players.player.exception.PlayerAlreadyExistsException;
import pl.degath.players.player.exception.PlayerNotFoundException;
import pl.degath.players.port.InMemoryRepository;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;
import pl.degath.players.team.exception.TeamNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UpdatePlayerCommandHandlerTest {

    private Repository<Player> playerRepository;
    private Repository<Team> teamRepository;
    private CommandHandler<UpdatePlayer> updatePlayerCommandHandler;
    private PlayerBuilder playerBuilder;

    @BeforeEach
    void setUp() {
        playerRepository = new InMemoryRepository<>();
        teamRepository = new InMemoryRepository<>();
        updatePlayerCommandHandler = new UpdatePlayerCommandHandler(playerRepository, teamRepository);
        playerBuilder = new PlayerBuilder(playerRepository, teamRepository);

    }

    @Test
    void updatePlayer_existingPlayerId_throwsException() {
        Player existingPlayer = new PlayerBuilder(playerRepository, teamRepository).inDb();
        UUID differentExistingTeamId = new TeamBuilder(teamRepository).withName("Other Team").inDb().getId();
        UpdatePlayer commandWithExistingPlayerId = new UpdatePlayer(existingPlayer.getId(), "New Name", differentExistingTeamId);

        updatePlayerCommandHandler.handle(commandWithExistingPlayerId);

        assertThat(this.getPlayerById(existingPlayer.getId()).getName()).isEqualTo("New Name");
    }

    @Test
    void updatePlayer_missingPlayerId_throwsException() {
        UUID notExistingPlayerId = UUID.fromString("ac8fad99-43c5-4c1a-90e1-b9e8c12bbfae");
        UpdatePlayer commandWithNotExistingPlayerId = new UpdatePlayer(notExistingPlayerId, null, null);

        Throwable thrown = catchThrowable(() -> updatePlayerCommandHandler.handle(commandWithNotExistingPlayerId));

        assertThat(thrown)
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessage("Player with id ac8fad99-43c5-4c1a-90e1-b9e8c12bbfae does not exist.");
    }

    @Test
    void updatePlayer_notUniqueName_throwsException() {
        UUID existingPlayerId = playerBuilder.withName("John Snow")
                .inDb()
                .getId();
        String alreadyUsedName = playerBuilder.withName("Tom Smith")
                .inDb()
                .getName();
        UpdatePlayer commandWithNotUniqueName = new UpdatePlayer(existingPlayerId, alreadyUsedName, null);

        Throwable thrown = catchThrowable(() -> updatePlayerCommandHandler.handle(commandWithNotUniqueName));

        assertThat(thrown)
                .isInstanceOf(PlayerAlreadyExistsException.class)
                .hasMessage("Player with name [Tom Smith] already exist.");
    }

    @Test
    void updatePlayer_byNotExistingTeam_throwsException() {
        UUID existingPlayerId = playerBuilder
                .withName("John Snow")
                .inDb()
                .getId();
        UUID notExistingTeamId = UUID.fromString("ac8fad99-43c5-4c1a-90e1-b9e8c12bbfae");
        UpdatePlayer commandWithNotExistingTeamId = new UpdatePlayer(existingPlayerId, "Unique Name", notExistingTeamId);


        Throwable thrown = catchThrowable(() -> updatePlayerCommandHandler.handle(commandWithNotExistingTeamId));

        assertThat(thrown)
                .isInstanceOf(TeamNotFoundException.class)
                .hasMessage("Team with id [ac8fad99-43c5-4c1a-90e1-b9e8c12bbfae] does not exist.");
    }

    private Player getPlayerById(UUID existingPlayerId) {
        return playerRepository.get(existingPlayerId)
                .orElseThrow();
    }
}