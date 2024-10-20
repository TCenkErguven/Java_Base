package com.java.base.repository.Base;

import java.util.List;
import java.util.Optional;

public interface BaseCrud<T, ID> {
    T save(T t);
    Iterable<T> saveAll(Iterable<T> t);
    T update(T t);
    void delete(T t);
    void deleteById(ID id);
    List<T> findAll();
    Optional<T> findById(ID id);
}
