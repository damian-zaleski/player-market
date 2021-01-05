package pl.degath.transfer.port;

import pl.degath.transfer.Team;

import java.util.Optional;
import java.util.UUID;

public interface ExternalTeamApi {
    Optional<Team> get(UUID id);
}
