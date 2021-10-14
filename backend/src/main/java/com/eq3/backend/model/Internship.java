package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Internship extends Entity {

    @DBRef
    private InternshipApplication internshipApplication;

    private PDFDocument internshipContract;
}
