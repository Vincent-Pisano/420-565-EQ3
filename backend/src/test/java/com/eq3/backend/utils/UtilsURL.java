package com.eq3.backend.utils;

import static com.eq3.backend.utils.UtilsTest.DOCUMENT_NAME;

public class UtilsURL {

    // AuthController
    public final static String URL_SIGN_UP_STUDENT = "/signUp/student";
    public final static String URL_SIGN_UP_MONITOR = "/signUp/monitor";
    public final static String URL_SIGN_UP_SUPERVISOR = "/signUp/supervisor";
    public final static String URL_LOGIN_STUDENT = "/login/student/";
    public final static String URL_LOGIN_MONITOR = "/login/monitor/";
    public final static String URL_LOGIN_SUPERVISOR = "/login/supervisor/";
    public final static String URL_LOGIN_INTERNSHIP_MANAGER = "/login/internshipManager/";
    public final static String URL_READMISSION_SUPERVISOR = "/readmission/supervisor/";
    public final static String URL_READMISSION_STUDENT = "/readmission/student/";

    // BackendController
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
    public final static String URL_DOWNLOAD_EVALUATION_DOCUMENT = "/get/"+ DOCUMENT_NAME +"/evaluation/document";
    public final static String URL_DOWNLOAD_INTERNSHIP_CONTRACT = "/get/internship/document/";
    public final static String URL_DOWNLOAD_INTERNSHIP_STUDENT_EVALUATION = "/get/internship/student/evaluation/document/";
    public final static String URL_DOWNLOAD_INTERNSHIP_ENTERPRISE_EVALUATION = "/get/internship/enterprise/evaluation/document/";
    public final static String URL_GET_ALL_NEXT_SESSIONS_INTERNSHIP_OFFERS = "/getAll/next/sessions/internshipOffer";
    public final static String URL_GET_ALL_NEXT_SESSIONS_INTERNSHIP_OFFERS_UNVALIDATED = "/getAll/next/sessions/internshipOffer/unvalidated";

    // CVController
    public final static String URL_SAVE_CV = "/save/CV/";
    public final static String URL_DELETE_CV = "/delete/CV/";
    public final static String URL_UPDATE_ACTIVE_CV = "/update/ActiveCV/";
    public final static String URL_GET_ALL_STUDENTS_CV_ACTIVE_NOT_VALID = "/getAll/student/CVActiveNotValid/";
    public final static String URL_VALIDATE_CV = "/validate/CV/";

    // InternshipController
    public final static String URL_SAVE_INTERNSHIP_OFFER = "/save/internshipOffer";
    public final static String URL_SAVE_INTERNSHIP = "/save/internship";
    public final static String URL_GET_ENGAGEMENTS = "/get/default/engagements";
    public final static String URL_GET_INTERNSHIP_FROM_INTERNSHIP_APPLICATION = "/get/internship/";
    public final static String URL_GET_ALL_INTERNSHIP_OFFERS = "/getAll/internshipOffer/";
    public final static String URL_GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS = "/getAll/internshipOffer/unvalidated/";
    public final static String URL_GET_ALL_VALIDATED_INTERNSHIP_OFFERS = "/getAll/internshipOffer/validated/";
    public final static String URL_MONITOR = "/monitor/";
    public final static String URL_GET_ALL_INTERNSHIP_APPLICATIONS = "/getAll/internshipApplication/";
    public final static String URL_GET_ALL_COMPLETED_INTERNSHIP_APPLICATIONS = "/getAll/internshipApplication/completed/";
    public final static String URL_GET_ALL_WAITING_INTERNSHIP_APPLICATIONS = "/getAll/internshipApplication/waiting/";
    public final static String URL_STUDENT = "/student/";
    public final static String URL_GET_ALL_ACCEPTED_INTERNSHIP_APPLICATIONS = "/getAll/accepted/internshipApplication";
    public final static String URL_GET_ALL_INTERNSHIP_APPLICATIONS_CURRENT_FUTURE_SESSIONS = "/getAll/accepted/internshipApplications/current/and/next/sessions";
    public final static String URL_GET_ALL_VALIDATED_INTERNSHIP_APPLICATIONS = "/getAll/validated/internshipApplication";
    public final static String URL_GET_ALL_INTERNSHIP_APPLICATIONS_BY_INTERNSHIP_OFFER = "/getAll/internshipApplication/internshipOffer/";
    public final static String URL_APPLY_INTERNSHIP_OFFER = "/apply/internshipOffer/";
    public final static String URL_VALIDATE_INTERNSHIP_OFFER = "/validate/internshipOffer/";
    public final static String URL_UPDATE_INTERNSHIP_APPLICATION = "/update/internshipApplication";
    public final static String URL_SIGN_INTERNSHIP_CONTRACT_MONITOR = "/sign/internshipContract/monitor/";
    public final static String URL_SIGN_INTERNSHIP_CONTRACT_STUDENT = "/sign/internshipContract/student/";
    public final static String URL_SIGN_INTERNSHIP_CONTRACT_INTERNSHIP_MANAGER = "/sign/internshipContract/internshipManager/";
    public final static String URL_DEPOSIT_INTERNSHIP_STUDENT_EVALUATION = "/deposit/evaluation/student/";
    public final static String URL_DEPOSIT_INTERNSHIP_ENTERPRISE_EVALUATION = "/deposit/evaluation/enterprise/";

}
