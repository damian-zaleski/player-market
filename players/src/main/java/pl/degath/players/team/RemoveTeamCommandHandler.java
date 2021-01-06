package pl.degath.players.team;

import pl.degath.shared.infrastructure.CommandHandler;
import pl.degath.players.player.Player;
import pl.degath.players.port.Repository;
import pl.degath.players.team.command.RemoveTeam;
import pl.degath.players.team.exception.TeamHasAssignedPlayersException;

import java.util.Objects;
import java.util.UUID;

public class RemoveTeamCommandHandler implements CommandHandler<RemoveTeam> {
    private final Repository<Team> teamRepository;
    private final Repository<Player> playerRepository;

    public RemoveTeamCommandHandler(Repository<Team> teamRepository, Repository<Player> playerRepository) {
        this.teamRepository = Objects.requireNonNull(teamRepository, "TeamRepository has to be specified");
        this.playerRepository = Objects.requireNonNull(playerRepository, "PlayerRepository has to be specified");
    }

    @Override
    public void handle(RemoveTeam command) {
        this.validateThatPlayersAreNotAssignedToThisTeam(command.getTeamId());
        teamRepository.remove(command.getTeamId());
    }

    private void validateThatPlayersAreNotAssignedToThisTeam(UUID teamId) {
        if (this.playersAreAssignedToThisTeam(teamId)) {
            throw new TeamHasAssignedPlayersException(teamId);
        }
    }

    private boolean playersAreAssignedToThisTeam(UUID teamId) {
        return playerRepository.existsBy(player -> player.getTeamId().equals(teamId));
    }
}
