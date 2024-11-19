package com.hazelcast.controller;

import com.hazelcast.service.JdbcService;
import com.hazelcast.service.HazelcastService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hazel-cast")
public class HazelcastController {

    private final HazelcastService service;
    private final JdbcService customService;

    public HazelcastController(HazelcastService service, JdbcService customService){
        this.service = service;
        this.customService = customService;
    }


    /*
    @PostMapping("/save/hazel")
    public ResponseEntity<SaveRequestDto> hazelCastSave(@RequestBody SaveRequestDto dto) {
        return ResponseEntity.ok(service.saveWithTTL(dto));
    }
    */

    @DeleteMapping("/delete-all")
    public void deleteAll(){
        customService.deleteAll();
    }
}
