package com.grpc.server.controller;


import com.grpc.server.proto.HelloWorldRequest;
import com.grpc.server.proto.HelloWorldResponse;
import com.grpc.server.proto.SimpleGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloWorldController extends SimpleGrpc.SimpleImplBase {
    @Override
    public void sayHello(HelloWorldRequest request, StreamObserver<HelloWorldResponse> responseObserver) {
        HelloWorldResponse response = HelloWorldResponse.newBuilder().setGreeting("Hey yo " + request.getName()).build();
        try{
            responseObserver.onNext(response);
        } catch (Exception e){
            responseObserver.onError(new StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription("Error incoming, take cover")));
        }
        responseObserver.onCompleted();
    }
}
