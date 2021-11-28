const URL_BACKEND = "http://localhost:9090/";

//STUDENT LIST
export const GET_ALL_SESSIONS_OF_STUDENTS = URL_BACKEND + "getAll/sessions/students";
export const GET_ALL_STUDENT_WITH_CV_ACTIVE_NOT_VALID = URL_BACKEND + "getAll/student/CVActiveNotValid/";
export const GET_ALL_STUDENT_FROM_DEPARTMENT = URL_BACKEND + "getAll/students/";
export const GET_ALL_STUDENTS_WITHOUT_SUPERVISOR = URL_BACKEND + "getAll/students/noSupervisor/";
export const GET_ALL_STUDENTS_OF_SUPERVISOR = URL_BACKEND + "getAll/students/supervisor/";
export const ASSIGN_SUPERVISOR_TO_STUDENT = URL_BACKEND + "assign/supervisor/";
export const VALIDATE_CV = URL_BACKEND + "validate/CV/";
export const REFUSE_CV = URL_BACKEND + "refuse/CV/";
export const VIEW_CV = URL_BACKEND + "get/CV/document/";

//INTERNSHIP APPLICATION LIST
export const SAVE_INTERNSHIP = URL_BACKEND + "save/internship";
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
export const DEPOSIT_ENTERPRISE_EVALUATION = URL_BACKEND + "deposit/evaluation/enterprise/";
export const GET_MONITOR_EVALUATION_OF_INTERNSHIP = URL_BACKEND + "get/internship/student/evaluation/document/";
export const GET_ENTERPRISE_EVALUATION_OF_INTERNSHIP = URL_BACKEND + "get/internship/enterprise/evaluation/document/";
export const GET_CONTRACT_OF_INTERNSHIP = URL_BACKEND + "get/internship/document/";
export const SIGN_CONTRACT_OF_INTERNSHIP_MONITOR = URL_BACKEND + "sign/internshipContract/monitor/";
export const SIGN_CONTRACT_OF_INTERNSHIP_STUDENT = URL_BACKEND + "sign/internshipContract/student/";
export const SIGN_CONTRACT_OF_INTERNSHIP_INTERNSHIP_MANAGER = URL_BACKEND + "sign/internshipContract/internshipManager/";
export const UPDATE_INTERNSHIP_APPLICATION = URL_BACKEND + "update/internshipApplication";
export const GET_DEFAULT_ENGAGEMENTS = URL_BACKEND + "get/default/engagements"

//HOME
export const GET_SIGNATURE = URL_BACKEND + "get/signature/";
export const SAVE_SIGNATURE = URL_BACKEND + "save/signature/";

//INTERNSHIP OFFER FORM
export const POST_APPLY_INTERNSHIP_OFFER = URL_BACKEND + "apply/internshipOffer/";
export const GET_MONITOR = URL_BACKEND + "get/monitor/";
export const SAVE_INTERNSHIP_OFFER = URL_BACKEND + "save/internshipOffer";
export const VALIDATE_INTERNSHIP_OFFER = URL_BACKEND + "validate/internshipOffer/";
export const REFUSE_INTERNSHIP_OFFER = URL_BACKEND + "refuse/internshipOffer/";
export const DOWNLOAD_INTERNSHIP_OFFER_DOCUMENT = URL_BACKEND + "get/internshipOffer/document/";

//INTERNSHIP OFFER LIST
export const GET_ALL_SESSIONS_OF_UNVALIDATED_INTERNSHIP_OFFERS = URL_BACKEND + "getAll/next/sessions/internshipOffer/unvalidated";
export const GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS = URL_BACKEND + "getAll/internshipOffer/unvalidated/";
export const GET_ALL_SESSIONS_OF_VALIDATED_INTERNSHIP_OFFERS = URL_BACKEND + "getAll/sessions/valid/internshipOffer";
export const GET_ALL_VALIDATED_INTERNSHIP_OFFERS = URL_BACKEND + "getAll/internshipOffer/validated/";
export const GET_ALL_NEXT_SESSIONS_OF_VALIDATED_INTERNSHIP_OFFERS = URL_BACKEND + "getAll/next/sessions/internshipOffer";
export const GET_ALL_SESSIONS_INTERNSHIP_OFFERS_OF_DEPARTMENT_NOT_APPLIED = URL_BACKEND + "getAll/internshipOffer/not/applied/";
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

//READMISSION
export const READMISSION_SUPERVISOR = URL_BACKEND + "readmission/supervisor/";
export const READMISSION_STUDENT = URL_BACKEND + "readmission/student/";

//REPORTS
export const GET_ALL_STUDENTS = URL_BACKEND + "getAll/students/";
export const GET_ALL_STUDENTS_WITHOUT_CV = URL_BACKEND + "getAll/students/without/CV/";
export const GET_ALL_STUDENTS_WITHOUT_INTERVIEW_DATE = URL_BACKEND + "getAll/students/without/interviewDate/";
export const GET_ALL_STUDENTS_WAITING_INTERVIEW = URL_BACKEND + "getAll/students/waiting/interview/";
export const GET_ALL_STUDENTS_WAITING_INTERVIEW_ANSWER = URL_BACKEND + "getAll/students/with/applicationStatus/waiting/and/interviewDate/passed/today/";
export const GET_ALL_STUDENTS_WITH_INTERNSHIP = URL_BACKEND + "getAll/students/with/Internship/";
export const GET_ALL_STUDENTS_WITHOUT_SUPERVISOR_EVALUATION = URL_BACKEND + "getAll/student/enterpriseEvaluation/unevaluated/";
export const GET_ALL_STUDENTS_WITHOUT_MONITOR_EVALUATION = URL_BACKEND + "getAll/student/studentEvaluation/unevaluated/";

//SIGN UPS
export const SIGN_UP_MONITOR = URL_BACKEND + "signUp/monitor";
export const SIGN_UP_STUDENT = URL_BACKEND + "signUp/student";
export const SIGN_UP_SUPERVISOR = URL_BACKEND + "signUp/supervisor";

//NAVBAR
export const DOWNLOAD_STUDENT_EVALUATION_DOCUMENT = URL_BACKEND + "get/student/evaluation/document";
export const DOWNLOAD_ENTERPRISE_EVALUATION_DOCUMENT = URL_BACKEND + "get/enterprise/evaluation/document";

