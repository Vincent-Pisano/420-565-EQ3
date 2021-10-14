package com.eq3.backend.utils;

import com.eq3.backend.model.PDFDocument;
import com.eq3.backend.model.Student;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public class Utils {
    public final static String INTERNSHIP_CONTRACT_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\contratTemplate.docx";
    public final static String EVALUATION_EXTENSION = "Evaluation.pdf";


    public static PDFDocument extractDocument(MultipartFile multipartFile) throws IOException {
        PDFDocument PDFDocument = new PDFDocument();
        PDFDocument.setName(multipartFile.getOriginalFilename());
        PDFDocument.setContent(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
        return PDFDocument;
    }

    public static Optional<Student> cleanUpStudentCVList(Optional<Student> optionalStudent) {
        optionalStudent.ifPresent(student -> {
            if (student.getCVList() != null) {
                student.getCVList().forEach(cv -> {
                    PDFDocument PDFDocument = cv.getPDFDocument();
                    PDFDocument.setContent(null);
                });
            }
        });
        return optionalStudent;
    }
}
