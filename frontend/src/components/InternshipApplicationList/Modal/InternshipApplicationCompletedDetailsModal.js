import React, { useEffect, useState } from "react";
import { Button, Container, Modal, Row, Col, Form } from "react-bootstrap";
import {
  GET_INTERNSHIP_BY_INTERNSHIP_APPLICATION,
  GET_ENTERPRISE_EVALUATION_OF_INTERNSHIP,
  GET_MONITOR_EVALUATION_OF_INTERNSHIP,
  GET_CONTRACT_OF_INTERNSHIP,
} from "../../../Utils/API";
import axios from "axios";
import "../../../styles/Form.css";

const InternshipApplicationDetailsModal = ({
  show,
  handleClose,
  currentInternshipApplication,
  showIntershipOffer,
}) => {
  const [internship, setInternship] = useState(undefined);

  let currentInternshipOffer =
    currentInternshipApplication !== undefined
      ? currentInternshipApplication.internshipOffer
      : { undefined };
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

  currentInternshipApplication =
    currentInternshipApplication !== undefined
      ? currentInternshipApplication
      : {};
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

  function isEnterpriseEvaluationDeposited() {
    return (
      internship !== undefined &&
      internship.enterpriseEvaluation !== (null && undefined)
    );
  }

  function isStudentEvaluationDeposited() {
    return (
      internship !== undefined &&
      internship.studentEvaluation !== (null && undefined)
    );
  }

  function checkEnterpriseEvaluation() {
    if (isEnterpriseEvaluationDeposited()) {
      return (
        <>
          <a
            className="btn btn_submit noHover btn-lg my-3"
            href={GET_ENTERPRISE_EVALUATION_OF_INTERNSHIP + internship.id}
            target="_blank"
            rel="noreferrer"
          >
            Visualiser l'évaluation de l'entreprise
          </a>
        </>
      );
    } else {
      return (
        <p style={{ color: "red" }}>
          L'évaluation de l'entreprise n'est pas déposé !
        </p>
      );
    }
  }

  function checkStudentEvaluation() {
    if (isStudentEvaluationDeposited()) {
      return (
        <>
          <a
            className="btn btn_submit noHover btn-lg my-3"
            href={GET_MONITOR_EVALUATION_OF_INTERNSHIP + internship.id}
            target="_blank"
            rel="noreferrer"
          >
            Visualiser l'évaluation de l'étudiant
          </a>
        </>
      );
    } else {
      return (
        <p style={{ color: "red" }}>
          L'évaluation de l'étudiant n'est pas déposé !
        </p>
      );
    }
  }

  function checkContract() {
    if (internship !== undefined) {
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
      );
    }
  }

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
          {checkEnterpriseEvaluation()}
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
                <Form.Group controlId="studentName">
                  <Form.Label className="labelFields">
                    Nom de l'étudiant
                  </Form.Label>
                  <Form.Control
                    value={
                      student !== undefined
                        ? student.lastName + " " + student.firstName
                        : ""
                    }
                    disabled
                    type="text"
                    className="input_form active_inp_form"
                    required
                  />
                </Form.Group>
                <Form.Group controlId="studentContact">
                  <Form.Label className="labelFields">
                    Contact de l'étudiant
                  </Form.Label>
                  <Form.Control
                    value={student !== undefined ? student.email : ""}
                    disabled
                    type="text"
                    placeholder="Entrer une description du poste"
                    className="input_form active_inp_form"
                    required
                  />
                </Form.Group>
                <hr className="modal_separator mx-auto" />
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
                {checkStudentEvaluation()}
                <hr className="modal_separator mx-auto" />
                {checkIfSupervisorAssigned()}
              </Container>
            </Form>
          </Col>
          <hr className="modal_separator mx-auto" />
        </Row>
        <Row style={{ textAlign: "center" }}>
          {checkContract()}
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
