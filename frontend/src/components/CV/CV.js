import React from 'react'
import CVButtonDownload from './CVButtonDownload'
import CVButtonDelete from './CVButtonDelete';

const CV = ({cv}) => {

    return (
        <tr>
            <td>{cv.document.name}</td>
            <td><CVButtonDownload document={cv.document} /></td>
            <td><CVButtonDelete documentId={cv.id}/></td>
        </tr>
    );
}

export default CV;