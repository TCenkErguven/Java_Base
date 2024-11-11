package com.hazelcast.model.base;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEntity {
    private long created;
    private long updated;
    private long deleted;
    private String createdBy;
    private String updatedBy;
}
