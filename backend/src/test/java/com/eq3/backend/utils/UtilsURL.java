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

    // BackendController
    public final static String URL_SAVE_SIGNATURE = "/save/signature/";
    public final static String URL_GET_ALL_STUDENTS_FROM_DEPARTMENT = "/getAll/students/";
    public final static String URL_GET_ALL_STUDENTS = "/getAll/students";
    public final static String URL_GET_ALL_STUDENTS_WITHOUT_SUPERVISOR = "/getAll/students/noSupervisor/";
    public final static String URL_GET_ALL_STUDENTS_WITHOUT_CV = "/getAll/students/without/CV";
    public final static String URL_GET_ALL_STUDENTS_WITHOUT_INTERVIEW_DATE = "/getAll/students/without/interviewDate";
    public final static String URL_GET_ALL_STUDENTS_WAITING_INTERVIEW = "/getAll/students/waiting/interview";
    public final static String URL_GET_ALL_SUPERVISORS = "/getAll/supervisors";
    public final static String URL_GET_MONITOR = "/get/monitor/";
    public final static String URL_GET_ALL_STUDENTS_WITHOUT_STUDENT_EVALUATION = "/get/internship/student/evaluation/unvalidated/";
    public final static String URL_ASSIGN_SUPERVISOR = "/assign/supervisor/";
    public final static String URL_DOWNLOAD_INTERNSHIP_OFFER_DOCUMENT = "/get/internshipOffer/document/";
    public final static String URL_DOWNLOAD_CV_DOCUMENT = "/get/CV/document/";
    public final static String URL_DOWNLOAD_EVALUATION_DOCUMENT = "/get/"+ DOCUMENT_NAME +"/evaluation/document";
    public final static String URL_DOWNLOAD_INTERNSHIP_CONTRACT = "/get/internship/document/";
    public final static String URL_DOWNLOAD_INTERNSHIP_STUDENT_EVALUATION = "/get/internship/student/evaluation/document/";

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
    public final static String URL_GET_ALL_INTERNSHIP_OFFERS_WORK_FIELD = "/getAll/internshipOffer/";
    public final static String URL_GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS = "/getAll/internshipOffer/unvalidated";
    public final static String URL_GET_ALL_VALIDATED_INTERNSHIP_OFFERS = "/getAll/internshipOffer/validated";
    public final static String URL_GET_ALL__INTERNSHIP_OFFERS_MONITOR = "/getAll/internshipOffer/monitor/";
    public final static String URL_GET_ALL_INTERNSHIP_APPLICATIONS_STUDENT = "/getAll/internshipApplication/student/";
    public final static String URL_GET_ALL_ACCEPTED_INTERNSHIP_APPLICATIONS = "/getAll/accepted/internshipApplication";
    public final static String URL_GET_ALL_VALIDATED_INTERNSHIP_APPLICATIONS = "/getAll/validated/internshipApplication";
    public final static String URL_GET_ALL_INTERNSHIP_APPLICATIONS_BY_INTERNSHIP_OFFER = "/getAll/internshipApplication/internshipOffer/";
    public final static String URL_APPLY_INTERNSHIP_OFFER = "/apply/internshipOffer/";
    public final static String URL_VALIDATE_INTERNSHIP_OFFER = "/validate/internshipOffer/";
    public final static String URL_UPDATE_INTERNSHIP_APPLICATION = "/update/internshipApplication";
    public final static String URL_SIGN_INTERNSHIP_CONTRACT_MONITOR = "/sign/internshipContract/monitor/";
    public final static String URL_SIGN_INTERNSHIP_CONTRACT_STUDENT = "/sign/internshipContract/student/";
    public final static String URL_SIGN_INTERNSHIP_CONTRACT_INTERNSHIP_MANAGER = "/sign/internshipContract/internshipManager/";
    public final static String URL_DEPOSIT_INTERNSHIP_STUDENT_EVALUATION = "/deposit/evaluation/student/";

}
