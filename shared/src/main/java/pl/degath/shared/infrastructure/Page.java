package pl.degath.shared.infrastructure;

import java.util.List;

public class Page<T> {
    private final List<T> result;
    private final int pagesCount;
    private final int currentPage;

    public Page(List<T> result, int pagesCount, int currentPage) {
        this.result = result;
        this.pagesCount = pagesCount;
        this.currentPage = currentPage;
    }

    public List<T> getResult() {
        return result;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}

