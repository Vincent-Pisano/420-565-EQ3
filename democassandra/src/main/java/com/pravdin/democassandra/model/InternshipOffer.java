package com.pravdin.democassandra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Table("internship_offer")
public class InternshipOffer{

    public enum WorkShift {
        DAY,
        NIGHT,
        FLEXIBLE
    }

    @PrimaryKey
    private String id = UUID.randomUUID().toString();;

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

    private String monitor;

    private Department workField;

    @Builder.Default
    protected Boolean isValid = false;

    public InternshipOffer() {
        super();
        this.isValid = false;
    }
}