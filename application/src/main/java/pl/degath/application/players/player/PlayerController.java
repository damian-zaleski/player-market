package pl.degath.application.players.player;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.degath.players.player.AddPlayerCommandHandler;
import pl.degath.players.player.GetAllPlayersQueryHandler;
import pl.degath.players.player.Player;
import pl.degath.players.player.RemovePlayerCommandHandler;
import pl.degath.players.player.UpdatePlayerCommandHandler;
import pl.degath.players.player.command.RemovePlayer;
import pl.degath.players.player.command.UpdatePlayer;
import pl.degath.players.player.query.GetAllPlayers;
import pl.degath.shared.infrastructure.Page;
import pl.degath.shared.infrastructure.Pagination;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/players")
@Api("Player operations")
public class PlayerController {

    private final AddPlayerCommandHandler addPlayerCommandHandler;
    private final RemovePlayerCommandHandler removePlayerCommandHandler;
    private final GetAllPlayersQueryHandler getAllPlayersQueryHandler;
    private final UpdatePlayerCommandHandler updatePlayerCommandHandler;

    public PlayerController(AddPlayerCommandHandler addPlayerCommandHandler,
                            RemovePlayerCommandHandler removePlayerCommandHandler,
                            GetAllPlayersQueryHandler getAllPlayersQueryHandler,
                            UpdatePlayerCommandHandler updatePlayerCommandHandler) {
        this.addPlayerCommandHandler = addPlayerCommandHandler;
        this.removePlayerCommandHandler = removePlayerCommandHandler;
        this.getAllPlayersQueryHandler = getAllPlayersQueryHandler;
        this.updatePlayerCommandHandler = updatePlayerCommandHandler;
    }

    @PostMapping
    @ApiOperation("Add a new player.")
    public void addPlayer(@RequestBody AddPlayerRequest addPlayerRequest) {
        addPlayerCommandHandler.handle(addPlayerRequest.toCommand());
    }

    //todo consider Patch instead of put
    @PutMapping("{id}")
    @ApiOperation("Edit existing player.")
    public void updatePlayer(@PathVariable("id") UUID playerId,
                             @RequestBody UpdatePlayerRequest request) {
        updatePlayerCommandHandler.handle(new UpdatePlayer(playerId, request.getPlayerName(), request.getTeamId()));
    }

    @DeleteMapping("{id}")
    @ApiOperation("Remove existing player.")
    public void removePlayer(@PathVariable("id") UUID uuid) {
        removePlayerCommandHandler.handle(new RemovePlayer(uuid));
    }

    @GetMapping
    @ApiOperation("Fetch all players.")
    public Page<Player> getAllPlayers(PaginationRequest paginationRequest) {
        Pagination pagination = PaginationProxy.from(paginationRequest).toDomain();
        return getAllPlayersQueryHandler.handle(new GetAllPlayers(pagination));
    }
}
