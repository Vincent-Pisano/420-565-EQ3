package com.eq3.backend.utils;

import com.eq3.backend.model.PDFDocument;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;

public class UtilsController {

    public final static String CROSS_ORIGIN_ALLOWED = "http://localhost:3006";
    public final static String APPLICATION_JSON_AND_CHARSET_UTF8 = "application/json;charset=utf8";
    public final static String APPLICATION_PDF = "application/pdf";
    public final static String MULTI_PART_FROM_DATA = "multipart/form-data";
    public final static String REQUEST_PART_DOCUMENT = "document";
    public final static String REQUEST_PART_INTERNSHIP_OFFER = "internshipOffer";

    public static class AuthControllerUrl {
        public final static String URL_SIGN_UP_STUDENT = "/signUp/student";
        public final static String URL_SIGN_UP_MONITOR = "/signUp/monitor";
        public final static String URL_SIGN_UP_SUPERVISOR = "/signUp/supervisor";
        public final static String URL_READMISSION_SUPERVISOR = "/readmission/supervisor/{id}";
        public final static String URL_READMISSION_STUDENT = "/readmission/student/{id}";
        public final static String URL_LOGIN_STUDENT = "/login/student/{username}/{password}";
        public final static String URL_LOGIN_MONITOR = "/login/monitor/{username}/{password}";
        public final static String URL_LOGIN_SUPERVISOR = "/login/supervisor/{username}/{password}";
        public final static String URL_LOGIN_INTERNSHIP_MANAGER = "/login/internshipManager/{username}/{password}";
    }

    public static class BackendControllerUrl {
        public final static String URL_SAVE_SIGNATURE = "/save/signature/{username}";
        public final static String URL_GET_ALL_STUDENTS_FROM_DEPARTMENT_BY_SESSION = "/getAll/students/{department}/{session}";
        public final static String URL_GET_ALL_SESSIONS_OF_STUDENTS = "/getAll/sessions/students";
        public final static String URL_GET_ALL_STUDENTS_BY_SESSION = "/getAll/students/{session}";
        public final static String URL_GET_ALL_STUDENTS_WITHOUT_SUPERVISOR_FROM_DEPARTMENT_BY_SESSION = "/getAll/students/noSupervisor/{department}/{session}";
        public final static String URL_GET_ALL_STUDENTS_WITH_SUPERVISOR_BY_SESSION = "/getAll/students/supervisor/{idSupervisor}/{session}";
        public final static String URL_GET_ALL_STUDENTS_WITHOUT_CV_BY_SESSION = "/getAll/students/without/CV/{session}";
        public final static String URL_GET_ALL_SUPERVISORS_BY_SESSION = "/getAll/supervisors/{session}";
        public final static String URL_GET_ALL_SESSIONS_INTERNSHIP_OFFER_MONITOR = "/getAll/sessions/internshipOffer/monitor/{idMonitor}";
        public final static String URL_GET_MONITOR = "/get/monitor/{username}";
        public final static String URL_ASSIGN_SUPERVISOR = "/assign/supervisor/{idStudent}/{idSupervisor}";
        public final static String URL_DOWNLOAD_CV_DOCUMENT = "/get/CV/document/{idStudent}/{idCV}";
        public final static String URL_GET_SIGNATURE = "/get/signature/{username}";
        public final static String URL_DELETE_SIGNATURE_STUDENT = "/delete/signature/student/{username}";
        public final static String URL_DELETE_SIGNATURE_MONITOR = "/delete/signature/monitor/{username}";
        public final static String URL_DELETE_SIGNATURE_INTERNSHIP_MANAGER = "/delete/signature/internshipManager/{username}";
    }

    public static class BackendInternshipControllerUrl {
        public final static String URL_GET_ALL_STUDENTS_WITHOUT_INTERVIEW_DATE_BY_SESSION = "/getAll/students/without/interviewDate/{session}";
        public final static String URL_GET_ALL_STUDENTS_WITH_APPLICATION_STATUS_WAITING_AND_INTERVIEW_DATE_PASSED_TODAY_BY_SESSION = "/getAll/students/with/applicationStatus/waiting/and/interviewDate/passed/today/{session}";
        public final static String URL_GET_ALL_STUDENTS_WITH_INTERNSHIP_BY_SESSION = "/getAll/students/with/Internship/{session}";
        public final static String URL_GET_ALL_STUDENTS_WAITING_INTERVIEW_BY_SESSION = "/getAll/students/waiting/interview/{session}";
        public final static String URL_GET_ALL_STUDENTS_WITHOUT_STUDENT_EVALUATION_BY_SESSION = "/getAll/student/studentEvaluation/unevaluated/{session}";
        public final static String URL_GET_ALL_STUDENTS_WITHOUT_ENTERPRISE_EVALUATION_BY_SESSION = "/getAll/student/enterpriseEvaluation/unevaluated/{session}";
        public final static String URL_GET_ALL_NEXT_SESSIONS_INTERNSHIP_OFFERS = "/getAll/next/sessions/internshipOffer";
        public final static String URL_GET_ALL_NEXT_SESSIONS_INTERNSHIP_OFFERS_UNVALIDATED = "/getAll/next/sessions/internshipOffer/unvalidated";
        public final static String URL_GET_ALL_SESSION_OF_INVALID_INTERNSHIP_OFFERS = "/getAll/sessions/invalid/internshipOffer";
        public final static String URL_GET_ALL_SESSION_OF_VALID_INTERNSHIP_OFFERS = "/getAll/sessions/valid/internshipOffer";
        public final static String URL_DOWNLOAD_INTERNSHIP_OFFER_DOCUMENT = "/get/internshipOffer/document/{id}";
        public final static String URL_DOWNLOAD_EVALUATION_DOCUMENT = "/get/{typeEvaluation}/evaluation/document";
        public final static String URL_DOWNLOAD_INTERNSHIP_CONTRACT = "/get/internship/document/{id}";
        public final static String URL_DOWNLOAD_INTERNSHIP_STUDENT_EVALUATION = "/get/internship/student/evaluation/document/{idInternship}";
        public final static String URL_DOWNLOAD_INTERNSHIP_ENTERPRISE_EVALUATION = "/get/internship/enterprise/evaluation/document/{idInternship}";
    }

