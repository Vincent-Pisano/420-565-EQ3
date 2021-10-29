import { React, useState } from "react";
import { Button, Container, Modal, Row, Col, Form } from "react-bootstrap";
import { useFormFields } from "../../lib/hooksLib";
import axios from "axios";
import "../../styles/Form.css";

const InternshipApplicationStudentModal = ({
  show,
  handleClose,
  currentInternshipApplication,
  showIntershipOffer,
}) => {
  let currentInternshipOffer = currentInternshipApplication.internshipOffer;

  const [errorMessageModal, setErrorMessageModal] = useState("");
  const [fields, handleFieldChange] = useFormFields({
    status: currentInternshipApplication.status,
    interviewDate: currentInternshipApplication.interviewDate,
  });

  function onConfirmModal(e) {
    e.preventDefault();
    ChangeStatus();
  }

  function setInternshipApplication() {
    currentInternshipApplication.status =
      fields.status !== undefined
        ? fields.status
        : currentInternshipApplication.status;
    currentInternshipApplication.interviewDate =
      fields.interviewDate !== undefined
        ? fields.interviewDate
        : currentInternshipApplication.interviewDate;
    currentInternshipApplication.student.cvlist = [];
    currentInternshipApplication.student.signature = undefined;
    if (currentInternshipApplication.student.supervisor !== null)
      currentInternshipApplication.student.supervisor.signature = undefined;
    currentInternshipApplication.internshipOffer.pdfdocument = undefined;
    currentInternshipApplication.internshipOffer.monitor.signature = undefined;
  }

  function formatDate(dateString) {
    console.log(dateString);
    let date = new Date(dateString);
    let dateFormatted = date.toISOString().split("T")[0];
    return dateFormatted;
  }

  function ChangeStatus() {
    setInternshipApplication();
    axios
      .post(
        `http://localhost:9090/update/internshipApplication`,
        currentInternshipApplication
      )
      .then((response) => {
        setTimeout(() => {
          setErrorMessageModal("");
          handleClose();
        }, 1000);
        setErrorMessageModal("Confirmation des changements");
      })
      .catch((err) => {
        setErrorMessageModal("Erreur lors de la mise à jour");
      });
  }

  function isValidated() {
    return currentInternshipApplication.status === "VALIDATED";
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
                    defaultValue={currentInternshipApplication.status}
                    onChange={handleFieldChange}
                    className="select_form d_block"
                    required
                  >
                    <option disabled={isValidated()} value="ACCEPTED">
                      Acceptée
                    </option>
                    <option disabled={isValidated()} value="NOT_ACCEPTED">
                      Refusée
                    </option>
                    <option disabled={isValidated()} value="WAITING">
                      En attente
                    </option>
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
                      currentInternshipApplication.interviewDate !==
                      (null && undefined)
                        ? formatDate(currentInternshipApplication.interviewDate)
                        : ""
                    }
                    onChange={handleFieldChange}
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
              variant="success"
              size="lg"
              className="btn_sub"
              onClick={(e) => onConfirmModal(e)}
            >
              Confirmer
            </Button>
          </Col>
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
      <Modal.Footer>
        <Row>
          <Col>
            <p
              className="error_p"
              style={{
                color: errorMessageModal.startsWith("Erreur") ? "red" : "green",
              }}
            >
              {errorMessageModal}
            </p>
          </Col>
        </Row>
      </Modal.Footer>
    </Modal>
  );
};

export default InternshipApplicationStudentModal;
