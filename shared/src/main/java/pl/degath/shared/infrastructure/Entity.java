package pl.degath.shared.infrastructure;

import java.util.UUID;

public abstract class Entity {

    private final UUID id;

    public Entity(UUID id) {
        this.id = id;
    }

    public Entity() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
