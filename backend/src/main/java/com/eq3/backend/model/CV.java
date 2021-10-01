package com.eq3.backend.model;

import lombok.Data;

import java.util.Date;

@Data
public class CV {

    private Document document;

    private Date depositDate;

    private Boolean isActive;

    private Boolean isValid;

    public CV() {
        depositDate = new Date();
        isActive = false;
        isValid = false;
    }

    public CV(Document document) {
        this.document = document;
        depositDate = new Date();
        isActive = false;
        isValid = false;
    }

}
