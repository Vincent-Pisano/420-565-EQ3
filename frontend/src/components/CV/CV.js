import React from 'react'
import CVButtonDownload from './CVButtonDownload'

const CV = ({cv}) => {

    return (
        <tr>
            <td>{cv.document.name}</td>
            <td><CVButtonDownload document={cv.document} /></td>
        </tr>
    );
}

export default CV;