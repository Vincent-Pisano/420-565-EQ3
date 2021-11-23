package com.eq3.backend.utils;

public class UtilsController {

    public final static String CROSS_ORIGIN_ALLOWED = "http://localhost:3006";
    public final static String APPLICATION_JSON_AND_CHARSET_UTF8 = "application/json;charset=utf8";
    public final static String MULTI_PART_FROM_DATA = "multipart/form-data";
    public final static String REQUEST_PART_DOCUMENT = "document";
    public final static String REQUEST_PART_INTERNSHIP_OFFER = "document";

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
        public final static String URL_SAVE_SIGNATURE = "/save/signature/";
        public final static String URL_GET_ALL_STUDENTS_FROM_DEPARTMENT = "/getAll/students/";
        public final static String URL_GET_ALL_STUDENTS = "/getAll/students/";
        public final static String URL_GET_ALL_STUDENTS_WITHOUT_SUPERVISOR = "/getAll/students/noSupervisor/";
        public final static String URL_GET_ALL_STUDENTS_WITH_SUPERVISOR = "/getAll/students/supervisor/";
        public final static String URL_GET_ALL_STUDENTS_WITHOUT_CV = "/getAll/students/without/CV/";
        public final static String URL_GET_ALL_STUDENTS_WITH_INTERNSHIP = "/getAll/students/with/Internship/";
        public final static String URL_GET_ALL_STUDENTS_WITHOUT_INTERVIEW_DATE = "/getAll/students/without/interviewDate/";
        public final static String URL_GET_ALL_STUDENTS_WAITING_INTERVIEW = "/getAll/students/waiting/interview/";
        public final static String URL_GET_ALL_STUDENTS_WITHOUT_STUDENT_EVALUATION = "/getAll/student/studentEvaluation/unevaluated/";
        public final static String URL_GET_ALL_STUDENTS_WITHOUT_ENTERPRISE_EVALUATION = "/getAll/student/enterpriseEvaluation/unevaluated/";
        public final static String URL_GET_ALL_STUDENTS_WITH_APPLICATION_STATUS_WAITING_AND_INTERVIEW_DATE_PASSED_TODAY = "/getAll/students/with/applicationStatus/waiting/and/interviewDate/passed/today/";
        public final static String URL_GET_ALL_SUPERVISORS = "/getAll/supervisors/";
        public final static String URL_GET_ALL_SESSIONS_INTERNSHIP_OFFER_MONITOR = "/getAll/sessions/internshipOffer/monitor/";
        public final static String URL_GET_ALL_SESSION_OF_STUDENTS = "/getAll/sessions/students";
        public final static String URL_GET_ALL_SESSION_OF_INVALID_INTERNSHIP_OFFERS = "/getAll/sessions/invalid/internshipOffer";
        public final static String URL_GET_ALL_SESSION_OF_VALID_INTERNSHIP_OFFERS = "/getAll/sessions/valid/internshipOffer";
        public final static String URL_GET_MONITOR = "/get/monitor/";
        public final static String URL_ASSIGN_SUPERVISOR = "/assign/supervisor/";
        public final static String URL_DOWNLOAD_INTERNSHIP_OFFER_DOCUMENT = "/get/internshipOffer/document/";
        public final static String URL_DOWNLOAD_CV_DOCUMENT = "/get/CV/document/";
        public final static String URL_DOWNLOAD_EVALUATION_DOCUMENT = "/get/{holé-hola}/evaluation/document";
        public final static String URL_DOWNLOAD_INTERNSHIP_CONTRACT = "/get/internship/document/";
        public final static String URL_DOWNLOAD_INTERNSHIP_STUDENT_EVALUATION = "/get/internship/student/evaluation/document/";
        public final static String URL_DOWNLOAD_INTERNSHIP_ENTERPRISE_EVALUATION = "/get/internship/enterprise/evaluation/document/";
        public final static String URL_GET_ALL_NEXT_SESSIONS_INTERNSHIP_OFFERS = "/getAll/next/sessions/internshipOffer";
        public final static String URL_GET_ALL_NEXT_SESSIONS_INTERNSHIP_OFFERS_UNVALIDATED = "/getAll/next/sessions/internshipOffer/unvalidated";
    }

    public static class CVControllerUrl {
        public final static String URL_SAVE_CV = "/save/CV/{id}";
        public final static String URL_DELETE_CV = "/delete/CV/{idStudent}/{idCV}";
        public final static String URL_UPDATE_ACTIVE_CV = "/update/ActiveCV/{idStudent}/{idCV}";
        public final static String URL_GET_ALL_STUDENTS_CV_ACTIVE_NOT_VALID = "/getAll/student/CVActiveNotValid/{session}";
        public final static String URL_VALIDATE_CV = "/validate/CV/{idStudent}";
    }

    public static class InternshipOfferControllerUrl {
        public final static String URL_SAVE_INTERNSHIP_OFFER = "/save/internshipOffer";
        public final static String URL_GET_ALL_INTERNSHIP_OFFERS_BY_SESSION_AND_WORK_FIELD = "/getAll/internshipOffer/{session}/{workField}";
        public final static String URL_GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS_BY_SESSION = "/getAll/internshipOffer/unvalidated/{session}";
        public final static String URL_GET_ALL_VALIDATED_INTERNSHIP_OFFERS_BY_SESSION = "/getAll/internshipOffer/validated/{session}";
        public final static String URL_GET_ALL_INTERNSHIP_OFFERS_OF_MONITOR_BY_SESSION = "/getAll/internshipOffer/{session}/monitor/{id}";
        public final static String URL_VALIDATE_INTERNSHIP_OFFER = "/validate/internshipOffer/{idOffer}";
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


}
