package com.grpc.client.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.grpc.client.dto.SaveRequestDto;
import com.grpc.client.dto.SaveResponseDto;
import com.grpc.client.proto.*;
import com.hazelcast.server.proto.*;
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
        ResponseWrapper grpcResponse = null;
        try{
            grpcResponse = duplicateBlockingStub.saveMessage(SaveRequest.newBuilder()
                    .setUuid(dto.getUuid())
                            .setCustom(CustomResponse.newBuilder().build())
                    .build());

        }catch (Exception e){
            System.out.println(e);
        }

        SaveRequestDto response = new SaveRequestDto();
        if(grpcResponse != null){

        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find-by-uuid/{uuid}")
    public ResponseEntity<SaveResponseDto> findByUUID(@PathVariable String uuid) throws InvalidProtocolBufferException {
        ResponseWrapper grpcResponse = null;
        try{
            grpcResponse = duplicateBlockingStub.queryForMessage(FindUUIDRequest.newBuilder()
                    .setUuid(uuid)
                    .build());

        }catch (Exception e){
            System.out.println(e);
        }

        SaveResponseDto response = new SaveResponseDto();
        if(grpcResponse != null){
            response.setMessage(JsonFormat.printer().print(grpcResponse.getData()));
            response.setStatus(grpcResponse.getStatus().getStatus());
        }

        return ResponseEntity.ok(response);
    }


}
