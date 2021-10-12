import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck, faTimes, faSyncAlt } from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";

const InternshipApplication = ({ internshipApplication, onDoubleClick }) => {

  let internshipOffer = internshipApplication.internshipOffer;

  return (
    <Row className="list_node"
    onDoubleClick={() => onDoubleClick(internshipApplication)}>
      <Col xs={3}>
        <FontAwesomeIcon className="fa-3x" icon={
          internshipApplication.status === "TAKEN" ? faCheck 
        : internshipApplication.status === "NOT_TAKEN" ? faTimes 
        : faSyncAlt} />
      </Col>
      <Col xs={9} className="list_node_text">
        <li>
          {" "}
          {internshipOffer.jobName}, {internshipOffer.city}{" "}
        </li>
      </Col>
    </Row>
  );
};

export default InternshipApplication;