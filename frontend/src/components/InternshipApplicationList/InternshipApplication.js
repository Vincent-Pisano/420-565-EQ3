import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope } from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";

const InternshipApplication = ({ internshipApplication }) => {
  return (
    <Row className="list_node">
      <Col xs={3}>
        <FontAwesomeIcon className="fa-3x" icon={faEnvelope} />
      </Col>
      <Col xs={9} className="list_node_text">
        <li>
          {internshipApplication.id}
        </li>
      </Col>
    </Row>
  );
};

export default InternshipApplication;