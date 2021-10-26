package com.eq3.backend.model;

import lombok.*;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PDFDocument {

    private String name;

    private Binary content;
}
