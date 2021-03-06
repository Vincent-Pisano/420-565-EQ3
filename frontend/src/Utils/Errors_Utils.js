//STUDENT LIST
export const ERROR_NO_STUDENT_TO_ASSIGN = "Erreur! Aucun étudiant à assigner actuellement";
export const ERROR_NO_STUDENT_ASSIGNED = "Erreur! Aucun étudiant n'a été assigné à cette session";
export const ERROR_NO_STUDENT_SUBSCRIBED = "Erreur! Aucun étudiant n'est enregistré";
export const ERROR_NO_STUDENT_SUBSCRIBED_TO_THIS_SESSION = "Erreur! Aucun étudiant ne s'est inscrit à cette session";
export const ERROR_NO_CV_TO_VALIDATE = "Erreur! Aucun CV à valider actuellement";
export const ERROR_NO_MORE_STUDENT_TO_ASSIGN = "Plus aucun étudiant à assigner, vous allez être redirigé";
export const ERROR_NO_MORE_CV_TO_VALID = "Plus aucun CV à valider, vous allez être redirigé";
export const ERROR_ASSIGN_SUPERVISOR = "Erreur durant l'assignation du Superviseur";
export const ERROR_VALID_CV = "Erreur durant la validation du CV";
export const ERROR_REFUSE_CV = "Erreur durant le refus du CV";

export const CONFIRM_ASSIGN_SUPERVISOR = "Confirmation de l'assignation";
export const CONFIRM_VALID_CV = "Confirmation de la validation du CV";
export const CONFIRM_REFUSE_CV = "Confirmation du refus du CV";

//INTERNSHIP APPLICATION LIST
export const ERROR_NO_MORE_INTERNSHIP_APPLICATION_TO_VALIDATE = "Plus aucune application à valider, vous allez être redirigé";
export const ERROR_NO_INTERNSHIP_APPLICATION_OF_STUDENT_THIS_SESSION = "Aucune Application enregistrée pour cette session"
export const ERROR_NO_INTERNSHIP_APPLICATION_ACCEPTED_THIS_SESSION = "Aucune Application d'offre de stage acceptée pour cette session"
export const ERROR_NO_INTERNSHIP_APPLICATION_VALIDATED_THIS_SESSION = "Aucune Application validée pour cette session"
export const ERROR_NO_INTERNSHIP_APPLICATION_YET = "Aucune Application enregistrée pour le moment"
export const ERROR_RETRIEVING_INTERNSHIP_INFOS = "Erreur lors de la récupération des informations du stage"
export const ERROR_INVALID_FORMAT_PDF = "Erreur, Veuillez déposer un fichier.pdf !"
export const ERROR_DEPOSIT = "Erreur lors du dépôt"
export const ERROR_WAITING_MONITOR_SIGNATURE = "Erreur ! En attente de la signature du Moniteur"
export const ERROR_WAITING_STUDENT_SIGNATURE = "Erreur ! En attente de la signature de l'Étudiant"
export const ERROR_NO_SIGNATURE = "Erreur ! La signature n'a pas été déposé!"
export const ERROR_UPDATE = "Erreur lors de la mise à jour"
export const ERROR_NO_UPDATE = "Aucune modification effectuée"
export const ERROR_ENGAGEMENTS = "Erreur lors de la requête des engagements"

export const CONFIRM_DEPOSIT = "Confirmation du dépôt";
export const CONFIRM_SIGNATURE = "Confirmation de la signature";
export const CONFIRM_MODIFICATIONS = "Confirmation des changements";

//INTERNSHIP OFFER FORM
export const ERROR_INTERNSHIP_OFFER_FORM_ACCEPTED = "Votre demande a été acceptée, vous allez être redirigé";
export const ERROR_INTERNSHIP_OFFER_FORM = "Erreur lors de l'application à l'ofre de stage stage";
export const ERROR_MONITOR_NOT_FOUND = "Erreur! Le moniteur est inexistant";
export const ERROR_NO_PDF_FOUND = "Erreur! Veuillez soumettre un document .pdf";
export const ERROR_INVALID_DURATION = "Erreur! Durée de stage invalide !";
export const ERROR_NO_WORK_DAYS = "Erreur! Choisissez au moins une journée de travail !";
export const ERROR_INVALID_INTERNSHIP_OFFER = "Erreur! L'offre de stage est invalide";
export const ERROR_VALIDATION_INTERNSHIP_OFFER = "Erreur lors de la validation";
export const ERROR_REFUSING_INTERNSHIP_OFFER = "Erreur lors du refus ";
export const ERROR_NO_ACTIVE_CV_VALID = "Erreur ! aucun CV actif n'est valide";

export const CONFIRM_SAVE_INTERNSHIP_OFFER = "L'offre de stage a été sauvegardé, vous allez être redirigé";
export const CONFIRM_VALIDATION_INTERNSHIP_OFFER = "L'offre de stage a été validée, vous allez être redirigé";
export const CONFIRM_REFUSING_INTERNSHIP_OFFER = "L'offre de stage a été refusée, vous allez être redirigé";

