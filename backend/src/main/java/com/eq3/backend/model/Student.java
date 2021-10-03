package com.eq3.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Document(collection = "student")
public class Student extends User {

    @Field
    private Department department;

    @Builder.Default
    private List<InternshipOffer> internshipOffers = new ArrayList<>();

    public Student() {
        super();
        this.internshipOffers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
