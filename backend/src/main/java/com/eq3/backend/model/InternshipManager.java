package com.eq3.backend.model;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class InternshipManager extends User{

}
