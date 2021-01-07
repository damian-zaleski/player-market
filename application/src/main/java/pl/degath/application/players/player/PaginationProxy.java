package pl.degath.application.players.player;

import pl.degath.shared.infrastructure.Pagination;

import static pl.degath.shared.infrastructure.Pagination.DEFAULT_PAGINATION;

public class PaginationProxy {
    private final Pagination pagination;

    private PaginationProxy(Pagination pagination) {
        this.pagination = pagination;
    }

    public static PaginationProxy from(PaginationRequest request) {
        if (request == null) {
            return new PaginationProxy(DEFAULT_PAGINATION);
        }
        Pagination domainPagination = Pagination.perPage(
                onNull(request.getSize(), DEFAULT_PAGINATION.getItemsPerPage()),
                onNull(request.getPageNumber(), DEFAULT_PAGINATION.getPageNumber()));
        return new PaginationProxy(domainPagination);
    }

    private static int onNull(Integer value, int defaultValue) {
        if (value != null) {
            return value;
        }
        return defaultValue;
    }

    public Pagination toDomain() {
        return pagination;
    }
}
