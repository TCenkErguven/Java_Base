package com.hazelcast.repository;

import java.util.List;
import java.util.Optional;

public interface ICustomRepository<T> {
    Optional<T> findById(String id);

    Optional<T> findByUUId(String uuid);

    List<T> findAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}