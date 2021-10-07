import React from 'react'
import { Container } from "react-bootstrap";
import "../../styles/List.css";

const StudentEvaluation = () => {
    return (
      <Container>
        <h2 className="cont_title_form">Document d'évalution d'étudiant</h2>
        <a
                className="btn btn-success btn-sm"
                download
                href={`http://localhost:9090/get/studentEvaluation/document`}
            >
                Télécharger
            </a>
      </Container>
    )
}

export default StudentEvaluation
