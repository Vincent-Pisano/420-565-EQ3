package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class InternshipOffer {

    @Id
    private String id;

    @Field
    private String jobName;

    @Field
    private String description;

    @Field
    private Date startDate;

    @Field
    private Date endDate;

    @Field
    private Double weeklyWorkTime;

    @Field
    private Double hourlySalary;

    @Field
    private List<String> workDays;

    @Field
    private WorkShift workShift;

    @Field
    private String address;

    @Field
    private String city;

    @Field
    private String postalCode;

    @Field
    private Department workField;

    @Field
    private Monitor monitor;

    @Field
    private Document document;

    @Field
    @Builder.Default
    protected Date creationDate = new Date();

    @Field
    @Builder.Default
    protected Boolean isValid = false;

    public InternshipOffer() {
        creationDate = new Date();
        isValid = false;
    }

}
