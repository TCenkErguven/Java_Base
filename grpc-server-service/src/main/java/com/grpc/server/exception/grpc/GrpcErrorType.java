package com.grpc.server.exception.grpc;

import com.google.protobuf.Any;
import com.google.rpc.Code;
import com.google.rpc.ErrorInfo;
import com.google.rpc.Status;
import io.grpc.Metadata;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GrpcErrorType {
/*
    NOT_FOUND(Status.newBuilder()
            .setCode(Code.NOT_FOUND.getNumber())
            .setMessage("Resource not found")
            .setDetails(Any.pack())
            .build()),
    INTERNAL_ERROR(Status.newBuilder()
            .setCode(Code.INTERNAL.getNumber())
            .setMessage("Internal Server Error")
            .setDetails(new Erro)
            .build());



    private Status status;
    private ErrorInfo errorInfo;
    private Metadata metadata;
*/

}
