package com.java.base.service;

import com.java.base.model.Custom;

import java.util.UUID;

public interface CustomService {
    void save(Custom custom);
    Custom findById(UUID id);
}
