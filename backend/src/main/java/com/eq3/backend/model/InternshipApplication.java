package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;


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
    private ApplicationStatus status = ApplicationStatus.NOT_TAKEN;

    @Field
    private InternshipOffer internshipOffer;

    public InternshipApplication() {
        super();
        this.status = ApplicationStatus.NOT_TAKEN;
    }

}
