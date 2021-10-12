package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;


@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class InternshipApplication extends Entity{

    public enum ApplicationStatus {
        NOT_TAKEN,
        WAITING,
        TAKEN
    }

    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.WAITING;

    @Field
    private InternshipOffer internshipOffer;

    @DBRef
    private Student student;

    public InternshipApplication() {
        super();
        this.status = ApplicationStatus.WAITING;
    }
}
