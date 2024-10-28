package com.java.base.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class SaveCustomRequestDto {
    private String description;
    private String title;
}
