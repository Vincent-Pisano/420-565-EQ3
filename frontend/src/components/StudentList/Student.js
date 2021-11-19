import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUserCircle,
  faLaptopCode,
  faLandmark,
  faStethoscope,
} from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";
import {
  ARCHITECTURE_DEPT,
  COMPUTER_SCIENCE_DEPT,
  NURSING_DEPT,
} from "../../Utils/DEPARTMENTS";

const Student = ({ student, onClick }) => {
  let icon =
    student.department === COMPUTER_SCIENCE_DEPT
      ? faLaptopCode
      : student.department === ARCHITECTURE_DEPT
      ? faLandmark
      : student.department === NURSING_DEPT
      ? faStethoscope
      : faUserCircle;

  return (
    <>
      <Row className="list_node" onClick={() => onClick(student)}>
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
