package com.eq3.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@Document(collection = "student")
public class Student extends User {

    @Field
    private Department department;

    @Field
    private List<CV> CVList;

    public Student() {
        super();
        CVList = new ArrayList<>();
    }
}
