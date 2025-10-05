package com.example.springcopylot.repository;

import java.util.concurrent.CompletableFuture;

public interface IGerericRepository<T> {
    CompletableFuture<T> saveAsync(T entity);
    CompletableFuture<T> findByIdAsync(Long id);
    CompletableFuture<T> deleteByIdAsync(Long id);
    CompletableFuture<Iterable<T>> findAllAsync();
    CompletableFuture<Long> countAsync();
}
