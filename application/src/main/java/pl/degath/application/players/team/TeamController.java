package pl.degath.application.players.team;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.degath.players.team.AddTeamCommandHandler;
import pl.degath.players.team.GetPlayersTeamsQueryHandler;
import pl.degath.players.team.PlayersTeamQueryResult;
import pl.degath.players.team.RemoveTeamCommandHandler;
import pl.degath.players.team.UpdateTeamCommandHandler;
import pl.degath.players.team.command.RemoveTeam;
import pl.degath.players.team.command.UpdateTeam;
import pl.degath.players.team.query.GetPlayersTeams;

import java.util.Set;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/teams")
public class TeamController {

    private final AddTeamCommandHandler addTeamCommandHandler;
    private final GetPlayersTeamsQueryHandler getPlayersTeamsQueryHandler;
    private final RemoveTeamCommandHandler removeTeamCommandHandler;
    private final UpdateTeamCommandHandler updateTeamCommandHandler;

    public TeamController(AddTeamCommandHandler addTeamCommandHandler, GetPlayersTeamsQueryHandler getPlayersTeamsQueryHandler, RemoveTeamCommandHandler removeTeamCommandHandler, UpdateTeamCommandHandler updateTeamCommandHandler) {
        this.addTeamCommandHandler = addTeamCommandHandler;
        this.getPlayersTeamsQueryHandler = getPlayersTeamsQueryHandler;
        this.removeTeamCommandHandler = removeTeamCommandHandler;
        this.updateTeamCommandHandler = updateTeamCommandHandler;
    }

    @PostMapping
    public void add(@RequestBody AddTeamRequest request) {
        addTeamCommandHandler.handle(request.toCommand());
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") UUID uuid) {
        removeTeamCommandHandler.handle(new RemoveTeam(uuid));
    }

    @GetMapping
    public Set<PlayersTeamQueryResult> get(@RequestParam Set<UUID> playersIds) {
        return getPlayersTeamsQueryHandler.handle(new GetPlayersTeams(playersIds));
    }

    @PutMapping("{id}")
    public void update(@PathVariable("id") UUID uuid,
                       @RequestBody UpdateTeamRequest request) {
        updateTeamCommandHandler.handle(new UpdateTeam(uuid, request.getTeamName()));
    }
}
