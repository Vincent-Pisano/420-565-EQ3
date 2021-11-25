import { useState } from "react";
import { Button, Modal, Row, Col, Form, Container } from "react-bootstrap";

import axios from "axios";
import { REFUSE_INTERNSHIP_OFFER } from "../../../Utils/API";
import {
  ERROR_REFUSING_INTERNSHIP_OFFER,
  CONFIRM_REFUSING_INTERNSHIP_OFFER,
} from "../../../Utils/Errors_Utils";
import "../../../styles/Form.css";

const CVModalActive = ({
  handleClose,
  show,
  redirect,
  internshipOffer,
  setErrorMessage,
}) => {
  let monitor =
    internshipOffer !== undefined ? internshipOffer.monitor : undefined;

  const [note, setNote] = useState("");

  function refuseInternshipOffer() {
    axios
      .post(REFUSE_INTERNSHIP_OFFER + internshipOffer.id, {
        refusalNote: note,
      })
      .then((response) => {
        setErrorMessage(CONFIRM_REFUSING_INTERNSHIP_OFFER);
        setTimeout(() => {
          redirect();
        }, 2000);
        handleClose();
      })
      .catch((error) => {
        handleClose();
        setErrorMessage(ERROR_REFUSING_INTERNSHIP_OFFER);
      });
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>
          Êtes-vous certain de refuser ce dépot d'offre de stage ?
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
        <Row style={{ textAlign: "center" }}>
          <Col md={12}>
            <Form>
              <Container className="cont_inputs">
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
                    className="input_form"
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
                    className="input_form"
                    required
                  />
                </Form.Group>
                <hr className="modal_separator mx-auto" />
                <Form.Group controlId="note">
                  <Form.Control
                    value={note}
                    onChange={(e) => setNote(e.currentTarget.value)}
                    type="text"
                    placeholder="Ajouter une note au refus (optionel)"
                    className="input_form"
                    required
                  />
                </Form.Group>
              </Container>
            </Form>
          </Col>
        </Row>
      </Modal.Body>
      <Modal.Footer>
        <Row>
          <Col xs={6}>
            <Button
              variant="success"
              size="lg"
              className="btn_sub"
              onClick={refuseInternshipOffer}
            >
              Oui
            </Button>
          </Col>
          <Col xs={6}>
            <Button
              variant="danger"
              size="lg"
              className="btn_sub"
              onClick={handleClose}
            >
              Non
            </Button>
          </Col>
        </Row>
      </Modal.Footer>
    </Modal>
  );
};
export default CVModalActive;
