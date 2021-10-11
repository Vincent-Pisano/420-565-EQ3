package com.eq3.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@Document(collection = "student")
public class Student extends User {

    @Field
    private Department department;

    @Builder.Default
    private List<CV> CVList = new ArrayList<>();

    @DBRef
    private Supervisor supervisor;

    public Student() {
        super();
        this.CVList = new ArrayList<>();
    }
}
