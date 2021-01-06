package pl.degath.players.port;

import pl.degath.shared.infrastructure.Entity;
import pl.degath.shared.infrastructure.Page;
import pl.degath.shared.infrastructure.Pagination;

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
    public Page<T> getAll(Pagination pagination) {
        int size = entities.size();
        Pagination.IndexRange range = pagination.calculateRange(size);
        List<T> result = getPaginatedResult(range);
        return pagination.result(result, size);
    }

    private List<T> getPaginatedResult(Pagination.IndexRange range) {
        return Optional.ofNullable(range)
                .filter(r -> r.getFirst() <= r.getLast())
                .map(indexRange -> getAll()
                        .subList(range.getFirst(), range.getLast()))
                .orElse(List.of());
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