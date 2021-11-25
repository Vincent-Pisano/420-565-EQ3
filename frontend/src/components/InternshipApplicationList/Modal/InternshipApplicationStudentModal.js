import { React, useEffect, useState } from "react";
import { Button, Container, Modal, Row, Col, Form } from "react-bootstrap";
import { useFormFields } from "../../../lib/hooksLib";
import { UPDATE_INTERNSHIP_APPLICATION, GET_CONTRACT_OF_INTERNSHIP, GET_INTERNSHIP_BY_INTERNSHIP_APPLICATION } from "../../../Utils/API";
import {
  ERROR_UPDATE,
  CONFIRM_MODIFICATIONS,
} from "../../../Utils/Errors_Utils";
import axios from "axios";
import "../../../styles/Form.css";
import { STATUS_ACCEPTED, STATUS_COMPLETED, STATUS_NOT_ACCEPTED, STATUS_VALIDATED, STATUS_WAITING } from "../../../Utils/APPLICATION_STATUSES";

const InternshipApplicationStudentModal = ({
  show,
  handleClose,
  currentInternshipApplication,
  showIntershipOffer,
}) => {
  let currentInternshipOffer = currentInternshipApplication.internshipOffer;

  const [internship, setInternship] = useState(undefined);
  const [errorMessageModal, setErrorMessageModal] = useState("");
  const [fields, handleFieldChange] = useFormFields({
    status: currentInternshipApplication.status,
    interviewDate: currentInternshipApplication.interviewDate,
  });

  useEffect(() => {
    axios
      .get(
        GET_INTERNSHIP_BY_INTERNSHIP_APPLICATION +
          currentInternshipApplication.id
      )
      .then((response) => {
        setInternship(response.data);
      })
      .catch((err) => {
        setInternship(undefined);
      });
  }, [currentInternshipApplication.id]);

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
    if (currentInternshipApplication.student.supervisorMap !== null && 
      currentInternshipApplication.student.supervisorMap !== undefined)
      currentInternshipApplication.student.supervisorMap = undefined;
    currentInternshipApplication.internshipOffer.pdfdocument = undefined;
    currentInternshipApplication.internshipOffer.monitor.signature = undefined;
  }

  function formatDate(dateString) {
    let date = new Date(dateString);
    let dateFormatted = date.toISOString().split("T")[0];
    return dateFormatted;
  }

  function ChangeStatus() {
    setInternshipApplication();
    axios
      .post(UPDATE_INTERNSHIP_APPLICATION, currentInternshipApplication)
      .then((response) => {
        setTimeout(() => {
          setErrorMessageModal("");
          handleClose();
        }, 1000);
        setErrorMessageModal(CONFIRM_MODIFICATIONS);
      })
      .catch((err) => {
        setErrorMessageModal(ERROR_UPDATE);
      });
  }

  function isValidatedOrCompleted() {
    return (
      currentInternshipApplication.status === STATUS_VALIDATED ||
      currentInternshipApplication.status === STATUS_COMPLETED
    );
  }

  function checkIfCompleted() {
    if (currentInternshipApplication.status === STATUS_COMPLETED && internship !== undefined) {
      return (
        <Col md={4}>
          <a
            className="btn btn-lg btn-success mt-3"
            href={GET_CONTRACT_OF_INTERNSHIP + internship.id}
            target="_blank"
            rel="noreferrer"
          >
            Contrat de stage
          </a>
        </Col>
      )
    } else {
      return (
        <Col md={4}>
            <Button
              variant="success"
              size="lg"
              className="mt-3"
              onClick={(e) => onConfirmModal(e)}
            >
              Confirmer
            </Button>
          </Col>
      )
    }
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
                    disabled={isValidatedOrCompleted()}
                  >
                    <option value={STATUS_ACCEPTED}>Acceptée</option>
                    <option value={STATUS_NOT_ACCEPTED}>Refusée</option>
                    <option value={STATUS_WAITING}>En attente</option>
                    <option disabled value={STATUS_VALIDATED}>
                      Validée
                    </option>
                    <option disabled value={STATUS_COMPLETED}>
                      Complétée
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
                      currentInternshipApplication.interviewDate !== null &&
                        currentInternshipApplication.interviewDate !== undefined
                        ? formatDate(currentInternshipApplication.interviewDate)
                        : ""
                    }
                    onChange={handleFieldChange}
                    disabled={isValidatedOrCompleted()}
                  />
                </Form.Group>
              </Container>
            </Form>
          </Col>
          <hr className="modal_separator mx-auto" />
        </Row>
        <Row style={{ textAlign: "center" }}>
          {checkIfCompleted()}
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
