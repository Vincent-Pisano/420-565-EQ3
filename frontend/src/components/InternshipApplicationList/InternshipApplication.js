import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheck,
  faTimes,
  faSyncAlt,
  faSignature,
  faAward,
} from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";
import auth from "../../services/Auth";
import { STATUS_ACCEPTED, STATUS_COMPLETED, STATUS_NOT_ACCEPTED, STATUS_VALIDATED } from "../../Utils/APPLICATION_STATUSES";

const InternshipApplication = ({ internshipApplication, onClick }) => {
  let internshipOffer = internshipApplication.internshipOffer;
  let student = internshipApplication.student;

  return (
    <Row className="list_node" onClick={() => onClick(internshipApplication)}>
      <Col xs={3}>
        <FontAwesomeIcon
          className="fa-3x"
          icon={
            internshipApplication.status === STATUS_ACCEPTED
              ? faCheck
              : internshipApplication.status === STATUS_NOT_ACCEPTED
              ? faTimes
              : internshipApplication.status === STATUS_VALIDATED
              ? faSignature
              : internshipApplication.status === STATUS_COMPLETED
              ? faAward
              : faSyncAlt
          }
        />
      </Col>
      <Col xs={9} className="list_node_text">
        <li>
          {" "}
          {!auth.isMonitor()
            ? internshipOffer.jobName + ", " + internshipOffer.city + " "
            : ""}
          {auth.isInternshipManager() || auth.isMonitor()
            ? student.firstName + " " + student.lastName
            : ""}
        </li>
      </Col>
    </Row>
  );
};

export default InternshipApplication;
