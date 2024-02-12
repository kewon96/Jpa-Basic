package org.example.c_inherit_mapping.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

//@MappedSuperclass
@Getter
public abstract class BaseEntity {

    private String createUserId;
    private LocalDateTime createDateTime;
    private String lastModifiedUserId;
    private LocalDateTime lastModifiedDateTime;

}
