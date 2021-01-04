package pl.degath.players.team;

import pl.degath.players.infrastructure.CommandHandler;
import pl.degath.players.port.Repository;
import pl.degath.players.team.command.UpdateTeam;
import pl.degath.players.team.exception.TeamAlreadyExistsException;
import pl.degath.players.team.exception.TeamNotFoundException;

import java.util.Objects;
import java.util.Optional;

public class UpdateTeamCommandHandler implements CommandHandler<UpdateTeam> {

    private final Repository<Team> teamRepository;

    public UpdateTeamCommandHandler(Repository<Team> teamRepository) {
        this.teamRepository = Objects.requireNonNull(teamRepository, "Team repository has to be specified.");
    }

    @Override
    public void handle(UpdateTeam command) {
        Objects.requireNonNull(command.getTeamId(), "Team id to update has to be specified.");

        Team updatedTeam = this.updateTeam(command);
        teamRepository.save(updatedTeam);
    }

    private Team updateTeam(UpdateTeam command) {
        Team teamToUpdate = teamRepository.get(command.getTeamId())
                .orElseThrow(() -> new TeamNotFoundException(command.getTeamId()));

        Optional.ofNullable(command.getTeamName())
                .map(this::validateTeamNameUniqueness)
                .ifPresent(teamToUpdate::setName);

        return teamToUpdate;
    }

    private String validateTeamNameUniqueness(String teamName) {
        if (teamRepository.existsBy(team -> team.getName().equals(teamName))) {
            throw new TeamAlreadyExistsException(String.format("Team with name [%s] already exist.", teamName));
        }
        return teamName;
    }
}