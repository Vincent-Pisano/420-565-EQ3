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
    public final static String URL_GET_ALL_STUDENTS = "/getAll/students/";
    public final static String URL_GET_ALL_STUDENTS_WITHOUT_SUPERVISOR = "/getAll/students/noSupervisor/";
    public final static String URL_GET_ALL_SUPERVISORS = "/getAll/supervisors";
    public final static String URL_GET_MONITOR = "/get/monitor/";
    public final static String URL_ASSIGN_SUPERVISOR = "/assign/supervisor/";
    public final static String URL_DOWNLOAD_INTERNSHIP_OFFER_DOCUMENT = "/get/internshipOffer/document/";
    public final static String URL_DOWNLOAD_CV_DOCUMENT = "/get/CV/document/";
    public final static String URL_DOWNLOAD_EVALUATION_DOCUMENT = "/get/"+ DOCUMENT_NAME +"/evaluation/document";
    public final static String URL_DOWNLOAD_INTERNSHIP_CONTRACT = "/get/internship/document/";

    // CVController
    public final static String URL_SAVE_CV = "/save/CV/";
    public final static String URL_DELETE_CV = "/delete/CV/";
    public final static String URL_UPDATE_ACTIVE_CV = "/update/ActiveCV/";
    public final static String URL_GET_ALL_STUDENTS_CV_ACTIVE_NOT_VALID = "/getAll/student/CVActiveNotValid/";
    public final static String URL_VALIDATE_CV = "/validate/CV/";

    // InternshipController
    public final static String URL_SAVE_INTERNSHIP_OFFER = "/save/internshipOffer";
    public final static String URL_SAVE_INTERNSHIP = "/save/internship";
    public final static String URL_GET_ALL_INTERNSHIP_OFFERS = "/getAll/internshipOffer/";
    public final static String URL_GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS = "/getAll/internshipOffer/unvalidated";
    public final static String URL_GET_ALL_INTERNSHIP_APPLICATIONS = "/getAll/internshipApplication/";
    public final static String URL_GET_ALL_ACCEPTED_INTERNSHIP_APPLICATIONS = "/getAll/accepted/internshipApplication";
    public final static String URL_APPLY_INTERNSHIP_OFFER = "/apply/internshipOffer/";
    public final static String URL_VALIDATE_INTERNSHIP_OFFER = "/validate/internshipOffer/";
    public final static String URL_UPDATE_INTERNSHIP_APPLICATION = "/update/internshipApplication";
    public final static String URL_GET_ENGAGEMENTS = "/get/default/engagements";


}
