package com.java.base.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class Custom {
    @Id
    private UUID id;
    private String description;
    private String title;

    @Override
    public String toString() {
        return "Custom{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
