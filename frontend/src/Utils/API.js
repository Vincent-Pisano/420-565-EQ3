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

//SUPERVISOR LIST
export const GET_ALL_SUPERVISORS = URL_BACKEND + "getAll/supervisors/";

//CV List
export const UPDATE_ACTIVE_CV = URL_BACKEND + "update/ActiveCV/";
export const DELETE_CV = URL_BACKEND + "delete/CV/";
export const SAVE_CV = URL_BACKEND + "save/CV/";

//REPORTS
export const GET_ALL_STUDENTS = URL_BACKEND + "getAll/students/";
export const GET_ALL_STUDENTS_WITHOUT_CV = URL_BACKEND + "getAll/students/without/CV/";
export const GET_ALL_STUDENTS_WITHOUT_INTERVIEW_DATE = URL_BACKEND + "getAll/students/without/interviewDate/";
export const GET_ALL_STUDENTS_WAITING_INTERVIEW = URL_BACKEND + "getAll/students/waiting/interview/";