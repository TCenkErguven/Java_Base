package com.hazelcast.model;

import lombok.*;
import com.hazelcast.model.base.BaseEntity;
import org.jetbrains.kotlin.com.google.gson.JsonObject;

import java.io.Serializable;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Custom extends BaseEntity implements Serializable{
    private String id;
    private Object message;
    private String transactionUUID;
    @Builder.Default
    private Boolean isProgressCompleted = false;
}
