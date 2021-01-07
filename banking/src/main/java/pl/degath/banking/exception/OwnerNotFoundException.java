package pl.degath.banking.exception;

import java.util.UUID;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(UUID ownerId) {
        super(String.format("Owner (player or team) with id [%s] does not exist.", ownerId));
    }
}
