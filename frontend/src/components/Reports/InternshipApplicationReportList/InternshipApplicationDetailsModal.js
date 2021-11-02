import { React } from "react";
import { Button, Container, Modal, Row, Col, Form } from "react-bootstrap";
import "../../../styles/Form.css";

const InternshipApplicationDetailsModal = ({
  show,
  handleClose,
  currentInternshipApplication,
  showIntershipOffer,
}) => {
  let currentInternshipOffer =
    currentInternshipApplication !== undefined
      ? currentInternshipApplication.internshipOffer
      : undefined;

  function formatDate(dateString) {
    let date = new Date(dateString);
    let dateFormatted = date.toISOString().split("T")[0];
    return dateFormatted;
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>
          Informations de l'application de stage
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
        <Row style={{ textAlign: "center" }}>
          <Col md={12}>
            <Form>
              <Container className="cont_inputs">
                <Form.Group controlId="status">
                  <Form.Label className="labelFields">
                    Status de l'application
                  </Form.Label>
                  <Form.Select
                    aria-label="Default select example"
                    defaultValue={
                      currentInternshipApplication !== undefined
                        ? currentInternshipApplication.status
                        : ""
                    }
                    className="select_form d_block"
                    disabled
                  >
                    <option value="ACCEPTED">Acceptée</option>
                    <option value="NOT_ACCEPTED">Refusée</option>
                    <option value="WAITING">En attente</option>
                    <option disabled value="VALIDATED">
                      Validée
                    </option>
                  </Form.Select>
                </Form.Group>
                <Form.Group controlId="interviewDate">
                  <Form.Label className="labelFields">
                    Date d'entrevue
                  </Form.Label>
                  <Form.Control
                    type="date"
                    name="interviewDate"
                    placeholder="Date d'entrevue"
                    className="select_form d_block"
                    defaultValue={
                      currentInternshipApplication !== undefined &&
                      currentInternshipApplication.interviewDate !==
                        (null && undefined)
                        ? formatDate(currentInternshipApplication.interviewDate)
                        : ""
                    }
                    disabled
                  />
                </Form.Group>
              </Container>
            </Form>
          </Col>
          <hr className="modal_separator mx-auto" />
        </Row>
        <Row style={{ textAlign: "center" }}>
          <Col md={4}>
            <Button
              variant="warning"
              size="lg"
              className="btn_sub"
              onClick={() => showIntershipOffer(currentInternshipOffer)}
            >
              Offre de Stage
            </Button>
          </Col>
          <Col md={4}>
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
      </Modal.Body>
    </Modal>
  );
};

export default InternshipApplicationDetailsModal;
