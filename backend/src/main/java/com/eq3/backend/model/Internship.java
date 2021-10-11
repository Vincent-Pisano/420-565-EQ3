package com.eq3.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;


@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class Internship extends Entity {

    @DBRef
    private InternshipApplication internshipApplication;
}
