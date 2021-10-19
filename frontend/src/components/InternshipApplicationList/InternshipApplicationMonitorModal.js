import axios from "axios";
import { React, useState, useEffect } from "react";
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
  const [internship, setInternship] = useState(undefined);

  useEffect(() => {
    axios
      .get(
        `http://localhost:9090/get/internship/${currentInternshipApplication.id}`
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
    signInternship();
  }

  function signInternship() {
    if (internship !== undefined && !internship.isSignedByMonitor) {
      axios
        .post(
          `http://localhost:9090/sign/internshipContract/monitor/${internship.id}`
        )
        .then((response) => {
          setInternship(response.data);
          setTimeout(() => {
            setErrorMessageModal("");
            handleClose();
          }, 1000);
          setErrorMessageModal("Confirmation de la signature");
        })
        .catch((err) => {
          setInternship(undefined);
        });
    }
  }

  function checkIfValidated() {
    if (internship !== undefined) {
      return (
        <Col md={4}>
          <a
            className="btn btn-lg btn-warning mt-3"
            href={`http://localhost:9090/get/internship/document/${internship.id}`}
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

  function checkIfNeedSignature() {
    if (internship !== undefined) {
      return (
        <Col md={4}>
          <Button
            variant="success"
            size="lg"
            className="btn_sub"
            onClick={(e) => onConfirmModal(e)}
            disabled={internship.signedByMonitor}
          >
            {internship.signedByMonitor ? "Déjà signé" : "Signer"}
          </Button>
        </Col>
      );
    }
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>{title}</Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
        <Row style={{ textAlign: "center" }}>
          {checkIfNeedSignature()}
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