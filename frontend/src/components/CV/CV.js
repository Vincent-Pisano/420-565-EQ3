import React from "react";
import CVButtonDownload from "./CVButtonDownload";
import CVButtonDelete from "./CVButtonDelete";
import CVButtonActive from "./CVButtonActive";
import "./../../styles/CV.css"

const CV = ({ cv }) => {

  return (
    <tr>
      <td>{cv.document.name}</td>
      <td>
        <CVButtonDownload document={cv.document} />
      </td>
      <td>
        <CVButtonDelete documentId={cv.id} />
      </td>
      <td>
        <CVButtonActive documentId={cv.id} documentActive={cv.isActive}/>
      </td>
    </tr>
  );
};

export default CV;
