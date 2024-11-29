package com.hazelcast.utility;

import com.google.protobuf.Struct;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;

public class ProtoHelper {

    public static Struct.Builder fromJson(String jsonString) throws IOException {
        Struct.Builder structBuilder = Struct.newBuilder();
        JsonFormat.parser().ignoringUnknownFields().merge(jsonString, structBuilder);
        return structBuilder;
    }
}