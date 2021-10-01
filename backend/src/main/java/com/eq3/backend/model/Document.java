package com.eq3.backend.model;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Document {

    private String name;

    private Binary content;
}
