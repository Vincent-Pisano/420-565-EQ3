import { faUserCircle } from "@fortawesome/free-solid-svg-icons";

const reportLink = "reports/";

let reports = [
  {
    id: "1",
    title: "Rapport des offres non-validées",
    link: reportLink + "listInternshipOffer",
    icon: faUserCircle,
  },
  {
    id: "2",
    title: "Rapport des offres validées",
    link: reportLink + "listInternshipOffer",
    icon: faUserCircle,
  },
  {
    id: "3",
    title: "Rapport des étudiants enregistrés",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
  {
    id: "4",
    title: "Rapport des étudiants avec aucun CV",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
  {
    id: "5",
    title: "Rapport des étudiants n'ayant aucune convocation à une entrevue",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
  {
    id: "6",
    title: "Rapport des étudiants en attente d’entrevue",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
  {
    id: "7",
    title:
      "Rapport des étudiants n’ayant pas encore été évalués par leur moniteur",
    link: reportLink + "listStudents",
    icon: faUserCircle,
  },
];

export function ReportsList() {
  return reports;
}
