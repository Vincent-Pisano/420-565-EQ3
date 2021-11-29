package com.pravdin.democassandra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Table("internship_application")
public class InternshipApplication {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    @Indexed
    private String student_id;

    @Indexed
    private String offer_id;

    public InternshipApplication(String student_id, String offer_id) {
        super();
        this.student_id = student_id;
        this.offer_id = offer_id;
    }
}
