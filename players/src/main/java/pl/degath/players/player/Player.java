package pl.degath.players.player;

import pl.degath.players.infrastructure.Entity;

import java.util.UUID;

public class Player extends Entity {
    private final String name;
    private final UUID teamId;

    public Player(String name, UUID teamId) {
        this.name = name;
        this.teamId = teamId;
    }
}
