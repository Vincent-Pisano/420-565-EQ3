import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLandmark, faLaptopCode, faStethoscope, faUserCircle } from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";

const Student = ({ student, onDoubleClick, isReport }) => {
  let icon = faUserCircle
  function checkWhichDepartment(){
    if(student.department === "COMPUTER_SCIENCE"){
      icon = faLaptopCode;
    }else if(student.department === "ARCHITECTURE"){
      icon = faLandmark
    }else if(student.department === "NURSING"){
      icon = faStethoscope
    }
  }
  return (
    <Row className="list_node" onDoubleClick={() => onDoubleClick(student)} >
      {checkWhichDepartment()}
      <Col xs={3}>
        <FontAwesomeIcon className="fa-3x" icon={icon} />
      </Col>
      <Col xs={9} className="list_node_text">
        <li>
          {student.firstName} {student.lastName}
        </li>
      </Col>
    </Row>
  );
};

export default Student;
