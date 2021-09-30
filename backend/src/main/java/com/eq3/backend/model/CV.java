package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CV {

    private Document document;

    private Date depositDate;

    private Boolean isActive;

    private Boolean isValid;

    public CV() {
        depositDate = new Date();
        isActive = true;
        isValid = false;
    }

}
