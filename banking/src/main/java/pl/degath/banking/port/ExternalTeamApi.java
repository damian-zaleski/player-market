package pl.degath.banking.port;

import java.util.UUID;

public interface ExternalTeamApi {

    boolean existsBy(UUID id);
}
