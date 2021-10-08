import React from "react";
import "../../styles/List.css";

const StudentEvaluation = ({ evaluationName }) => {
  return (
      <a
        className="btn btn-secondary btn-lg mt-3"
        download
        href={`http://localhost:9090/get/studentEvaluation/document`}
      >
        Télécharger
      </a>
  );
};

export default StudentEvaluation;
