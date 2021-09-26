package com.eq3.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "student")
public class Student extends User {

    @Field
    private Department department;

    @Override
    public String toString() {
        return super.toString();
    }
}
