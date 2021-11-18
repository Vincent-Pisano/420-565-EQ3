import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUserCircle,
  faLaptopCode,
  faLandmark,
  faStethoscope,
} from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";
import {
  GET_ARCHITECTURE_DEPT,
  GET_COMPUTER_SCIENCE_DEPT,
  GET_NURSING_DEPT,
} from "../../Utils/DEPARTMENTS";

const Student = ({ student, onClick }) => {
  let icon =
    student.department === GET_COMPUTER_SCIENCE_DEPT
      ? faLaptopCode
      : student.department === GET_ARCHITECTURE_DEPT
      ? faLandmark
      : student.department === GET_NURSING_DEPT
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
