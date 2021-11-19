package com.eq3.backend.utils;

import com.eq3.backend.model.*;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class Utils {

    public final static String ASSETS_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\";

    public final static String CONTRACT_FILE_NAME = "Contract.pdf";
    public final static String EVALUATION_FILE_NAME = "Evaluation.pdf";

    public final static String ENTERPRISE_ENGAGEMENT_KEY = "Enterprise";
    public final static String ENTERPRISE_ENGAGEMENT_VALUES
            = "L'entreprise s'engage à ... ";

    public final static String STUDENT_ENGAGEMENT_KEY = "Student";
    public final static String STUDENT_ENGAGEMENT_VALUES
            = "L'étudiant s'engage à ...";

    public final static String COLLEGE_ENGAGEMENT_KEY = "College";
    public final static String COLLEGE_ENGAGEMENT_VALUES
            = "Le collège s'engage à ...";

    public final static String EMAIL_SUBJECT_ACTIVE_CV
            = "Un étudiant vient de déposer un CV";

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

    public final static String EMAIL_SUBJECT_SUPERVISOR_ABOUT_EVALUATION = "Remise de l'évaluation de l'entreprise";
    public final static String EMAIL_SUBJECT_MONITOR_ABOUT_EVALUATION = "Remise de l'évaluation de l'étudiant";
    public final static String EMAIL_SUBJECT_INTERVIEW_STUDENT = "Convocation à une entrevue d'un étudiant";

    public final static String EMAIL_SUBJECT_INTERVIEW_ONE_WEEK_BEFORE_STUDENT = "Convocation a votre entrevue dans une semaine";

    public final static String UTC_TIME_ZONE = "UTC";

    public static String getEmailTextForSupervisorAboutEvaluation(Supervisor supervisor, Monitor monitor){
        return "Bonjour " + supervisor.getFirstName() + " " + supervisor.getLastName() + "\n" +
                "vous devez remettre l'évaluation de l'entreprise : " +
                monitor.getEnterpriseName() + "\n" +
                "d'ici deux semaines.";
    }

    public static String getEmailTextForMonitorAboutEvaluation(Student student, Monitor monitor){
        return "Bonjour " + monitor.getFirstName() + " " + monitor.getLastName() + "\n" +
                "vous devez remettre l'évaluation de l'étudiant : " +
                student.getFirstName() + " " + student.getLastName() + "\n" +
                "d'ici deux semaines.";
    }

    public static String getEmailTextWhenStudentsGetsInterviewed(Student student){
        return "L'étudiant " + student.getFirstName() + " " + student.getLastName() +
                " va être convoqué a une entrevue de stage aujourd'hui!";
    }

    public static String getEmailTextStudentAboutInterviewOneWeekBefore(InternshipOffer internshipOffer){
        return "Vous allez être convoqué à votre entrevue de stage dans 1 semaine." + "\n" + "Détails : " + "\n"
                + "Titre de l'emploi : " + internshipOffer.getJobName() + "\n"
                + "Description : " + internshipOffer.getDescription();
    }

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

    public static String getEmailTextActiveCV(Student student, CV cvActive){
        PDFDocument pdfFromCv = cvActive.getPDFDocument();
        return "L'étudiant " + student.getFirstName() + " " + student.getLastName() +
                " vient de déposer son CV: " + pdfFromCv.getName();
    }
}
