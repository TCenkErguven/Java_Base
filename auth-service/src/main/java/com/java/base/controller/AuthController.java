package com.java.base.controller;

import com.java.base.dto.request.AuthRegisterRequestDto;
import com.java.base.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.java.base.constant.APIUrls.AUTH;
import static com.java.base.constant.APIUrls.REGISTER_VISITOR;

@RestController
@RequestMapping(AUTH)
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService){
        this.authService = authService;
    }

    @PostMapping(REGISTER_VISITOR)
    public ResponseEntity<Boolean> registerVisitor(HttpServletRequest request, @RequestBody AuthRegisterRequestDto dto) {
        HttpSession session = request.getSession();
        System.out.println(session.toString());
        return ResponseEntity.ok(authService.registerVisitor(dto));
    }
}
