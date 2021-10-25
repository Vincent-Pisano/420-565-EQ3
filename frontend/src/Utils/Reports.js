import { faUserCircle, faCheck } from "@fortawesome/free-solid-svg-icons";

let reports = [
  {
    id: "1",
    title: "Rapport des offres non-validées",
    link: "unvalidated",
    icon: faUserCircle,
  },
  {
    id: "2",
    title: "Rapport non validé",
    link: "test",
    icon: faUserCircle,
  },
  {
    id: "3",
    title: "Rapport non validé",
    link: "test",
    icon: faUserCircle,
  },
  {
    id: "4",
    title: "Rapport non validé",
    link: "test",
    icon: faCheck,
  },
];

export function ReportsList() {
  return reports;
}
