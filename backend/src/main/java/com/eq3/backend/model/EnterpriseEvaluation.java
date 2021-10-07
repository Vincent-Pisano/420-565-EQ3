package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
public class EnterpriseEvaluation {

    @Id
    private String id;

    @Field
    @Indexed(unique = true)
    private String name;

    @Field
    private PDFDocument PDFDocument;

    public EnterpriseEvaluation() {

    }
}
