package com.eq3.backend.model;

import lombok.*;
import org.bson.types.Binary;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PDFDocument {

    private String name;

    private Binary content;
}
