package pl.degath.players.team.exception;

import java.util.UUID;

public class TeamHasAssignedPlayersException extends RuntimeException {
    public TeamHasAssignedPlayersException(UUID teamId) {
        super(String.format("Cannot remove team [%s] with players.", teamId));
    }
}