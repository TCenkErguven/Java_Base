package com.java.base.repository;

import com.java.base.model.base.BaseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseService<T extends BaseModel, ID> implements BaseCrud<T, ID> {
    private final JpaRepository<T, ID> repository;

    @Override
    public T save(T t) {
        t.setCreated(System.currentTimeMillis());
        t.setUpdated(System.currentTimeMillis());
        return repository.save(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {
        t.forEach(x -> {
            x.setCreated(System.currentTimeMillis());
            x.setUpdated(System.currentTimeMillis());
        });
        return repository.saveAll(t);
    }

    @Override
    public T update(T t) {
        t.setUpdated(System.currentTimeMillis());
        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        t.setDeleted(System.currentTimeMillis());
        repository.saveAndFlush(t);
    }

    @Override
    public void deleteById(ID id) {
        T t = findById(id).orElseThrow(RuntimeException::new);
        delete(t);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }
}

