package com.eq3.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@Document(collection = "student")
public class Student extends User {

    @Field
    private Department department;

    @Builder.Default
    private List<CV> CVList = new ArrayList<>();

    @Builder.Default
    private List<String> sessions = new ArrayList<>();

    @DBRef
    @Builder.Default
    private Map<String, Supervisor> supervisorMap = new HashMap<>();

    public Student() {
        super();
        this.CVList = new ArrayList<>();
        this.sessions = new ArrayList<>();
        this.supervisorMap = new HashMap<>();
    }
}
