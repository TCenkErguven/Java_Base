package com.java.base.service;

import com.java.base.dto.request.AuthRegisterRequestDto;
import com.java.base.model.Auth;
import com.java.base.repository.AuthRepository;
import com.java.base.repository.BaseService;
import com.java.base.security.Password;

import java.util.UUID;

public class AuthService extends BaseService<Auth, UUID> {
    private final AuthRepository repository;
    private final Password passwordEncoder;

    public AuthService(AuthRepository authRepository,
                       Password passwordEncoder){
        super(authRepository);
        this.repository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(AuthRegisterRequestDto dto){}


}
