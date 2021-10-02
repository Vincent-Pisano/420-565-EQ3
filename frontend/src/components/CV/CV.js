import React from "react";
import CVButtonDownload from "./CVButtonDownload";
import CVButtonDelete from "./CVButtonDelete";
import CVButtonActive from "./CVButtonActive";

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
        <CVButtonActive documentId={cv.id} />
      </td>
    </tr>
  );
};

export default CV;
