package pl.degath.players.team;

import pl.degath.players.infrastructure.QueryHandler;
import pl.degath.players.player.Player;
import pl.degath.players.player.exception.PlayerNotFoundException;
import pl.degath.players.port.Repository;
import pl.degath.players.team.exception.TeamNotFoundException;
import pl.degath.players.team.query.GetPlayersTeams;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetPlayersTeamsQueryHandler implements QueryHandler<GetPlayersTeams, Set<PlayersTeamQueryResult>> {

    private final Repository<Team> teamRepository;
    private final Repository<Player> playerRepository;

    public GetPlayersTeamsQueryHandler(Repository<Team> teamRepository, Repository<Player> playerRepository) {
        this.teamRepository = Objects.requireNonNull(teamRepository, "Team repository has to be specified");
        this.playerRepository = Objects.requireNonNull(playerRepository, "Team repository has to be specified");
    }

    @Override
    public Set<PlayersTeamQueryResult> handle(GetPlayersTeams query) {
        return query.getPlayerIds()
                .stream()
                .map(this::getPlayer)
                .map(this::toQueryResult)
                .collect(Collectors.toSet());
    }

    private Player getPlayer(UUID id) {
        return playerRepository.get(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }

    private PlayersTeamQueryResult toQueryResult(Player player) {
        Team team = Objects.isNull(player.getTeamId()) ? null : getTeam(player.getTeamId());
        return new PlayersTeamQueryResult(player.getId(), team);
    }

    private Team getTeam(UUID teamId) {
        return teamRepository.get(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));
    }
}
