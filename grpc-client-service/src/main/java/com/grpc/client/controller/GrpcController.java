package com.grpc.client.controller;

import com.grpc.client.dto.SaveRequestDto;
import com.grpc.client.proto.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grpc-client")
public class GrpcController {

    @GrpcClient("greetingService")
    private SimpleGrpc.SimpleBlockingStub simpleBlockingStub;

    @GrpcClient("duplicateService")
    private DuplicateGrpc.DuplicateBlockingStub duplicateBlockingStub;

    @PostMapping("/hello")
    public ResponseEntity<String> sayHello() {
        HelloWorldResponse response = null;
        try{
            response = simpleBlockingStub.sayHello(HelloWorldRequest.newBuilder().setName("sadsadsads").build());
        }catch (Exception e){
            System.out.println(e);
        }
        return ResponseEntity.ok(response != null ? response.getGreeting() : null);
    }

    @PostMapping("/save")
    public ResponseEntity<SaveRequestDto> save(@RequestBody SaveRequestDto dto) {
        SaveRequest grpcResponse = null;
        try{
            grpcResponse = duplicateBlockingStub.saveMessage(SaveRequest.newBuilder()
                    .setMessage(dto.getMessage())
                    .setUuid(dto.getUuid())
                    .build());

        }catch (Exception e){
            System.out.println(e);
        }

        SaveRequestDto response = new SaveRequestDto();
        if(grpcResponse != null){
            response.setMessage(grpcResponse.getMessage());
            response.setUuid(grpcResponse.getUuid());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find-by-uuid/{uuid}")
    public ResponseEntity<SaveRequestDto> findByUUID(@PathVariable String uuid){
        SaveRequest grpcResponse = null;
        try{
            grpcResponse = duplicateBlockingStub.queryForMessage(QueryRequest.newBuilder()
                    .setUuid(uuid)
                    .build());

        }catch (Exception e){
            System.out.println(e);
        }

        SaveRequestDto response = new SaveRequestDto();
        if(grpcResponse != null){
            response.setMessage(grpcResponse.getMessage());
            response.setUuid(grpcResponse.getUuid());
        }

        return ResponseEntity.ok(response);
    }


}
