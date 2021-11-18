import { React, useState, useEffect } from "react";
import { Button, Container, Modal, Row, Col, Form } from "react-bootstrap";
import {
  GET_INTERNSHIP_BY_INTERNSHIP_APPLICATION,
  DEPOSIT_ENTERPRISE_EVALUATION,
  GET_ENTERPRISE_EVALUATION_OF_INTERNSHIP
} from "../../../Utils/API";
import {
  ERROR_RETRIEVING_INTERNSHIP_INFOS,
  ERROR_INVALID_FORMAT_PDF,
  CONFIRM_DEPOSIT,
  ERROR_DEPOSIT,
} from "../../../Utils/ERRORS";
import axios from "axios";
import "../../../styles/Form.css";

const InternshipApplicationSupervisorEnterpriseEvaluationModal = ({
  show,
  handleClose,
  currentInternshipApplication,
}) => {
  const [errorMessageModal, setErrorMessageModal] = useState("");
  const [document, setDocument] = useState(undefined);
  const [internship, setInternship] = useState(undefined);

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
        setErrorMessageModal(ERROR_RETRIEVING_INTERNSHIP_INFOS);
      });
  }, [currentInternshipApplication.id]);

  function onConfirmModal(e) {
    e.preventDefault();
    if (internship !== undefined) {
      if (document !== undefined && document.type === "application/pdf") {
        depositEvaluation();
      } else {
        setErrorMessageModal(ERROR_INVALID_FORMAT_PDF);
        setTimeout(() => {
          setErrorMessageModal("");
        }, 2000);
      }
    }
  }

  function depositEvaluation() {
    let formData = new FormData();
    formData.append("document", document);
    axios
      .post(DEPOSIT_ENTERPRISE_EVALUATION + internship.id, formData)
      .then((response) => {
        setInternship(response.data);
        setTimeout(() => {
          setErrorMessageModal("");
          handleClose();
        }, 1000);
        setErrorMessageModal(CONFIRM_DEPOSIT);
      })
      .catch((error) => {
        setErrorMessageModal(ERROR_DEPOSIT);
      });
  }

  function checkEnterpriseEvaluation() {
    if (!isEnterpriseEvaluationDeposited()) {
      return (
        <Form.Group controlId="document" className="mb-3">
          <Form.Label className="labelFields">
            Dépôt d'évaluation de l'entreprise
          </Form.Label>
          <Form.Control
            type="file"
            onChange={(e) => {
              setDocument(e.target.files[0]);
            }}
            className="input_file_form mt-2"
            accept=".pdf"
          />
        </Form.Group>
      );
    } else {
      return (
        <>
          <a
            className="btn btn_submit btn-lg mb-3"
            href={GET_ENTERPRISE_EVALUATION_OF_INTERNSHIP + internship.id}
            target="_blank"
            rel="noreferrer"
          >
            Visualiser l'évaluation de l'entreprise
          </a>
        </>
      );
    }
  }

  function isEnterpriseEvaluationDeposited() {
    return (
      internship !== undefined &&
      internship.enterpriseEvaluation !== (null && undefined)
    );
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
                {checkEnterpriseEvaluation()}
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
              disabled={isEnterpriseEvaluationDeposited()}
            >
              {isEnterpriseEvaluationDeposited()
                ? "Évaluation déposée"
                : "Confirmer le dépot"}
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

export default InternshipApplicationSupervisorEnterpriseEvaluationModal;
