package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@SuperBuilder(toBuilder = true)
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation extends Entity{

    @Field
    private PDFDocument document;

}
