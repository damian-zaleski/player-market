package pl.degath.players.team;

import pl.degath.players.port.Repository;
import pl.degath.players.team.command.AddTeam;
import pl.degath.players.team.exception.TeamAlreadyExistsException;
import pl.degath.shared.infrastructure.CommandHandler;

import java.util.Objects;
import java.util.UUID;

public class AddTeamCommandHandler implements CommandHandler<AddTeam> {

    private final Repository<Team> teamRepository;

    public AddTeamCommandHandler(Repository<Team> teamRepository) {
        this.teamRepository = Objects.requireNonNull(teamRepository, "Team repository has to be specified.");
    }

    @Override
    public void handle(AddTeam command) {
        this.validateUniqueName(command.getTeamName());

        Team newTeam = new Team(UUID.randomUUID(), command.getTeamName(), command.getCurrency());
        teamRepository.save(newTeam);
    }

    private void validateUniqueName(String teamName) {
        if (teamRepository.existsBy(team -> team.getName().equals(teamName))) {
            throw new TeamAlreadyExistsException(String.format("Team with name [%s] already exist.", teamName));
        }
    }
}
