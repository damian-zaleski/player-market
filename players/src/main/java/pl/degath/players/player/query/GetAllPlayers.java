package pl.degath.players.player.query;

import pl.degath.shared.infrastructure.Pagination;
import pl.degath.shared.infrastructure.Query;

public class GetAllPlayers implements Query {
    private final Pagination pagination;

    public GetAllPlayers(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {
        if (pagination != null) {
            return pagination;
        }
        return Pagination.withDefaultSize();
    }
}

