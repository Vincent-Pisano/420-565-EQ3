import { Button, Modal, Row, Col, Form, Container } from "react-bootstrap";
import "../../../styles/Form.css";

const CVModalActive = ({
  handleClose,
  show,
  currentInternshipOffer,
  showInternshipOffer,
}) => {
  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>
          Information sur le refus de l'offre de stage
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
        <Row style={{ textAlign: "center" }}>
          <Col md={12}>
            <Form>
              <Container className="cont_inputs">
                <Form.Group controlId="note">
                  <Form.Control
                    value={
                      currentInternshipOffer !== undefined &&
                      currentInternshipOffer.status === "REFUSED"
                        ? currentInternshipOffer.refusalNote
                        : ""
                    }
                    disabled
                    type="text"
                    className="input_form"
                    required
                  />
                </Form.Group>
              </Container>
            </Form>
          </Col>
        </Row>
      </Modal.Body>
      <Modal.Footer>
        <Row>
          <Col xs={6}>
            <Button
              variant="warning"
              size="lg"
              className="btn_sub"
              onClick={() => showInternshipOffer(currentInternshipOffer)}
            >
              Oui
            </Button>
          </Col>
          <Col xs={6}>
            <Button
              variant="danger"
              size="lg"
              className="btn_sub"
              onClick={handleClose}
            >
              Retour
            </Button>
          </Col>
        </Row>
      </Modal.Footer>
    </Modal>
  );
};
export default CVModalActive;
