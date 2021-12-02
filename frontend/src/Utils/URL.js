import auth from "../services/Auth";

//STUDENT LIST
export const URL_STUDENT_LIST_CV_TO_VALIDATE = "/studentList/validate/cv";
export const URL_STUDENT_LIST_OF_DEPARTMENT = "/studentList/department";
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
export const URL_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT_NOT_APPLIED = "/internshipOfferList/department/not/applied";
export const URL_INTERNSHIP_OFFER_LIST_OF_MONITOR = "/internshipOfferList/monitor";

//INTERNSHIP OFFER FORM
export const URL_INTERNSHIP_OFFER_FORM = "/internshipOfferForm"

//REPORTS
export const REPORT_LINK = "/reports";

export const URL_INTERNSHIP_OFFER_LIST_WAITING_VALIDATION = REPORT_LINK + "/internshipOfferList/unvalidated";
export const URL_INTERNSHIP_OFFER_LIST_VALIDATED = REPORT_LINK + "/internshipOfferList/validated";
export const URL_STUDENT_LIST_SUBSCRIBED = REPORT_LINK + "/studentList/subscribed";
export const URL_STUDENT_LIST_WITHOUT_CV = REPORT_LINK + "/studentList/without/cv";
export const URL_STUDENT_LIST_WITH_CV_WAITING_VALIDATION = REPORT_LINK + "/studentList/with/cv/waiting/validation";
export const URL_STUDENT_LIST_WITHOUT_INTERVIEW = REPORT_LINK + "/studentList/without/interview";
export const URL_STUDENT_LIST_WAITING_INTERVIEW = REPORT_LINK + "/studentList/waiting/interview";
export const URL_STUDENT_LIST_WAITING_INTERVIEW_ANSWER = REPORT_LINK + "/studentList/waiting/interview/answer";
export const URL_STUDENT_LIST_WITH_INTERNSHIP = REPORT_LINK + "/studentList/with/internship";
export const URL_STUDENT_LIST_WITHOUT_SUPERVISOR_EVALUATION = REPORT_LINK + "/studentList/without/supervisor/evaluation";
export const URL_STUDENT_LIST_WITHOUT_MONITOR_EVALUATION = REPORT_LINK + "/studentList/without/monitor/evaluation";
export const URL_INTERNSHIP_APPLICATION_LIST_WAITING_REPORT = REPORT_LINK + "/internshipApplicationList/waiting/student/";
export const URL_INTERNSHIP_APPLICATION_LIST_COMPLETED_REPORT = REPORT_LINK + "/internshipApplicationList/completed/student/";
export const URL_INTERNSHIP_APPLICATION_LIST_WAITING_ENTERPRISE_EVALUATION_REPORT = REPORT_LINK + "/internshipApplicationList/waiting/enterprise/evaluation/";
export const URL_INTERNSHIP_APPLICATION_LIST_WAITING_STUDENT_EVALUATION_REPORT = REPORT_LINK + "/internshipApplicationList/waiting/student/evaluation/";

function getPathHome() {
  return "/home/" + auth.user.username;
}

export const INTERNSHIP_MANAGER_URL = [
  {
    key: "HOME",
    link: () => getPathHome(),
    name: "Accueil",
  },
  {
    key: "URL_INTERNSHIP_OFFER_FORM",
    link: URL_INTERNSHIP_OFFER_FORM,
    name: "Dépôt d'offre de stage",
  },
  {
    key: "URL_INTERNSHIP_OFFER_LIST_UNVALIDATED",
    link: URL_INTERNSHIP_OFFER_LIST_UNVALIDATED,
    name: "Liste des offres non validées",
  },
  {
    key: "URL_INTERNSHIP_APPLICATION_LIST_ACCEPTED",
    link: URL_INTERNSHIP_APPLICATION_LIST_ACCEPTED,
    name: "Liste des applications acceptées",
  },
  {
    key: "URL_STUDENT_LIST_CV_TO_VALIDATE",
    link: URL_STUDENT_LIST_CV_TO_VALIDATE,
    name: "Liste des CV à valider",
  },
  {
    key: "URL_SUPERVISOR_LIST",
    link: URL_SUPERVISOR_LIST,
    name: "Assignation de Superviseur",
  },
  {
    key: "URL_INTERNSHIP_APPLICATION_LIST_SIGNATURE",
    link: URL_INTERNSHIP_APPLICATION_LIST_SIGNATURE,
    name: "Signature d'applications validées",
  },
  {
    key: "REPORT_LINK",
    link: REPORT_LINK,
    name: "Rapports",
  }
];

export const STUDENT_URL = [
  {
    key: "HOME",
    link: () => getPathHome(),
    name: "Accueil",
  },
  {
    key: "INTERNSHIP_OFFER_LIST",
    link: URL_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT_NOT_APPLIED,
    name: "Liste d'offres de stage",
  },
  {
    key: "INTERNSHIP_APPLICATION_LIST",
    link: URL_INTERNSHIP_APPLICATION_LIST_OF_STUDENT,
    name: "Liste de vos applications de stage",
  },
];

export const MONITOR_URL = [
  {
    key: "HOME",
    link: () => getPathHome(),
    name: "Accueil",
  },
  {
    key: "INTERNSHIP_OFFER_DEPOSIT",
    link: URL_INTERNSHIP_OFFER_FORM,
    name: "Dépôt d'offres de stage",
  },
  {
    key: "INTERNSHIP_OFFER_LIST_OF_MONITOR",
    link: URL_INTERNSHIP_OFFER_LIST_OF_MONITOR,
    name: "Liste de vos offres de stage",
  }
];

export const SUPERVISOR_URL = [
  {
    key: "HOME",
    link: () => getPathHome(),
    name: "Accueil",
  },
  {
    key: "STUDENT_LIST_DEPARTMENT",
    link: URL_STUDENT_LIST_OF_DEPARTMENT,
    name: "Liste des étudiants du département",
  },
  {
    key: "STUDENT_LIST_ASSIGNED",
    link: URL_STUDENT_LIST_ASSIGNED_SUPERVISOR,
    name: "Liste de vos étudiant",
  }
];