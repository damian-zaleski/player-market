package pl.degath.players.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.degath.players.PlayerBuilder;
import pl.degath.shared.infrastructure.CommandHandler;
import pl.degath.players.player.command.RemovePlayer;
import pl.degath.players.port.InMemoryRepository;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RemovePlayerCommandHandlerTest {


    private Repository<Player> playerRepository;
    private Repository<Team> teamRepository;
    private CommandHandler<RemovePlayer> removePlayerHandler;

    @BeforeEach
    void setUp() {
        playerRepository = new InMemoryRepository<>();
        teamRepository = new InMemoryRepository<>();
        removePlayerHandler = new RemovePlayerCommandHandler(playerRepository);
    }

    @Test
    void removePlayer_withExistingPlayerId_removesPlayer() {
        UUID existingPlayerId = this.existingPlayer().getId();

        removePlayerHandler.handle(new RemovePlayer(existingPlayerId));

        assertThat(playerRepository.getAll()).hasSize(0);
    }

    @Test
    void removePlayer_withNotExistingPlayerId_doesNotRemovePlayer() {
        this.existingPlayer();
        UUID notExistingPlayerId = UUID.fromString("ac8fad99-43c5-4c1a-90e1-b9e8c12bbfae");

        removePlayerHandler.handle(new RemovePlayer(notExistingPlayerId));

        assertThat(playerRepository.getAll()).hasSize(1);
    }

    private Player existingPlayer() {
        return new PlayerBuilder(playerRepository, teamRepository)
                .withName("Robert Lewandowski")
                .inDb();
    }
}