//INTERNSHIP OFFER LIST
export const ERROR_NO_INTERNSHIP_OFFER_FOUND = "Aucune offre de stage déposée...";
export const ERROR_NO_INTERNSHIP_OFFER_TO_VALIDATE = "Aucune offre de stage à valider";
export const ERROR_NO_INTERNSHIP_OFFER_VALIDATED_YET = "Aucune Offre de stage n'a été validé pour le moment";
export const ERROR_NO_INTERNSHIP_OFFER_DEPOSITED = "Vous n'avez déposé aucune offre de stage";
export const ERROR_NO_INTERNSHIP_OFFER_DEPOSITED_FOR_THIS_SESSION = "Vous n'avez déposé aucune offre de stage pour cette session";

//SUPERVISOR LIST
export const ERROR_NO_SUPERVISOR_SUBSCRIBED_TO_THIS_SESSION = "Aucun Superviseur enregistré pour cette session";

//READMISSION
export const ERROR_READMISSION = "Erreur lors de la réinscription !";
export const ERROR_INVALID_PASSWORD = "Erreur ! Les mots de passes ne sont pas corrects";
export const ERROR_PASSWORD_NOT_IDENTICAL = "Erreur ! Les mots de passes ne sont pas identiques";
export function CONFIRM_READMISSION(session) {
    return `Confirmation de la réinscription pour la session ${session}`;
  }

//CV LIST
export const ERROR_ACTIVE_CV = "Erreur de traitement de CV!";
export const ERROR_DELETE_CV = "Erreur! Le fichier n'a pas été supprimé";
export const ERROR_SAVE_CV = "Erreur d'envoi de fichier";
export const ERROR_CV_LIST_MAX_SIZE = "Erreur! Taille maximale de fichiers atteinte(10)";
export const ERROR_CV_INVALID_FORMAT = "Erreur! veuillez soumettre un fichier sous format .pdf";

export const WAIT_SENDING_EMAIL_ACTIVE_CV = "Envoie de l'email d'activation de CV en cours, veuillez patientez";

export const CONFIRM_ACTIVE_CV = "Le CV est maintenant actif";
export const CONFIRM_DELETE_CV = "Le fichier a été supprimé";
export const CONFIRM_SAVE_CV = "Le fichier a été déposé";

//REPORTS
export const ERROR_NO_INTERNSHIP_OFFER_VALIDATED = "Aucune Offre de stage validée...";
export const ERROR_NO_STUDENT_SUBSCRIBED_THIS_SESSION = "Erreur! Aucun étudiant n'est enregistré cette session";
export const ERROR_NO_STUDENT_WITHOUT_CV = "Erreur! Aucun étudiant n'a pas de CV";
export const ERROR_NO_STUDENT_WITHOUT_INTERVIEW_DATE = "Erreur! Aucun étudiant n'a pas de convocation à une entrevue cette session";
export const ERROR_NO_STUDENT_WAITING_INTERVIEW = "Erreur! Aucun étudiant en attente d'entrevue cette session";
export const ERROR_NO_STUDENTS_WAITING_INTERVIEW_ANSWER = "Erreur! Tous les étudiants ont eu une réponse d'entrevue";
export const ERROR_NO_STUDENTS_WITH_INTERNSHIP = "Erreur! Aucuns étudiants n'a trouvé de stage ctte session.";
export const ERROR_NO_STUDENTS_FOR_MONITOR_EVALUATION = "Erreur! Tous les étudiants ont été évalués par leur moniteur cette session";
export const ERROR_NO_STUDENTS_FOR_SUPERVISOR_EVALUATION = "Erreur! Tous les étudiants ont leur entreprise évaluée par leurs superviseurs";

//SIGN UPS
export const ERROR_INVALID_MONITOR_USERNAME = "Le nom de moniteur doit commencer par 'M'.";
export const ERROR_INVALID_STUDENT_USERNAME = "Le nom d'utilisateur doit commencer par 'E'.";
export const ERROR_INVALID_SUPERVISOR_USERNAME = "Le nom d'utilisateur doit commencer par 'S'.";
export const ERROR_USERNAME_EMAIL_ALREADY_EXISTS = "Le nom d'utilisateur ou le courriel existe déjà.";

//HOME
export const ERROR_SAVE_SIGNATURE = "Erreur lors de la sauvegarde de la signature";
export const ERROR_SELECT_PNG = "Sélectionnez une image PNG";
export const ERROR_DELETE_SIGNATURE = "Erreur durant la suppression de la signature...";

export const CONFIRM_DELETE_SIGNATURE = "Confirmation de la suppression de la signature";
export const CONFIRM_SAVE_SIGNATURE = "Confirmation du dépôt de la signature";

