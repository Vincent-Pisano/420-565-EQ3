package com.eq3.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@ToString
@SuperBuilder(toBuilder = true)
public class InternshipManager extends User{

}
