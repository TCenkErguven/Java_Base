package com.grpc.client.controller;

import com.grpc.client.dto.FindByUUIDResponseDto;
import com.grpc.client.dto.SaveRequestDto;
import com.grpc.client.dto.SaveResponseDto;
import com.grpc.client.proto.*;
import com.grpc.client.service.GrpcService;
import com.hazelcast.server.proto.DuplicateGrpc;
import com.hazelcast.server.proto.ResponseObject;
import com.hazelcast.server.proto.ResponseWrapper;
import com.hazelcast.server.proto.SaveRequest;
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

    private final GrpcService grpcService;

    public GrpcController(GrpcService grpcService) {
        this.grpcService = grpcService;
    }

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
    public ResponseEntity<SaveResponseDto> save(@RequestBody SaveRequestDto dto) {
        return ResponseEntity.ok(grpcService.save(dto));
    }

    @PostMapping("/find-by-request/{uuid}")
    public ResponseEntity<FindByUUIDResponseDto> findByUUID(@PathVariable String uuid){
        return ResponseEntity.ok(grpcService.findByUUID(uuid));
    }


}
