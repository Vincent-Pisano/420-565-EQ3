//STUDENT LIST
export const URL_STUDENT_LIST_CV_TO_VALIDATE = "/studentList/validate/cv";
export const URL_STUDENT_LIST_FROM_DEPARTMENT = "/studentList/from/department";
export const URL_STUDENT_LIST_ASSIGN_SUPERVISOR = "/studentList/assign";
export const URL_STUDENT_LIST_ASSIGNED_SUPERVISOR = "/studentList/assigned";

//SUPERVISOR LIST
export const URL_SUPERVISOR_LIST = "/supervisorList";

//INTERNSHIP APPLICATION LIST
export const URL_INTERNSHIP_APPLICATION_LIST_OF_STUDENT = "/internshipApplicationList/student";
export const URL_INTERNSHIP_APPLICATION_LIST_ACCEPTED = "/internshipApplicationList/accepted";
export const URL_INTERNSHIP_APPLICATION_LIST_SIGNATURE = "/internshipApplicationList/signature";
export const URL_INTERNSHIP_APPLICATION_LIST_OF_INTERNSHIP_OFFER = "/internshipApplicationList/monitor/internshipOffer";
export const URL_INTERNSHIP_APPLICATION_LIST_OF_STUDENT_ASSIGNED = "/internshipApplicationList/student/";

//INTERNSHIP OFFER LIST
export const URL_INTERNSHIP_OFFER_LIST_UNVALIDATED = "/internshipOfferList/unvalidated";
export const URL_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT = "/internshipOfferList/department";
export const URL_INTERNSHIP_OFFER_LIST_OF_MONITOR = "/internshipOfferList/monitor";

//REPORTS
const reportLink = "/reports";

export const URL_INTERNSHIP_OFFER_LIST_WAITING_VALIDATION = reportLink + "/internshipOfferList/unvalidated";
export const URL_INTERNSHIP_OFFER_LIST_VALIDATED = reportLink + "/internshipOfferList/validated";
export const URL_STUDENT_LIST_SUBSCRIBED = reportLink + "/studentList/subscribed";
export const URL_STUDENT_LIST_WITHOUT_CV = reportLink + "/studentList/without/cv";
export const URL_STUDENT_LIST_WITH_CV_WAITING_VALIDATION = reportLink + "/studentList/with/cv/waiting/validation";
export const URL_STUDENT_LIST_WITHOUT_INTERVIEW = reportLink + "/studentList/without/interview";
export const URL_STUDENT_LIST_WAITING_INTERVIEW = reportLink + "/studentList/waiting/interview";
export const URL_STUDENT_LIST_WAITING_INTERVIEW_ANSWER = reportLink + "/studentList/waiting/interview/answer";
export const URL_STUDENT_LIST_WITH_INTERNSHIP = reportLink + "/studentList/with/internship";
export const URL_STUDENT_LIST_WITHOUT_SUPERVISOR_EVALUATION = reportLink + "/studentList/without/supervisor/evaluation";
export const URL_STUDENT_LIST_WITHOUT_MONITOR_EVALUATION = reportLink + "/studentList/without/monitor/evaluation";
export const URL_INTERNSHIP_APPLICATION_LIST_WAITING_REPORT = reportLink + "/internshipApplicationList/waiting/student/";
export const URL_INTERNSHIP_APPLICATION_LIST_COMPLETED_REPORT = reportLink + "/internshipApplicationList/completed/student/";




/*const HOME_PAGE_URL = [
  {
    key: "LOGIN",
    link: "/",
    name: "Connexion",
  },
  {
    key: "SUBSCRIBE",
    link: "/subscribe",
    name: "Inscription",
  },
];

const INTERNSHIP_MANAGER_URL = [
  {
    key: "VALID_CV",
    link: "/studentList/validation/cv",
    name: "Validation CV étudiant",
  },
];

const STUDENT_URL = [];

const MONITOR_URL = [];

const SUPERVISOR_URL = [];*/
