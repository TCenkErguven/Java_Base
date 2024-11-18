package com.hazelcast.model;

import lombok.*;
import com.hazelcast.model.base.BaseEntity;

import java.io.Serializable;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Custom extends BaseEntity implements Serializable{
    private String id;
    private String message;
    private String transactionUUID;
    @Builder.Default
    private Boolean isProgressCompleted = false;
}
