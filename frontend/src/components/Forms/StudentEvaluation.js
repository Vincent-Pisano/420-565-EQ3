import React from 'react'

const StudentEvaluation = ({ }) => {
    return (
        <div>
            <h4>Document d'évalution d'étudiant</h4>
            <a
                className="btn btn-success btn-sm"
                download
                href={`http://localhost:9090/get/studentEvaluation`}
            >
                Télécharger
            </a>
        </div>
    )
}

export default StudentEvaluation
