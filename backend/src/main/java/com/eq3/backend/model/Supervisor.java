package com.eq3.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Document(collection = "supervisor")
public class Supervisor extends User {

    @Field
    private Department department;
}
