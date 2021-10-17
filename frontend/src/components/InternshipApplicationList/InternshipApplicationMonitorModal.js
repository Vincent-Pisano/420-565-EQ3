import axios from "axios";
import { React, useState } from "react";
import { Button, Modal, Row, Col } from "react-bootstrap";
import "../../styles/Form.css";

const InternshipApplicationMonitorModal = ({
  show,
  handleClose,
  currentInternshipApplication,
}) => {
  let title =
    currentInternshipApplication.status === "VALIDATED"
      ? "Signature du stage"
      : "Informations sur l'application de stage";
  let student = currentInternshipApplication.student;

  const [errorMessageModal, setErrorMessageModal] = useState("");

  function onConfirmModal(e) {
    e.preventDefault();
    signInternship();
  }

  function signInternship() {}

  function checkIfValidated() {
    if (currentInternshipApplication.status === "VALIDATED") {
      return (
        <Col md={4}>
          <a
            className="btn btn-lg btn-warning mt-3"
            href={`http://localhost:9090/get/internship/document/${currentInternshipApplication.id}`}
            target="_blank"
            rel="noreferrer"
          >
            contrat
          </a>
        </Col>
      );
    } else {
      let idCVActiveValid = undefined;
      if (student !== undefined) {
        student.cvlist.forEach((cv) => {
          if (cv.isActive && cv.status === "VALID") {
            idCVActiveValid = cv.id;
          }
        });
        if (idCVActiveValid !== undefined) {
          console.log("test1");
          return (
            <Col md={4}>
              <a
                className="btn btn-lg btn-warning mt-3"
                href={`http://localhost:9090/get/CV/document/${student.id}/${idCVActiveValid}`}
                target="_blank"
                rel="noreferrer"
              >
                CV
              </a>
            </Col>
          );
        } else {
          return (
            <Col md={4}>
              <Button variant="warning" size="lg" className="btn_sub" disabled>
                Pas de CV
              </Button>
            </Col>
          );
        }
      }
    }
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>{title}</Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
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
          {checkIfValidated()}
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

export default InternshipApplicationMonitorModal;
