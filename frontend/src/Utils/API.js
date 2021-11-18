const URL_BACKEND = "http://localhost:9090/";

//STUDENT LIST
export const GET_ALL_SESSIONS_OF_STUDENTS = URL_BACKEND + "getAll/sessions/students";
export const GET_ALL_STUDENT_WITH_CV_ACTIVE_NOT_VALID = URL_BACKEND + "getAll/student/CVActiveNotValid/";
export const GET_ALL_STUDENT_FROM_DEPARTMENT = URL_BACKEND + "getAll/students/";
export const GET_ALL_STUDENTS_WITHOUT_SUPERVISOR = URL_BACKEND + "getAll/students/noSupervisor/";
export const GET_ALL_STUDENTS_OF_SUPERVISOR = URL_BACKEND + "getAll/students/supervisor/";
export const ASSIGN_SUPERVISOR_TO_STUDENT = URL_BACKEND + "assign/supervisor/";
export const VALIDATE_CV = URL_BACKEND + "validate/CV/";
export const VIEW_CV = URL_BACKEND + "get/CV/document/";

//INTERNSHIP APPLICATION LIST
export function GET_ALL_INTERNSHIP_APPLICATIONS_OF_STUDENT(session) {
    return URL_BACKEND + "getAll/internshipApplication/" + session + "/student/";
}
export const GET_ALL_INTERNSHIP_APPLICATIONS_ACCEPTED_NEXT_SESSIONS = URL_BACKEND + "getAll/accepted/internshipApplications/current/and/next/sessions";
export const GET_ALL_INTERNSHIP_APPLICATIONS_VALIDATED_NEXT_SESSIONS = URL_BACKEND + "getAll/validated/internshipApplication";
export const GET_ALL_INTERNSHIP_APPLICATIONS_OF_INTERNSHIP_OFFER = URL_BACKEND + "getAll/internshipApplication/internshipOffer/";
export function GET_ALL_COMPLETED_INTERNSHIP_APPLICATIONS_OF_STUDENT(session) {
    return URL_BACKEND + "getAll/internshipApplication/completed/" + session + "/student/";
}
export function GET_ALL_WAITING_INTERNSHIP_APPLICATIONS_OF_STUDENT(session) {
    return URL_BACKEND + "getAll/internshipApplication/waiting/" + session + "/student/";
}
export const GET_INTERNSHIP_BY_INTERNSHIP_APPLICATION = URL_BACKEND + "get/internship/";
export const DEPOSIT_MONITOR_EVALUATION = URL_BACKEND + "deposit/evaluation/student/";
export const GET_MONITOR_EVALUATION_OF_INTERNSHIP = URL_BACKEND + "get/internship/student/evaluation/document/";
export const GET_MONITOR_CONTRACT_OF_INTERNSHIP = URL_BACKEND + "get/internship/document/";
export const SIGN_CONTRACT_OF_INTERNSHIP_MONITOR = URL_BACKEND + "sign/internshipContract/monitor/";
export const SIGN_CONTRACT_OF_INTERNSHIP_STUDENT = URL_BACKEND + "sign/internshipContract/student/";
export const SIGN_CONTRACT_OF_INTERNSHIP_INTERNSHIP_MANAGER = URL_BACKEND + "sign/internshipContract/internshipManager/";
export const UPDATE_INTERNSHIP_APPLICATION = URL_BACKEND + "update/internshipApplication";

//INTERNSHIP OFFER LIST
export const GET_ALL_SESSIONS_OF_UNVALIDATED_INTERNSHIP_OFFERS = URL_BACKEND + "getAll/next/sessions/internshipOffer/unvalidated";
export const GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS = URL_BACKEND + "getAll/internshipOffer/unvalidated/";
export const GET_ALL_SESSIONS_OF_VALIDATED_INTERNSHIP_OFFERS = URL_BACKEND + "getAll/sessions/valid/internshipOffer";
export const GET_ALL_VALIDATED_INTERNSHIP_OFFERS = URL_BACKEND + "getAll/internshipOffer/validated/";
export const GET_ALL_NEXT_SESSIONS_OF_VALIDATED_INTERNSHIP_OFFERS = URL_BACKEND + "getAll/next/sessions/internshipOffer";
export const GET_ALL_SESSIONS_INTERNSHIP_OFFERS_OF_DEPARTMENT = URL_BACKEND + "getAll/internshipOffer/";
export const GET_ALL_SESSIONS_OF_INTERNSHIP_OFFERS_OF_MONITOR = URL_BACKEND + "getAll/sessions/internshipOffer/monitor/";
export function GET_ALL_INTERNSHIP_OFFER_OF_MONITOR(session) {
    return URL_BACKEND + "getAll/internshipOffer/" + session + "/monitor/";
}

//SUPERVISOR LIST
export const GET_ALL_SUPERVISORS = URL_BACKEND + "getAll/supervisors/";

//CV List
export const UPDATE_ACTIVE_CV = URL_BACKEND + "update/ActiveCV/";
export const DELETE_CV = URL_BACKEND + "delete/CV/";
export const SAVE_CV = URL_BACKEND + "save/CV/";
export const GET_CV = URL_BACKEND + "get/CV/document/";

//REPORTS
export const GET_ALL_STUDENTS = URL_BACKEND + "getAll/students/";
export const GET_ALL_STUDENTS_WITHOUT_CV = URL_BACKEND + "getAll/students/without/CV/";
export const GET_ALL_STUDENTS_WITHOUT_INTERVIEW_DATE = URL_BACKEND + "getAll/students/without/interviewDate/";
export const GET_ALL_STUDENTS_WAITING_INTERVIEW = URL_BACKEND + "getAll/students/waiting/interview/";
export const GET_ALL_STUDENTS_WAITING_INTERVIEW_ANSWER = URL_BACKEND + "getAll/students/with/applicationStatus/waiting/and/interviewDate/passed/today/";
export const GET_ALL_STUDENTS_WITH_INTERNSHIP = URL_BACKEND + "getAll/students/with/Internship/";
export const GET_ALL_STUDENTS_WITHOUT_SUPERVISOR_EVALUATION = URL_BACKEND + "getAll/student/enterpriseEvaluation/unevaluated/";
export const GET_ALL_STUDENTS_WITHOUT_MONITOR_EVALUATION = URL_BACKEND + "getAll/student/studentEvaluation/unevaluated/";





