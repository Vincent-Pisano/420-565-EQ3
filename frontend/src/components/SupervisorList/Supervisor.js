import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUserCircle,
  faLaptop,
  faBriefcaseMedical,
  faHome,
} from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";

const Supervisor = ({ supervisor, onDoubleClick }) => {
  return (
    <Row className="list_node" onDoubleClick={() => onDoubleClick(supervisor)}>
      <Col xs={3}>
        <FontAwesomeIcon
          className="fa-3x"
          icon={
            supervisor.department === "COMPUTER_SCIENCE"
              ? faLaptop
              : supervisor.department === "NURSING"
              ? faBriefcaseMedical
              : supervisor.department === "ARCHITECTURE"
              ? faHome
              : faUserCircle
          }
        />
      </Col>
      <Col xs={9} className="list_node_text">
        <li>
          {supervisor.firstName} {supervisor.lastName}
        </li>
      </Col>
    </Row>
  );
};

export default Supervisor;
