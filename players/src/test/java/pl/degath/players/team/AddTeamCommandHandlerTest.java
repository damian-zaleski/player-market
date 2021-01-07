package pl.degath.players.team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.players.FakeBankingService;
import pl.degath.players.TeamBuilder;
import pl.degath.players.port.ExternalBankingApi;
import pl.degath.players.port.InMemoryRepository;
import pl.degath.players.port.Repository;
import pl.degath.players.team.command.AddTeam;
import pl.degath.players.team.exception.TeamAlreadyExistsException;
import pl.degath.shared.infrastructure.CommandHandler;

import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AddTeamCommandHandlerTest {

    private Repository<Team> teamRepository;
    private CommandHandler<AddTeam> addTeamCommandHandler;

    @BeforeEach
    void setUp() {
        teamRepository = new InMemoryRepository<>();
        ExternalBankingApi fakeBankingService = new FakeBankingService();
        addTeamCommandHandler = new AddTeamCommandHandler(teamRepository, fakeBankingService, "1000");
    }

    @Test
    void addTeam_withUniqueTeamName_addsTeam() {
        var notExistingTeamName = "Fresh awesome team";
        var validCommand = new AddTeam(notExistingTeamName, Currency.getInstance("USD"));

        addTeamCommandHandler.handle(validCommand);

        assertThat(teamRepository.getAll()).hasSize(1);
    }


    @Test
    void addTeam_withNotUniqueTeamName_throwsException() {
        String existingTeamName = new TeamBuilder(teamRepository).inDb().getName();
        AddTeam validCommand = new AddTeam(existingTeamName, Currency.getInstance("USD"));

        Throwable thrown = catchThrowable(() -> addTeamCommandHandler.handle(validCommand));

        assertThat(thrown)
                .isInstanceOf(TeamAlreadyExistsException.class)
                .hasMessage("Team with name [Best Team Ever!] already exist.");
    }
}