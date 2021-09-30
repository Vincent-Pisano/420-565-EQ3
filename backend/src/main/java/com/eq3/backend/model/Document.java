package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    private String name;

    private Binary content;
}
