import React from 'react'

const EnterpriseEvaluation = () => {
    return (
        <div>
            <h4>Document d'évalution d'entreprise</h4>
            <a
                className="btn btn-success btn-sm"
                download
                href={`http://localhost:9090/get/enterpriseEvaluation`}
            >
                Télécharger
            </a>
        </div>
    )
}

export default EnterpriseEvaluation
