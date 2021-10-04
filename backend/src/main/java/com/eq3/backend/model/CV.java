package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@org.springframework.data.mongodb.core.mapping.Document
@Builder
@AllArgsConstructor
public class CV {

    @Id
    private String id;

    private Document document;

    @Builder.Default
    private Date depositDate = new Date();

    @Builder.Default
    private Boolean isActive = false;

    @Builder.Default
    private Boolean isValid = false;

    public CV(Document document) {
        this.id = String.valueOf(new ObjectId());
        this.document = document;
        this.depositDate = new Date();
        this.isActive = false;
        this.isValid = false;
    }

}
