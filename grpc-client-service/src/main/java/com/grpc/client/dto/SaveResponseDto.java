package com.grpc.client.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SaveResponseDto {
    private String uuid;
    private Map message;
    private int code;
    private boolean status;
    private String errorMessage;
}
