import React from "react";
import "../../styles/List.css";

const EvaluationButtonDeposit = ({ evaluationName }) => {
  return (
    <a
      className="btn btn-secondary btn-lg mt-3"
      download
      href={`http://localhost:9090/get/${evaluationName}/document`}
    >
      Télécharger
    </a>
  );
};

export default EvaluationButtonDeposit;
