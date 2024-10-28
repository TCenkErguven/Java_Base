package com.java.base.reposityory.base;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseDao<T, ID> {
    T save(T t);
    Iterable<T> saveAll(Iterable<T> t);
    T update(T t);
    void delete(T t);
    void deleteById(ID id);
    List<T> findAll();
    Optional<T> findById(ID id);

    Optional<T> findById(UUID id);
}
