import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUserCircle } from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";

const Supervisor = ({ supervisor }) => {
  return (
    <Row className="list_node">
      <Col xs={3}>
        <FontAwesomeIcon className="fa-3x" icon={faUserCircle} />
      </Col>
      <Col xs={9} className="list_node_text">
        <li>
          {supervisor.firstName} {supervisor.lastName} {supervisor.department}
        </li>
      </Col>
    </Row>
  );
};

export default Supervisor;
