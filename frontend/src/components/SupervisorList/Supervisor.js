import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Row, Col } from "react-bootstrap";
import { DEPARTMENTS } from "../../Utils/DEPARTMENTS";

const Supervisor = ({ supervisor, onClick }) => {

  let icon = DEPARTMENTS.find(
    (department) => department.key === supervisor.department
  ).icon;

  return (
    <Row className="list_node" onClick={() => onClick(supervisor)}>
      <Col xs={3}>
        <FontAwesomeIcon
          className="fa-3x"
          icon={icon}
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
