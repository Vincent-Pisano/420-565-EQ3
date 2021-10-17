package com.eq3.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Map;
import static com.eq3.backend.utils.Utils.getDefaultEngagements;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Internship extends Entity {

    public final static Map<String, String> DEFAULT_ENGAGEMENTS = getDefaultEngagements();

    @Transient
    private Map<String, String> engagements;

    @DBRef
    private InternshipApplication internshipApplication;

    private PDFDocument internshipContract;

}
