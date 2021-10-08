import React from 'react'
import { Container } from "react-bootstrap";
import EvaluationButtonDeposit from "./EvaluationButtonDeposit"
import "../../styles/List.css";

const EnterpriseEvaluation = () => {
    return (
      <Container>
        <h2 className="cont_title_form">Document d'évalution d'entreprise</h2>
        <EvaluationButtonDeposit evaluationName="enterpriseEvaluation"/>
      </Container>
    )
}

export default EnterpriseEvaluation
