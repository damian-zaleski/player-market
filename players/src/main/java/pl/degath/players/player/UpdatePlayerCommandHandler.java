package pl.degath.players.player;

import pl.degath.players.infrastructure.CommandHandler;
import pl.degath.players.player.command.UpdatePlayer;
import pl.degath.players.player.exception.PlayerAlreadyExistsException;
import pl.degath.players.player.exception.PlayerNotFoundException;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;
import pl.degath.players.team.exception.TeamNotFoundException;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UpdatePlayerCommandHandler implements CommandHandler<UpdatePlayer> {

    private final Repository<Player> playerRepository;
    private final Repository<Team> teamRepository;

    public UpdatePlayerCommandHandler(Repository<Player> playerRepository, Repository<Team> teamRepository) {
        this.playerRepository = Objects.requireNonNull(playerRepository, "PlayerRepository cannot be null.");
        this.teamRepository = Objects.requireNonNull(teamRepository, "TeamRepository cannot be null.");
    }

    @Override
    public void handle(UpdatePlayer command) {
        Objects.requireNonNull(command.getPlayerId(), "Player id has to be specified.");

        Player updatedPlayer = updatePlayer(command);
        playerRepository.save(updatedPlayer);
    }

    private Player updatePlayer(UpdatePlayer command) {
        Player playerToEdit = playerRepository.get(command.getPlayerId())
                .orElseThrow(() -> new PlayerNotFoundException(command.getPlayerId()));

        Optional.ofNullable(command.getTeamId())
                .map(this::validateTeamExistence)
                .ifPresent(playerToEdit::setTeamId);

        Optional.ofNullable(command.getPlayerName())
                .map(this::validatePlayerNameUniquness)
                .ifPresent(playerToEdit::setName);

        return playerToEdit;
    }

    private UUID validateTeamExistence(UUID teamId) {
        if (teamNotExist(teamId)) {
            throw new TeamNotFoundException(teamId);
        }
        return teamId;
    }

    private boolean teamNotExist(UUID uuid) {
        return !teamRepository.existsBy(team -> team.getId().equals(uuid));
    }


    private String validatePlayerNameUniquness(String playerName) {
        if (playerNameIsTaken(playerName)) {
            throw new PlayerAlreadyExistsException(playerName);
        }
        return playerName;
    }

    private boolean playerNameIsTaken(String playerName) {
        return playerRepository.existsBy(player -> player.getName().equals(playerName));
    }
}
