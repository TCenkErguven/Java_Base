package com.java.base.service.impl;

import com.java.base.dto.request.AuthRegisterRequestDto;

public interface AuthServiceImpl {

    Boolean registerVisitor(AuthRegisterRequestDto dto);

}
