import {
    faLaptopCode,
    faLandmark,
    faStethoscope,
  } from "@fortawesome/free-solid-svg-icons";

export const COMPUTER_SCIENCE_DEPT = "COMPUTER_SCIENCE";
export const ARCHITECTURE_DEPT = "ARCHITECTURE";
export const NURSING_DEPT = "NURSING";

export const DEPARTMENTS = [
    {
        key: COMPUTER_SCIENCE_DEPT,
        name: "Informatique",
        icon: faLaptopCode,
    },
    {
        key: ARCHITECTURE_DEPT,
        name: "Architecture",
        icon: faLandmark,
    },
    {
        key: NURSING_DEPT,
        name: "Infirmier",
        icon: faStethoscope,
    }
]
