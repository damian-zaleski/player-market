package pl.degath.transfer;

import pl.degath.transfer.port.ExternalPlayerApi;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class FakeExternalPlayerApi implements ExternalPlayerApi {

    private Set<Player> players = new HashSet<>();

    Player add(Player player) {
        players.add(player);
        return player;
    }

    @Override
    public Optional<Player> get(UUID id) {
        return players.stream()
                .filter(player -> player.getId().equals(id))
                .findFirst();
    }

    @Override
    public void changeTeam(UUID playerId, UUID teamId) {
    }
}
