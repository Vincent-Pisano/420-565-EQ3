import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope } from "@fortawesome/free-solid-svg-icons";
import { Row, Col } from "react-bootstrap";

const InternshipOffer = ({ internshipOffer, onDoubleClick }) => {
  return (
    <Row
      className="list_node_internship"
      onDoubleClick={() => onDoubleClick(internshipOffer)}
    >
      <Col xs={3}>
        <FontAwesomeIcon className="fa-3x" icon={faEnvelope} />
      </Col>

      <Col xs={9} className="list_node_text_internship">
        <li>
          {" "}
          {internshipOffer.jobName}, {internshipOffer.city}{" "}
        </li>
      </Col>
    </Row>
  );
};

export default InternshipOffer;
