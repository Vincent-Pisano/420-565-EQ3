import { faScroll } from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const CVDetails = ({cv, onToggle}) => {

  return (
    <Row
      className="list_node"
      onDoubleClick={() => onToggle(cv)}
    >
      <Col xs={3}>
        <FontAwesomeIcon className="fa-3x" icon={faScroll} />
      </Col>

      <Col xs={9} className="list_node_text">
        <li>
          {cv.id}
        </li>
      </Col>
    </Row>
  );
};

export default CVDetails;