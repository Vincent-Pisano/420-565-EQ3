import React from 'react'
import { Container} from "react-bootstrap";

const StudentEvaluation = () => {
    return (
        <Container>
            <h4>Document d'évalution d'étudiant</h4>
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
