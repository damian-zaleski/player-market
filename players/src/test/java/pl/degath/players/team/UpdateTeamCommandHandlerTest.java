package pl.degath.players.team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.players.TeamBuilder;
import pl.degath.players.infrastructure.CommandHandler;
import pl.degath.players.port.InMemoryRepository;
import pl.degath.players.port.Repository;
import pl.degath.players.team.command.UpdateTeam;
import pl.degath.players.team.exception.TeamAlreadyExistsException;
import pl.degath.players.team.exception.TeamNotFoundException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UpdateTeamCommandHandlerTest {

    private Repository<Team> teamRepository;
    private CommandHandler<UpdateTeam> updateTeamCommandHandler;

    @BeforeEach
    void setUp() {
        teamRepository = new InMemoryRepository<>();
        updateTeamCommandHandler = new UpdateTeamCommandHandler(teamRepository);
    }


    @Test
    void updateTeam_withExistingTeamId_updatesTeam() {
        var existingTeamId = new TeamBuilder(teamRepository).inDb().getId();
        UpdateTeam commandWIthNotExistingTeam = new UpdateTeam(existingTeamId, "Arka Gdynia");

        updateTeamCommandHandler.handle(commandWIthNotExistingTeam);

        assertThat(teamRepository.get(existingTeamId).orElseThrow().getName()).isEqualTo("Arka Gdynia");
    }

    @Test
    void updateTeam_withNotExistingTeamId_throwsException() {
        UUID notExistingTeamId = UUID.fromString("ac8fad99-43c5-4c1a-90e1-b9e8c12bbfae");
        UpdateTeam commandWIthNotExistingTeam = new UpdateTeam(notExistingTeamId, "Arka Gdynia");

        Throwable thrown = catchThrowable(() -> updateTeamCommandHandler.handle(commandWIthNotExistingTeam));

        assertThat(thrown)
                .isInstanceOf(TeamNotFoundException.class)
                .hasMessage("Team with id [ac8fad99-43c5-4c1a-90e1-b9e8c12bbfae] does not exist.");
    }

    @Test
    void updateTeam_withNotUniqueTeamName_throwsException() {
        UUID existingTeamId = new TeamBuilder(teamRepository).inDb().getId();
        String otherExistingTeamName = new TeamBuilder(teamRepository).withName("Legia Warszawa").inDb().getName();
        UpdateTeam commandWithNotUniqueTeamName = new UpdateTeam(existingTeamId, otherExistingTeamName);

        Throwable thrown = catchThrowable(() -> updateTeamCommandHandler.handle(commandWithNotUniqueTeamName));

        assertThat(thrown)
                .isInstanceOf(TeamAlreadyExistsException.class)
                .hasMessage(String.format("Team with name [%s] already exist.", otherExistingTeamName));
    }
}