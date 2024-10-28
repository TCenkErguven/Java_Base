package com.java.base.controller;

import com.java.base.dto.request.SaveCustomRequestDto;
import com.java.base.model.Custom;
import com.java.base.service.CustomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/custom")
public class CustomController {
    private final CustomService service;

    public CustomController(CustomService service){
        this.service = service;
    }


    @PostMapping("/save")
    public ResponseEntity<String> saveCustom(@RequestBody Custom custom) {
        custom.setId(UUID.randomUUID());
        service.save(custom);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/find-by-id/{id}")
    public ResponseEntity<Custom> findById(@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }
}
