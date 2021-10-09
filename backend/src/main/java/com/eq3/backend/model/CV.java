package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Date;

@Data
@org.springframework.data.mongodb.core.mapping.Document
@Builder
@AllArgsConstructor
public class CV {

    public enum CVStatus {
        INVALID, WAITING, VALID
    }

    @Id
    private String id;

    private PDFDocument PDFDocument;

    @Builder.Default
    private Date depositDate = new Date();

    @Builder.Default
    private Boolean isActive = false;

    @Builder.Default
    private CVStatus status = CVStatus.INVALID;

    public CV(PDFDocument PDFDocument) {
        this.id = String.valueOf(new ObjectId());
        this.PDFDocument = PDFDocument;
        this.depositDate = new Date();
        this.isActive = false;
        this.status = CVStatus.INVALID;
    }

    public CV() {
        this.id = String.valueOf(new ObjectId());
        this.depositDate = new Date();
        this.isActive = false;
        this.status = CVStatus.INVALID;
    }

}
