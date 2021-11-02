import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUserCircle,
  faLaptopCode,
  faLandmark,
  faStethoscope,
} from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";

const Student = ({ student, onDoubleClick }) => {
  let icon =
    student.department === "COMPUTER_SCIENCE"
      ? faLaptopCode
      : student.department === "ARCHITECTURE"
      ? faLandmark
      : student.department === "NURSING"
      ? faStethoscope
      : faUserCircle;

  return (
    <>
      <Row className="list_node" onDoubleClick={() => onDoubleClick(student)}>
        <Col xs={3}>
          <FontAwesomeIcon className="fa-3x" icon={icon} />
        </Col>
        <Col xs={9} className="list_node_text">
          <li>
            {student.firstName} {student.lastName}
          </li>
        </Col>
      </Row>
    </>
  );
};

export default Student;
