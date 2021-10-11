package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Document
public class Evaluation extends Entity{

    @Field
    private PDFDocument document;

}
