package pl.degath.shared.infrastructure;

import java.util.List;

public class Pagination {

    public final static Pagination DEFAULT_PAGINATION = withDefaultSize();
    private final int itemsPerPage;
    private final int pageNumber;

    private Pagination(int itemsPerPage, int pageNumber) {
        this.itemsPerPage = itemsPerPage;
        this.pageNumber = pageNumber;
    }

    public static Pagination withDefaultSize() {
        int defaultPageSize = 20;
        int defaultPage = 1;
        return new Pagination(defaultPageSize, defaultPage);
    }

    public static Pagination perPage(int count, int requestedPage) {
        return new Pagination(count, requestedPage);
    }

    public <T> Page<T> result(List<T> result, int wholeSize) {
        int pagesCount = (int) Math.ceil((double) wholeSize / itemsPerPage);
        return new Page<>(result, pagesCount, pageNumber);
    }

    public IndexRange calculateRange(int size) {
        int firstIndex = calculateFirstIndex();
        int lastIndex = calculateLastIndex(firstIndex, size);
        return new IndexRange(firstIndex, lastIndex);
    }

    private int calculateFirstIndex() {
        if (pageNumber == 1) {
            return 0;
        }
        return itemsPerPage * (pageNumber - 1);
    }

    private int calculateLastIndex(int firstIndex, int size) {
        if (itemsPerPage > size) {
            return size;
        }
        int lastIndexBasedOnFirst = firstIndex + itemsPerPage;
        return Math.min(lastIndexBasedOnFirst, size);
    }

    public static class IndexRange {
        private final int first;
        private final int last;

        IndexRange(int first, int last) {
            this.first = first;
            this.last = last;
        }

        public int getFirst() {
            return first;
        }

        public int getLast() {
            return last;
        }
    }
}