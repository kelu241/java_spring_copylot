package com.example.springcopylot.pagination;

public class PagedList<T> implements List<T>{
    public int currentPage;
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int totalPages;
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int pageSize;
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public bool HasPrevious() {
        return CurrentPage > 1;
    }

    public bool HasNext() {

        return CurrentPage < TotalPages;
    }

    public PagedList(List<T> items, int count, int pageNumber, int pageSize) {
        setTotalCount(count);
        setPageSize(pageSize);
        setCurrentPage(pageNumber);
        setTotalPages((int) Math.Ceiling(count / (double) pageSize));

        AddRange(items);
    }

    public static PagedList<T> ToPagedList(IQueryable<T> source, int pageNumber, int pageSize) {
        var count = source.Count();
        var items = source.Skip((pageNumber - 1) * pageSize).Take(pageSize).ToList();

        return new PagedList<T>(items, count, pageNumber, pageSize);
    }
}
