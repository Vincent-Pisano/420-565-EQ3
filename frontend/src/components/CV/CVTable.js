import React from 'react'
import { Table } from 'react-bootstrap';
import CV from "./CV"

const CVTable = ({cvlist}) => {

    return (
        <Table responsive="md" striped bordered hover>
            <thead>
                <tr>
                <th>Nom de fichier</th>
                <th>Téléchargements</th>
                </tr>
            </thead>
            <tbody>
                {
                    cvlist.map(cv => (
                        <CV
                            key={cvlist.indexOf(cv)}
                            cv={cv} />
                    ))
                }
            </tbody>
        </Table>
    );
}

export default CVTable;