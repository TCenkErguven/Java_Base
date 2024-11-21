package com.grpc.client.utility;

import com.hazelcast.server.proto.Value;
import org.jetbrains.kotlin.com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class GrpcHelper {

    private static final Gson gson = new Gson();

    /**
     * source: https://github.com/protocolbuffers/protobuf/blob/main/src/google/protobuf/struct.proto
     * @return
     */

    public static Map<String, Value> prepareStruct() {
        try {
            Map<String, Value> messageMap = new HashMap<>();
            messageMap.put("error", Value.newBuilder().setStringValue("error erol").build());
            messageMap.put("code", Value.newBuilder().setNumberValue(200).build());
            messageMap.put("message", Value.newBuilder().setStringValue("İşleminiz başarılı bir şekilde gerçekleşmiştir").build());
            return messageMap;
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException(e);
        }
    }

    public static Map<String, Object> convertMapStructToObject(Map<String, Value> value) {
        Map<String, Object> result = new HashMap<>();
        value.forEach((key, val) -> {
            switch (val.getKindCase()) {
                case STRING_VALUE:
                    result.put(key, val.getStringValue());
                    break;
                case NUMBER_VALUE:
                    result.put(key, val.getNumberValue());
                    break;
                case BOOL_VALUE:
                    result.put(key, val.getBoolValue());
                    break;
                case STRUCT_VALUE:
                    result.put(key, convertMapStructToObject(val.getStructValue().getFieldsMap()));
                    break;
                case LIST_VALUE:
                    result.put(key, val.getListValue().getValuesList().stream()
                            .map(v -> convertMapStructToObject(Map.of("value", v)).get("value"))
                            .collect(Collectors.toList()));
                    break;
                default:
                    result.put(key, null);
            }
        });
        return result;
    }

}
