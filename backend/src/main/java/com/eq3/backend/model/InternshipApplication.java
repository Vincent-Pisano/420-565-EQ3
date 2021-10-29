package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

import java.util.Date;


@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class InternshipApplication extends Entity{

    public enum ApplicationStatus {
        NOT_ACCEPTED,
        ACCEPTED,
        WAITING,
        VALIDATED,
        COMPLETED
    }

    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.WAITING;

    @DBRef
    private InternshipOffer internshipOffer;

    @DBRef
    private Student student;

    private Date interviewDate;

    public InternshipApplication() {
        super();
        this.status = ApplicationStatus.WAITING;
        this.interviewDate = null;
    }
}
