package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Document
public class Evaluation {

    @Id
    private String id;

    @Field
    private PDFDocument document;

    @Field
    @Builder.Default
    protected Date creationDate = new Date();

    @Field
    @Builder.Default
    protected Boolean isDisabled = false;

    public Evaluation() {
        this.creationDate = new Date();
        this.isDisabled = false;
    }

}
