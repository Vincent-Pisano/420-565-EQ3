import React from 'react'
import { Container } from "react-bootstrap";
import EvaluationButtonDeposit from "./EvaluationButtonDeposit"
import "../../styles/List.css";

const StudentEvaluation = () => {
    return (
      <Container>
        <h2 className="cont_title_form">Document d'évalution d'étudiant</h2>
        <EvaluationButtonDeposit />
      </Container>
    )
}

export default StudentEvaluation
