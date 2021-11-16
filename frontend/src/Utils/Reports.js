import {
  faUserCircle,
  faCheck,
  faUserTimes,
  faSyncAlt,
  faUserEdit,
  faUserTag,
  faUserClock,
  faIdBadge
} from "@fortawesome/free-solid-svg-icons";

const reportLink = "reports/";

let reports = [
  {
    id: "1",
    title: "Offres en attente de validation",
    link: reportLink + "listInternshipOffer",
    icon: faSyncAlt,
  },
  {
    id: "2",
    title: "Offres validées",
    link: reportLink + "listInternshipOffer",
    icon: faCheck,
  },
  {
    id: "3",
    title: "Étudiants enregistrés",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
  {
    id: "4",
    title: "Étudiants avec aucun CV",
    link: reportLink + "listStudents",
    icon: faUserTimes,
  },
  {
    id: "5",
    title: "Étudiants avec un CV à valider",
    link: reportLink + "listStudents",
    icon: faUserEdit,
  },
  {
    id: "6",
    title: "Étudiants n'ayant aucune convocation à une entrevue",
    link: reportLink + "listStudents",
    icon: faUserTimes,
  },
  {
    id: "7",
    title: "Étudiants en attente d’entrevue",
    link: reportLink + "listStudents",
    icon: faUserClock,
  },
  {
    id: "8",
    title: "Étudiants en attente d'une réponse d'entrevue",
    link: reportLink + "listStudents",
    icon: faUserClock,
  },
  {
    id: "9",
    title: "Étudiants ayant trouvé un stage",
    link: reportLink + "listStudents",
    icon: faUserTag,
  },
  {
    id: "10",
    title: "Étudiants n’ayant pas encore été évalués par leur moniteur",
    link: reportLink + "listStudents",
    icon: faIdBadge,
  },
  {
    id: "11",
    title: "Étudiants dont le superviseur n’a pas encore évalué l’entreprise",
    link: reportLink + "listStudents",
    icon: faIdBadge,
  }
];

export function ReportsList() {
  return reports;
}
