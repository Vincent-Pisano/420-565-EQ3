import React, { useEffect, useState } from "react";
import { Button, Container, Modal, Row, Col, Form } from "react-bootstrap";
import {
  GET_INTERNSHIP_BY_INTERNSHIP_APPLICATION,
  GET_CONTRACT_OF_INTERNSHIP,
} from "../../../Utils/API";
import axios from "axios";
import "../../../styles/Form.css";

const InternshipApplicationCompletedMonitorInfo = ({
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
                <hr className="modal_separator mx-auto" />
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

export default InternshipApplicationCompletedMonitorInfo;
