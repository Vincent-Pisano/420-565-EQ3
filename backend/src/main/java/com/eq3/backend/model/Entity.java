package com.eq3.backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
@SuperBuilder(toBuilder = true)
public abstract class Entity implements Serializable {

    @Id
    protected String id;

    @CreatedDate
    @Builder.Default
    protected Date creationDate = new Date();

    @Builder.Default
    protected Boolean isDisabled = false;

    public Entity() {
        creationDate = new Date();
        isDisabled = false;
    }
}
