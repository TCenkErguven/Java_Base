package com.grpc.client.configuration.grpc;

import com.grpc.client.proto.SimpleGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.annotation.Configuration;

public class GrpcConfiguration {
    /*
        @Bean
        public ApplicationRunner clientRunner(@GrpcClient("greetingService")SimpleGrpc.SimpleBlockingStub simpleBlockingStub){
            return args -> System.out.println(simpleBlockingStub.sayHello(HelloWorldRequest.newBuilder().setName("BTC").build()));
        }
    */



}
