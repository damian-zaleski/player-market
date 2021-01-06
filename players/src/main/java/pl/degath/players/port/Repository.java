package pl.degath.players.port;

import pl.degath.shared.infrastructure.Entity;
import pl.degath.shared.infrastructure.Page;
import pl.degath.shared.infrastructure.Pagination;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public interface Repository<T extends Entity> {

    Optional<T> get(UUID id);

    List<T> getAll();

    Page<T> getAll(Pagination pagination);

    T save(T entity);

    void remove(UUID id);

    boolean existsBy(Predicate<T> predicate);
}
