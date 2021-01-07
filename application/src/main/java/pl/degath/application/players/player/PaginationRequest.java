package pl.degath.application.players.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaginationRequest {
    private final Integer size;
    private final Integer pageNumber;

    @JsonCreator
    public PaginationRequest(@JsonProperty("size") Integer size,
                             @JsonProperty("pageNumber") Integer pageNumber) {
        this.size = size;
        this.pageNumber = pageNumber;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

}
