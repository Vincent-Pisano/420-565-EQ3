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
  let monitor =
    currentInternshipOffer !== undefined
      ? currentInternshipOffer.monitor
      : undefined;

  let student =
    currentInternshipApplication !== undefined
      ? currentInternshipApplication.student
      : undefined;

  let supervisor =
    student !== undefined
      ? student.supervisorMap[currentInternshipOffer.session]
      : undefined;

  function checkIfSupervisorAssigned() {
    if (supervisor !== undefined) {
      return (
        <>
          <Form.Group controlId="monitorName">
            <Form.Label className="labelFields">Nom du Superviseur</Form.Label>
            <Form.Control
              value={
                supervisor !== undefined
                  ? supervisor.lastName + " " + supervisor.firstName
                  : ""
              }
              disabled
              type="text"
              className="input_form active_inp_form"
              required
            />
          </Form.Group>
          <Form.Group controlId="monitorContact">
            <Form.Label className="labelFields">
              Contact du superviseur
            </Form.Label>
            <Form.Control
              value={supervisor !== undefined ? supervisor.email : ""}
              disabled
              type="text"
              placeholder="Entrer une description du poste"
              className="input_form active_inp_form"
              required
            />
          </Form.Group>
        </>
      );
    } else {
      return <p style={{ color: "red" }}>Aucun superviseur assigné !</p>;
    }
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>
          Informations du stage
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
        <Row style={{ textAlign: "center" }}>
          <Col md={12}>
            <Form>
              <Container className="cont_inputs">
                <Form.Group controlId="enterpriseName">
                  <Form.Label className="labelFields">
                    Nom de l'entreprise
                  </Form.Label>
                  <Form.Control
                    value={monitor !== undefined ? monitor.enterpriseName : ""}
                    disabled
                    type="text"
                    className="input_form active_inp_form"
                    required
                  />
                </Form.Group>
                <Form.Group controlId="enterpriseName">
                  <Form.Label className="labelFields">
                    Adresse du stage
                  </Form.Label>
                  <Form.Control
                    value={
                      currentInternshipOffer !== undefined
                        ? currentInternshipOffer.address +
                          ", " +
                          currentInternshipOffer.city +
                          ", " +
                          currentInternshipOffer.postalCode
                        : ""
                    }
                    disabled
                    type="text"
                    className="input_form active_inp_form"
                    required
                  />
                </Form.Group>
                <hr className="modal_separator mx-auto" />
                <Form.Group controlId="monitorName">
                  <Form.Label className="labelFields">
                    Nom du monitor
                  </Form.Label>
                  <Form.Control
                    value={
                      monitor !== undefined
                        ? monitor.lastName + " " + monitor.firstName
                        : ""
                    }
                    disabled
                    type="text"
                    className="input_form active_inp_form"
                    required
                  />
                </Form.Group>
                <Form.Group controlId="monitorContact">
                  <Form.Label className="labelFields">
                    Contact du monitor
                  </Form.Label>
                  <Form.Control
                    value={monitor !== undefined ? monitor.email : ""}
                    disabled
                    type="text"
                    placeholder="Entrer une description du poste"
                    className="input_form active_inp_form"
                    required
                  />
                </Form.Group>
                <hr className="modal_separator mx-auto" />
                {checkIfSupervisorAssigned()}
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
