package com.java.base.service.impl;

import com.java.base.model.Custom;
import com.java.base.repository.CustomRepository;
import com.java.base.service.CustomService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;


@Service
public class CustomServiceImpl implements CustomService {
    private final CustomRepository repository;

    public CustomServiceImpl(CustomRepository repository){
        this.repository = repository;
    }

    public void save(Custom custom){
        repository.save(custom);
    }

    @Override
    public Custom findById(UUID id) {
        return repository.findById(id).orElseThrow(()->new NotFoundException("Not FOund"));
    }


}
