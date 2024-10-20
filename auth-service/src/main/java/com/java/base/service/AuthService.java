package com.java.base.service;

import com.java.base.dto.request.AuthRegisterRequestDto;
import com.java.base.model.Auth;
import com.java.base.repository.AuthRepository;
import com.java.base.repository.Base.BaseService;
import com.java.base.security.Password;
import com.java.base.service.impl.AuthServiceImpl;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService extends BaseService<Auth, UUID> implements AuthServiceImpl {
    private final AuthRepository repository;
    private final Password passwordEncoder;

    public AuthService(AuthRepository authRepository,
                       Password passwordEncoder){
        super(authRepository);
        this.repository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean registerVisitor(AuthRegisterRequestDto dto){
        return true;
    }


}
