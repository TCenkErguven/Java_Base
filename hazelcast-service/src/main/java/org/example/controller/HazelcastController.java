package org.example.controller;

import org.example.dto.SaveRequestDto;
import org.example.service.HazelcastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hazel-cast")
public class HazelcastController {

    private final HazelcastService service;

    public HazelcastController(HazelcastService service){
        this.service = service;
    }


    @PostMapping("/save")
    public ResponseEntity<SaveRequestDto> save(SaveRequestDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/find-by-uuid/{uuid}")
    public ResponseEntity<SaveRequestDto> findByUUID(@PathVariable String uuid){
        return ResponseEntity.ok(service.findResponseByUUID(uuid));
    }

    @PostMapping("/save/hazel")
    public ResponseEntity<SaveRequestDto> hazelCastSave(SaveRequestDto dto) {
        return ResponseEntity.ok(service.saveWithTTL(dto));
    }
}