    public static class CVControllerUrl {
        public final static String URL_SAVE_CV = "/save/CV/{id}";
        public final static String URL_DELETE_CV = "/delete/CV/{idStudent}/{idCV}";
        public final static String URL_UPDATE_ACTIVE_CV = "/update/ActiveCV/{idStudent}/{idCV}";
        public final static String URL_GET_ALL_STUDENTS_CV_ACTIVE_NOT_VALID = "/getAll/student/CVActiveNotValid/{session}";
        public final static String URL_VALIDATE_CV = "/validate/CV/{idStudent}";
        public final static String URL_REFUSE_CV = "/refuse/CV/{idStudent}";
    }

    public static class InternshipOfferControllerUrl {
        private final static String JSON_NODE_REFUSAL_NODE = "refusalNote";

        public static String getRefusalNote(ObjectNode json) {
            return json != null && json.get(JSON_NODE_REFUSAL_NODE) != null ? json.get(JSON_NODE_REFUSAL_NODE).asText() : "";
        }

        public final static String URL_SAVE_INTERNSHIP_OFFER = "/save/internshipOffer";
        public final static String URL_GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS_BY_SESSION = "/getAll/internshipOffer/unvalidated/{session}";
        public final static String URL_GET_ALL_VALIDATED_INTERNSHIP_OFFERS_BY_SESSION = "/getAll/internshipOffer/validated/{session}";
        public final static String URL_GET_ALL_INTERNSHIP_OFFERS_OF_MONITOR_BY_SESSION = "/getAll/internshipOffer/{session}/monitor/{id}";
        public final static String URL_VALIDATE_INTERNSHIP_OFFER = "/validate/internshipOffer/{idOffer}";
        public final static String URL_REFUSE_INTERNSHIP_OFFER = "/refuse/internshipOffer/{idOffer}";
    }

    public static class InternshipApplicationControllerUrl {
        public final static String URL_APPLY_INTERNSHIP_OFFER = "/apply/internshipOffer/{username}";
        public final static String URL_GET_ALL_INTERNSHIP_APPLICATIONS_OF_STUDENT_BY_SESSION = "/getAll/internshipApplication/{session}/student/{username}";
        public final static String URL_GET_ALL_COMPLETED_INTERNSHIP_APPLICATIONS_OF_STUDENT_BY_SESSION = "/getAll/internshipApplication/completed/{session}/student/{username}";
        public final static String URL_GET_ALL_WAITING_INTERNSHIP_APPLICATIONS_OF_STUDENT_BY_SESSION = "/getAll/internshipApplication/waiting/{session}/student/{username}";
        public final static String URL_GET_ALL_INTERNSHIP_APPLICATIONS_OF_INTERNSHIP_OFFER = "/getAll/internshipApplication/internshipOffer/{id}";
        public final static String URL_GET_ALL_ACCEPTED_INTERNSHIP_APPLICATIONS = "/getAll/accepted/internshipApplication";
        public final static String URL_GET_ALL_ACCEPTED_INTERNSHIP_APPLICATIONS_CURRENT_AND_NEXT_SESSIONS = "/getAll/accepted/internshipApplications/current/and/next/sessions";
        public final static String URL_GET_ALL_VALIDATED_INTERNSHIP_APPLICATIONS = "/getAll/validated/internshipApplication";
        public final static String URL_GET_ALL_INTERNSHIP_OFFERS_NOT_APPLIED_BY_SESSION_AND_USER = "/getAll/internshipOffer/not/applied/{session}/{username}";
        public final static String URL_UPDATE_INTERNSHIP_APPLICATION = "/update/internshipApplication";
    }

    public static class InternshipControllerUrl {
        public final static String URL_SAVE_INTERNSHIP = "/save/internship";
        public final static String URL_GET_DEFAULT_ENGAGEMENTS = "/get/default/engagements";
        public final static String URL_GET_INTERNSHIP_BY_INTERNSHIP_APPLICATION = "/get/internship/{idApplication}";
        public final static String URL_SIGN_INTERNSHIP_CONTRACT_MONITOR = "/sign/internshipContract/monitor/{idInternship}";
        public final static String URL_SIGN_INTERNSHIP_CONTRACT_STUDENT = "/sign/internshipContract/student/{idInternship}";
        public final static String URL_SIGN_INTERNSHIP_CONTRACT_INTERNSHIP_MANAGER = "/sign/internshipContract/internshipManager/{idInternship}";
        public final static String URL_DEPOSIT_INTERNSHIP_STUDENT_EVALUATION = "/deposit/evaluation/student/{idInternship}";
        public final static String URL_DEPOSIT_INTERNSHIP_ENTERPRISE_EVALUATION = "/deposit/evaluation/enterprise/{idInternship}";
    }

    public static ResponseEntity<InputStreamResource> getDownloadingDocument(PDFDocument PDFDocument) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "inline; filename=" + PDFDocument.getName());

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .headers(headers)
                .contentLength(PDFDocument.getContent().length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(
                        new ByteArrayInputStream(PDFDocument.getContent().getData()))
                );
    }
}
