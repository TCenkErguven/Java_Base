package com.grpc.server.exception.grpc;

import io.grpc.StatusException;

public class GrpcServerException extends StatusException {


    public GrpcServerException(GrpcErrorMessage grpcErrorMessage) {
        super(grpcErrorMessage.getStatus(), grpcErrorMessage.getMetadata());
    }
}
