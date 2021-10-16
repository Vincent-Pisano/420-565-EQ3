package com.eq3.backend.utils;

import com.eq3.backend.model.PDFDocument;
import com.eq3.backend.model.Student;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Utils {
    public final static String EVALUATION_EXTENSION = "Evaluation.pdf";

    public final static String ENTERPRISE_ENGAGEMENT_KEY = "Enterprise";
    public final static String ENTERPRISE_ENGAGEMENT_VALUES
            = "L'entreprise s'engage à ne pas exploiter l'étudiant comme le régime communiste Cambodgien de Pol Pot. ";

    public final static String STUDENT_ENGAGEMENT_KEY = "Student";
    public final static String STUDENT_ENGAGEMENT_VALUES
            = "L'étudiant s'engage à être plus efficace qu'une truite lors de la durée de son stage.";

    public final static String COLLEGE_ENGAGEMENT_KEY = "College";
    public final static String COLLEGE_ENGAGEMENT_VALUES
            = "Le collège s'engage à suivre l'étudiant dans son cheminenement spirituel.";

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

    public static Map<String, String> getDefaultEngagements() {
        Map<String, String> defaultEngagements = new HashMap();

        defaultEngagements.put(ENTERPRISE_ENGAGEMENT_KEY, ENTERPRISE_ENGAGEMENT_VALUES);
        defaultEngagements.put(STUDENT_ENGAGEMENT_KEY, STUDENT_ENGAGEMENT_VALUES);
        defaultEngagements.put(COLLEGE_ENGAGEMENT_KEY, COLLEGE_ENGAGEMENT_VALUES);

        return defaultEngagements;
    }
}
