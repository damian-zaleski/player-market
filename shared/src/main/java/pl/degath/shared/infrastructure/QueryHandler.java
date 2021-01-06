package pl.degath.shared.infrastructure;

public interface QueryHandler<T extends Query, R> {
    R handle(T query);
}
