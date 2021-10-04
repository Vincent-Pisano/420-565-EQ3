import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEnvelope } from '@fortawesome/free-solid-svg-icons'
import { Row, Col } from 'react-bootstrap';

const Student = ({ internshipOffer, onToggle }) => {
    return (
        <Row className="list_node_internship" onDoubleClick= {() => onToggle(internshipOffer)}>
            <Col xs={3} >
                <FontAwesomeIcon className="fa-3x" icon={faEnvelope} />
            </Col>        
            
            <Col xs={9} className="list_node_text_internship">
            <li> {internshipOffer.jobName}, {internshipOffer.city} </li>
               
            </Col>
        </Row >
    );
}

export default Student