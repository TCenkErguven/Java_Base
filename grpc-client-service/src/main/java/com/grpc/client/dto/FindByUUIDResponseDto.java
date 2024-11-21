package com.grpc.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class FindByUUIDResponseDto {
    private String errorMessage;
    private Boolean status;
    private Integer code;
}
