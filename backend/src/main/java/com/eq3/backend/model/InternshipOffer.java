package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class InternshipOffer extends Entity{

    public enum WorkShift {
        DAY,
        NIGHT,
        FLEXIBLE
    }

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

    @DBRef
    private Monitor monitor;

    @Field
    private PDFDocument PDFDocument;

    @Field
    @Builder.Default
    protected Boolean isValid = false;

    public InternshipOffer() {
        super();
        this.isValid = false;
    }

}
