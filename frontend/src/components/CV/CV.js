import React from "react";
import CVButtonDownload from "./CVButtonDownload";
import CVButtonDelete from "./CVButtonDelete";
import CVButtonActive from "./CVButtonActive";
import auth from "../../services/Auth";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck, faTimes, faSyncAlt } from "@fortawesome/free-solid-svg-icons";
import "./../../styles/CV.css";

const CV = ({ cv }) => {
  return (
    <tr>
      <td>{cv.pdfdocument.name}</td>
      <td>
        <CVButtonDownload user={auth.user} cv={cv} />
      </td>
      <td>
        <CVButtonDelete documentId={cv.id} />
      </td>
      <td>
        <CVButtonActive documentId={cv.id} documentActive={cv.isActive} />
      </td>
      <td>
        <FontAwesomeIcon
          className="fa-2x"
          icon={
            cv.status === "VALID"
              ? faCheck
              : cv.status === "INVALID"
              ? faTimes
              : faSyncAlt
          }
          style={{
            color:
              cv.status === "VALID"
                ? "green"
                : cv.status === "INVALID"
                ? "red"
                : "#ffc107",
          }}
        />
      </td>
    </tr>
  );
};

export default CV;
