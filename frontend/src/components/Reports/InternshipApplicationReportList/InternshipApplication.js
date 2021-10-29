import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheck,
  faTimes,
  faSyncAlt,
  faSignature,
  faAward,
} from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";

const InternshipApplication = ({
  internshipApplication,
  onDoubleClick,
}) => {
  let internshipOffer = internshipApplication.internshipOffer;
  let student = internshipApplication.student;

  return (
    <Row
      className="list_node"
      onDoubleClick={() => onDoubleClick(internshipApplication)}
    >
      <Col xs={3}>
        <FontAwesomeIcon
          className="fa-3x"
          icon={
            internshipApplication.status === "ACCEPTED"
              ? faCheck
              : internshipApplication.status === "NOT_ACCEPTED"
              ? faTimes
              : internshipApplication.status === "VALIDATED"
              ? faSignature
              : internshipApplication.status === "COMPLETED"
              ? faAward
              : faSyncAlt
          }
        />
      </Col>
      <Col xs={9} className="list_node_text">
        <li>
          {internshipOffer.jobName +
            ", " +
            internshipOffer.city +
            student.firstName +
            " " +
            student.lastName}
        </li>
      </Col>
    </Row>
  );
};

export default InternshipApplication;
