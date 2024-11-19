package com.hazelcast.controller;

import com.hazelcast.dto.SaveRequestDto;
import com.hazelcast.service.CustomService;
import com.hazelcast.service.HazelcastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hazel-cast")
public class HazelcastController {

    private final HazelcastService service;
    private final CustomService customService;

    public HazelcastController(HazelcastService service, CustomService customService){
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
