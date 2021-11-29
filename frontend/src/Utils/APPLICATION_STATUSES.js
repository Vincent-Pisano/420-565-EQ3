import {
  faCheck,
  faTimes,
  faSyncAlt,
  faSignature,
  faAward,
} from "@fortawesome/free-solid-svg-icons";

export const STATUS_ACCEPTED = "ACCEPTED";
export const STATUS_NOT_ACCEPTED = "NOT_ACCEPTED";
export const STATUS_WAITING = "WAITING";
export const STATUS_VALIDATED = "VALIDATED";
export const STATUS_COMPLETED = "COMPLETED";

export const APPLICATION_STATUSES = [
  {
    key: STATUS_ACCEPTED,
    name: "Acceptée",
    icon: faCheck,
    disabled: false

  },
  {
    key: STATUS_NOT_ACCEPTED,
    name: "Refusée",
    icon: faTimes,
    disabled: false

  },
  {
    key: STATUS_WAITING,
    name: "En attente",
    icon: faSyncAlt,
    disabled: false

  },
  {
    key: STATUS_VALIDATED,
    name: "Validée",
    icon: faSignature,
    disabled: true

  },
  {
    key: STATUS_COMPLETED,
    name: "Complétée",
    icon: faAward, 
    disabled: true
  },
];

