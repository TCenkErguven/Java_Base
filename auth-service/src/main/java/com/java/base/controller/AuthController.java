package com.java.base.controller;

import com.java.base.dto.request.AuthRegisterRequestDto;
import com.java.base.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Boolean> registerVisitor(HttpSession session, @RequestBody AuthRegisterRequestDto dto) {
        session.setAttribute("as","sda");
        return ResponseEntity.ok(authService.registerVisitor(dto));
    }
}
