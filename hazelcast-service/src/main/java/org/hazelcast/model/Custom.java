package org.hazelcast.model;

import lombok.*;
import org.hazelcast.model.base.BaseEntity;

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
}
