import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUserCircle,
  faLaptop,
  faBriefcaseMedical,
  faHome,
} from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";
import { GET_ARCHITECTURE_DEPT, GET_COMPUTER_SCIENCE_DEPT, GET_NURSING_DEPT } from "../../Utils/DEPARTMENTS";

const Supervisor = ({ supervisor, onClick }) => {
  return (
    <Row className="list_node" onClick={() => onClick(supervisor)}>
      <Col xs={3}>
        <FontAwesomeIcon
          className="fa-3x"
          icon={
            supervisor.department === GET_COMPUTER_SCIENCE_DEPT
              ? faLaptop
              : supervisor.department === GET_NURSING_DEPT
              ? faBriefcaseMedical
              : supervisor.department === GET_ARCHITECTURE_DEPT
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
