package pl.degath.players.team;

import pl.degath.players.port.ExternalBankingApi;
import pl.degath.players.port.Repository;
import pl.degath.players.team.command.AddTeam;
import pl.degath.players.team.exception.TeamAlreadyExistsException;
import pl.degath.shared.infrastructure.CommandHandler;
import pl.degath.shared.infrastructure.Money;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class AddTeamCommandHandler implements CommandHandler<AddTeam> {

    private final Repository<Team> teamRepository;
    private final ExternalBankingApi externalBankingApi;
    private final String initialAmount;

    public AddTeamCommandHandler(Repository<Team> teamRepository, ExternalBankingApi externalBankingApi, String initialAmount) {
        this.teamRepository = Objects.requireNonNull(teamRepository, "Team repository has to be specified.");
        this.externalBankingApi = Objects.requireNonNull(externalBankingApi, "External banking api has to be specified.");
        this.initialAmount = initialAmount;
    }

    @Override
    public void handle(AddTeam command) {
        this.validateUniqueName(command.getTeamName());

        Team newTeam = new Team(UUID.randomUUID(), command.getTeamName(), command.getCurrency());
        Team createdTeam = teamRepository.save(newTeam);
        externalBankingApi.createAccount(createdTeam.getId(), new Money(new BigDecimal(initialAmount), command.getCurrency()));
    }

    private void validateUniqueName(String teamName) {
        if (teamRepository.existsBy(team -> team.getName().equals(teamName))) {
            throw new TeamAlreadyExistsException(String.format("Team with name [%s] already exist.", teamName));
        }
    }
}
