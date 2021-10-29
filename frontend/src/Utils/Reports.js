import { faUserCircle, faCheck } from "@fortawesome/free-solid-svg-icons";

const reportLink = 'reports/'

let reports = [
  {
    id: "1",
    title: "Rapport des offres non-validées",
    link: "listInternshipOffer",
    icon: faUserCircle,
  },
  {
    id: "2",
    title: "Rapport des offres validées",
    link: "listInternshipOffer",
    icon: faUserCircle,
  },
  {
    id: "3",
    title: "Rapport non validé",
    link: "listStudents",
    icon: faUserCircle,
  },
  {
    id: "4",
    title: "Rapport des étudiants avec aucun CV",
    link: "listStudents",
    icon: faUserCircle,
  },
  {
    id: "5",
    title: "Rapport des étudiants n'ayant aucune convocation à entrevue",
    link: "listStudents",
    icon: faUserCircle,
  },
  {
    id: "6",
    title: "Rapport des étudiants ayant trouvé un stage",
    link: "listStudents",
    icon: faUserCircle,
  },
];

export function ReportsList() {
  return reports;
}
