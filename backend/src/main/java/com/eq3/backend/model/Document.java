package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Document {

    @Indexed(unique = true)
    private String name;

    private Binary content;
}
