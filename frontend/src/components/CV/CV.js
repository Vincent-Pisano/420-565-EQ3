import React from "react";
import CVButtonDownload from "./CVButtonDownload";
import CVButtonDelete from "./CVButtonDelete";
import CVButtonActive from "./CVButtonActive";
import auth from "../../services/Auth";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck, faTimes } from "@fortawesome/free-solid-svg-icons";
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
          icon={cv.isActive ? faCheck : faTimes}
          style={{ color: cv.isActive ? "green" : "red" }}
        />
      </td>
    </tr>
  );
};

export default CV;
