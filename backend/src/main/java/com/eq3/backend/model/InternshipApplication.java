package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Builder
@AllArgsConstructor
public class InternshipApplication extends Entity{

    @Builder.Default
    private InternshipApplicationStatus status = InternshipApplicationStatus.NOT_TAKEN;

    @Field
    private InternshipOffer internshipOffer;

    public InternshipApplication() {
        super();
        this.status = InternshipApplicationStatus.NOT_TAKEN;
    }

}
