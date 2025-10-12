package com.example.springcopylot.pagination;

import java.util.Collection;

public interface IPagedList<T> extends Collection<T> {
    int getCurrentPage();
    int getTotalPages();
    int getPageSize();
    int getTotalCount();
}
