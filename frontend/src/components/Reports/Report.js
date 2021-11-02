import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Row, Col } from "react-bootstrap";
import { useHistory } from "react-router";

const Report = ({ report }) => {
  let history = useHistory();

  function onCreatePost(e) {
    e.preventDefault();
    history.push({
      pathname: `${report.link}`,
      state: report,
    });
  }

  return (
    <Row className="list_node" onClick={(e) => onCreatePost(e)}>
      <Col xs={3}>
        <FontAwesomeIcon className="fa-3x" icon={report.icon} />
      </Col>
      <Col xs={9} className="list_node_text">
        <li>{report.title}</li>
      </Col>
    </Row>
  );
};

export default Report;
