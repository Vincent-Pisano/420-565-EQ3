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

    public enum OfferStatus {
        WAITING,
        ACCEPTED,
        REFUSED
    }

    private String jobName;

    private String description;

    private Date startDate;

    private Date endDate;

    private Double weeklyWorkTime;

    private Double hourlySalary;

    private List<String> workDays;

    private WorkShift workShift;

    private String address;

    private String city;

    private String postalCode;

    private Department workField;

    private String session;

    @DBRef
    private Monitor monitor;

    private PDFDocument PDFDocument;

    private String refusalNote;

    @Field
    @Builder.Default
    protected OfferStatus status = OfferStatus.WAITING;

    public InternshipOffer() {
        super();
        this.status = OfferStatus.WAITING;
    }
}
