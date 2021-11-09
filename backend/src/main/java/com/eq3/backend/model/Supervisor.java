package com.eq3.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@Document(collection = "supervisor")
public class Supervisor extends User {

    @Field
    private Department department;

    @Builder.Default
    private List<String> sessions = new ArrayList<>();

    public Supervisor() {
        super();
        this.sessions = new ArrayList<>();
    }
}
