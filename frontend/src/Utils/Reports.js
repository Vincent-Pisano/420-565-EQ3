import { faUserCircle } from "@fortawesome/free-solid-svg-icons";

const reportLink = "reports/";

let reports = [
  {
    id: "1",
    title: "Offres non-validées",
    link: reportLink + "listInternshipOffer",
    icon: faUserCircle,
  },
  {
    id: "2",
    title: "Offres validées",
    link: reportLink + "listInternshipOffer",
    icon: faUserCircle,
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
    icon: faUserCircle,
  },
  {
    id: "5",
    title: "Étudiants n'ayant aucune convocation à une entrevue",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
  {
    id: "6",
    title: "Étudiants en attente d’entrevue",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
  {
    id: "7",
    title:
      "Étudiants n’ayant pas encore été évalués par leur moniteur",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
  {
    id: "8",
    title: "Étudiants ayant trouvé un stage",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
  {
    id: "9",
    title: "Étudiants dont le superviseur n’a pas encore évalué l’entreprise",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
  {
    id: "10",
    title: "Étudiants en attente d'une réponse d'entrevue",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
];

export function ReportsList() {
  return reports;
}
