//STUDENT LIST
export const TITLE_STUDENT_LIST_CV_TO_VALIDATE =
  "Étudiants avec un CV à valider";
export const TITLE_STUDENT_LIST_OF_DEPARTMENT =
  "Étudiants de votre département";
export const TITLE_STUDENT_LIST_SUPERVISOR_TO_ASSIGN =
  "Étudiants de ce département à assigner";
export const TITLE_APPLICATION_LIST_OF_STUDENT =
  "Application aux offres de stage de : ";
  export const TITLE_STUDENT_LIST_ASSIGNED_TO_SUPERVISOR =
  "Étudiants qui vous sont assignés";

//INTERNSHIP APPLICATION LIST
export const TITLE_INTERNSHIP_APPLICATION_LIST_OF_USER =
"Liste de vos applications de stage";
export const TITLE_INTERNSHIP_APPLICATION_LIST_ACCEPTED =
"Liste des applications de stages acceptées";
export const TITLE_INTERNSHIP_SIGNATURE =
"Signature du stage";
export const TITLE_INTERNSHIP_APPLICATION_INFOS =
"Informations sur l'application de stage";

//INTERNSHIP OFFER LIST
export const TITLE_INTERNSHIP_OFFER_LIST_UNVALIDATED =
"Liste des offres de stages non validées"
export const TITLE_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT =
"Liste des offres de stages de votre département"
export const TITLE_INTERNSHIP_OFFER_LIST_OF_MONITOR =
"Liste de vos offres de stage"

//SUPERVISOR LIST
export const TITLE_SUPERVISOR_LIST_OF_SESSION =
"Liste des superviseurs de stages inscrit en : ";

//CV LIST
export const TITLE_CV_LIST = "Liste de vos CVs";

//REPORTS
export const TITLE_REPORT = "Liste des rapports disponibles"

export const TITLE_INTERNSHIP_OFFER_LIST_WAITING_VALIDATION = "Offres en attente de validation";
export const TITLE_INTERNSHIP_OFFER_LIST_VALIDATED = "Offres validées";
export const TITLE_STUDENT_LIST_SUBSCRIBED = "Étudiants enregistrés";
export const TITLE_STUDENT_LIST_WITHOUT_CV = "Étudiants avec aucun CV";
export const TITLE_STUDENT_LIST_WITH_CV_WAITING_VALIDATION = "Étudiants avec un CV à valider";
export const TITLE_STUDENT_LIST_WITHOUT_INTERVIEW = "Étudiants n'ayant aucune convocation à une entrevue";
export const TITLE_STUDENT_LIST_WAITING_INTERVIEW = "Étudiants en attente d’entrevue";
export const TITLE_STUDENT_LIST_WAITING_INTERVIEW_ANSWER = "Étudiants en attente d'une réponse d'entrevue";
export const TITLE_STUDENT_LIST_WITH_INTERNSHIP = "Étudiants ayant trouvé un stage";
export const TITLE_STUDENT_LIST_WITHOUT_SUPERVISOR_EVALUATION = "Étudiants dont le superviseur n’a pas encore évalué l’entreprise";
export const TITLE_STUDENT_LIST_WITHOUT_MONITOR_EVALUATION = "Étudiants n’ayant pas encore été évalués par leur moniteur";
export function TITLE_INTERNSHIP_APPLICATION_LIST_COMPLETED(student, session) {
  return `Applications complétées de ${student.firstName} ${student.lastName} pour la session ${session}`;
}
export function TITLE_INTERNSHIP_APPLICATION_LIST_WAITING(student, session) {
  return `Applications en attente de ${student.firstName} ${student.lastName} pour la session ${session}`;
}

