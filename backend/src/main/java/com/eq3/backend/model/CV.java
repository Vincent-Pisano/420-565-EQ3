package com.eq3.backend.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@org.springframework.data.mongodb.core.mapping.Document
public class CV {

    @Id
    private String id;

    private Document document;

    private Date depositDate;

    private Boolean isActive;

    private Boolean isValid;

    public CV(Document document) {
        this.id = String.valueOf(new ObjectId());
        this.document = document;
        this.depositDate = new Date();
        this.isActive = false;
        this.isValid = false;
    }

}
