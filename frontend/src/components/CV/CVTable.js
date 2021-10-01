import React from 'react'
import { Table } from 'react-bootstrap';
import CV from "./CV"


const CVTable = ({cvlist}) => {

    return (
        <Table striped bordered hover>
            <thead>
                <tr>
                <th>FileName</th>
                </tr>
            </thead>
            <tbody>
                {
                    cvlist.map(cv => (
                        <CV
                            key={cvlist.indexOf(cv.document.name)}
                            cv={cv} />
                    ))
                }
            </tbody>
        </Table>
    );
}

export default CVTable;