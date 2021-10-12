import { React, useState } from "react";
import { Button, Container, Modal, Row, Col, Form } from "react-bootstrap";
import { useFormFields } from "../../lib/hooksLib";
import axios from "axios";
import "../../styles/Form.css";

const InternshipApplicationModal = ({
  show,
  handleClose,
  currentInternshipApplication,
  showIntershipOffer,
}) => {

    let currentInternshipOffer = currentInternshipApplication.internshipOffer;

  const [errorMessageModal, setErrorMessageModal] = useState("");
  const [fields, handleFieldChange] = useFormFields({
    status : currentInternshipApplication.status !== undefined ? currentInternshipApplication.status : "NON"
  });

  console.log(fields)

  function onConfirmModal(e) {
    e.preventDefault();
    ChangeStatus();
  }

  function ChangeStatus() {
    axios
      .post(
        `http://localhost:9090//update/internshipApplication`,
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
              <Form.Group controlId="workField">
                    <Form.Label className="labelFields">
                      Status de l'application
                    </Form.Label>
                    <Form.Select
                      aria-label="Default select example"
                      defaultValue={fields.status}
                      onChange={handleFieldChange}
                      className="select_form d_block"
                      required
                    >
                      <option value="TAKEN">Accepté</option>
                      <option value="NOT_TAKEN">Refusé</option>
                      <option value="WAITING">En attente</option>
                    </Form.Select>
                  </Form.Group>
              </Container>
            </Form>
          </Col>
          <hr className="modal_separator mx-auto"/>
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

export default InternshipApplicationModal;
