package pl.degath.players.infrastructure;

public interface QueryHandler<T extends Query, R> {
    R handle(T query);
}
