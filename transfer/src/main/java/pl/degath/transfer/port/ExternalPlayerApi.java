package pl.degath.transfer.port;

import pl.degath.transfer.Player;

import java.util.Optional;
import java.util.UUID;

public interface ExternalPlayerApi {
    Optional<Player> get(UUID id);

    void changeTeam(UUID playerId, UUID teamId);
}
