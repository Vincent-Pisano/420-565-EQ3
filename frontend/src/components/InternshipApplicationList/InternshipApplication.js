import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Row, Col } from "react-bootstrap";
import auth from "../../services/Auth";
import { APPLICATION_STATUSES } from "../../Utils/APPLICATION_STATUSES";

const InternshipApplication = ({ internshipApplication, onClick }) => {
  let internshipOffer = internshipApplication.internshipOffer;
  let student = internshipApplication.student;

  let icon = APPLICATION_STATUSES.find(
    (status) => status.key === internshipApplication.status
  ).icon;

  return (
    <Row className="list_node" onClick={() => onClick(internshipApplication)}>
      <Col xs={3}>
        <FontAwesomeIcon className="fa-3x" icon={icon} />
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
