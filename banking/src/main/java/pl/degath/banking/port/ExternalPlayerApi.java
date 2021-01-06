package pl.degath.banking.port;

import java.util.UUID;

public interface ExternalPlayerApi {

    boolean existsBy(UUID id);
}
