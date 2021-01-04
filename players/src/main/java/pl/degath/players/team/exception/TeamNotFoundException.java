package pl.degath.players.team.exception;

import java.util.UUID;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(UUID teamId) {
        super(String.format("Team with id [%s] does not exist.", teamId));
    }
}
