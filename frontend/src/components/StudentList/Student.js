import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Row, Col } from "react-bootstrap";
import { DEPARTMENTS } from "../../Utils/DEPARTMENTS";

const Student = ({ student, onClick }) => {
  let icon = DEPARTMENTS.find(
    (department) => department.key === student.department
  ).icon;

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
