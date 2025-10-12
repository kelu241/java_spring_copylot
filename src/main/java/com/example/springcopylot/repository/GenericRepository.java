package com.example.springcopylot.repository;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.springcopylot.filter.Filter;
import com.example.springcopylot.pagination.PagedList;

@Component
public abstract class GenericRepository<T> implements IGerericRepository<T> {

    private Filter<T> criteria;

    public GenericRepository() {
         this.criteria = new Filter<T>(); // Inicializa com Filter concreto
    }



    protected abstract JpaRepository<T, Long> getRepository();

    @Override
    @Async
    public CompletableFuture<T> saveAsync(T entity) {
        T result = getRepository().save(entity);
        return CompletableFuture.completedFuture(result);
    }

    @Override
    @Async
    public CompletableFuture<T> findByIdAsync(Long id) {
        return CompletableFuture.completedFuture(getRepository().findById(id).orElse(null));
    }

    @Override
    @Async
    public CompletableFuture<T> deleteByIdAsync(Long id) {
        T entity = getRepository().findById(id).orElse(null);
        getRepository().deleteById(id);
        return CompletableFuture.completedFuture(entity);
    }

    @Override
    @Async
    public CompletableFuture<Iterable<T>> findAllAsync() {
        return CompletableFuture.completedFuture(getRepository().findAll());
    }

    @Override
    @Async
    public CompletableFuture<Long> countAsync() {
        return CompletableFuture.completedFuture(getRepository().count());
    }

    @Override
    @Async
    public CompletableFuture<PagedList<T>> paginateAsync(int pageNumber, int pageSize) {
        List<T> list = getRepository().findAll(); // Retorna List<T> diretamente
        return CompletableFuture.completedFuture(PagedList.toPagedList(list, pageNumber, pageSize));
    }

    @Override
    @Async
    public CompletableFuture<Iterable<T>> searchAsync(Predicate<T> search) {
        List<T> list = getRepository().findAll(); // Retorna List<T> diretamente
        return CompletableFuture.completedFuture(criteria.where(list, search));
    }

}
