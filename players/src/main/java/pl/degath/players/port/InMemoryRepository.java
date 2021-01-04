package pl.degath.players.port;

import pl.degath.players.infrastructure.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class InMemoryRepository<T extends Entity> implements Repository<T> {

    private final Map<UUID, T> entities;

    public InMemoryRepository() {
        this.entities = new HashMap<>();
    }

    @Override
    public List<T> getAll() {
        return List.copyOf(entities.values());
    }

    @Override
    public T save(T entity) {
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void remove(UUID id) {
        entities.remove(id);
    }

    @Override
    public Optional<T> get(UUID id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public boolean existsBy(Predicate<T> predicate) {
        return entities.values()
                .stream()
                .anyMatch(predicate);
    }
}
