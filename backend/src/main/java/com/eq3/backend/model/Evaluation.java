package com.eq3.backend.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@SuperBuilder(toBuilder = true)
@Document
public class Evaluation extends Entity{

    @Field
    private PDFDocument document;

}
