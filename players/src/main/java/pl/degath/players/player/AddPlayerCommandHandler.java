package pl.degath.players.player;

import pl.degath.players.player.command.AddPlayer;
import pl.degath.players.player.exception.PlayerAlreadyExistsException;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;
import pl.degath.players.team.exception.TeamNotFoundException;
import pl.degath.shared.infrastructure.CommandHandler;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class AddPlayerCommandHandler implements CommandHandler<AddPlayer> {

    private final Repository<Player> playerRepository;
    private final Repository<Team> teamRepository;

    public AddPlayerCommandHandler(Repository<Player> playerRepository, Repository<Team> teamRepository) {
        this.playerRepository = Objects.requireNonNull(playerRepository, "Player repository cannot be null.");
        this.teamRepository = Objects.requireNonNull(teamRepository, "Team repository cannot be null.");
    }

    @Override
    public void handle(AddPlayer command) {
        this.validatePlayerName(command.getName());
        this.validateTeamExistenceIfSpecified(command.getTeamId());
        //todo validate year of birth. probably 18+
        //todo validate career start. probably after birth before today

        Player newPlayer = new Player(command.getName(), command.getTeamId(), command.getYearOfBirth(), command.getCareerStart());
        playerRepository.save(newPlayer);
    }

    private void validatePlayerName(String playerName) {
        if (playerRepository.existsBy(player -> player.getName().equals(playerName))) {
            throw new PlayerAlreadyExistsException(playerName);
        }
    }

    private void validateTeamExistenceIfSpecified(UUID teamId) {
        Optional.ofNullable(teamId)
                .ifPresent(this::validateTeamExistence);
    }

    private void validateTeamExistence(UUID teamId) {
        if (!teamRepository.existsBy(team -> team.getId().equals(teamId))) {
            throw new TeamNotFoundException(teamId);
        }
    }
}
