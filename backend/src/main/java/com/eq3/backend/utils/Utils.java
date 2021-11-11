package com.eq3.backend.utils;

import com.eq3.backend.model.PDFDocument;
import com.eq3.backend.model.Student;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class Utils {

    public final static String ASSETS_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\";

    public final static String CONTRACT_FILE_NAME = "Contract.pdf";
    public final static String EVALUATION_FILE_NAME = "Contract.pdf";

    public final static String ENTERPRISE_ENGAGEMENT_KEY = "Enterprise";
    public final static String ENTERPRISE_ENGAGEMENT_VALUES
            = "L'entreprise s'engage à ... ";

    public final static String STUDENT_ENGAGEMENT_KEY = "Student";
    public final static String STUDENT_ENGAGEMENT_VALUES
            = "L'étudiant s'engage à ...";

    public final static String COLLEGE_ENGAGEMENT_KEY = "College";
    public final static String COLLEGE_ENGAGEMENT_VALUES
            = "Le collège s'engage à ...";

    public final static String QUERY_CRITERIA_MONITOR_ID = "monitor.$id";
    public final static String COLLECTION_NAME_INTERNSHIP_OFFER = "internshipOffer";
    public final static String FIELD_SESSION = "session";
    public final static String FIELD_IS_DISABLED = "isDisabled";

    public final static int SESSION_MONTH = 5;
    public final static int SESSION_WINTER_MONTH = 2;
    public final static int SESSION_SUMMER_MONTH = 8;
    public final static String WINTER_SESSION = "Hiver";
    public final static String SUMMER_SESSION = "Été";

    public final static int POSITION_YEAR_IN_SESSION = 0;
    public final static int POSITION_TAG_IN_SESSION = 1;

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

    public static String getSessionFromDate(Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        return month <= SESSION_MONTH ? year + " " + WINTER_SESSION : year + " " + SUMMER_SESSION;
    }

    public static String getNextSessionFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        return month > SESSION_WINTER_MONTH && month < SESSION_SUMMER_MONTH
                ? year + " " +SUMMER_SESSION
                : (year + 1) + " " +WINTER_SESSION;
    }
}
