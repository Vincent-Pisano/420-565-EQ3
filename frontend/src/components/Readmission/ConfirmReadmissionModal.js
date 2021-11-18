import { React, useState } from "react";
import { Button, Container, Modal, Row, Col, Form } from "react-bootstrap";
import auth from "../../services/Auth";
import axios from "axios";
import "../../styles/Form.css";
import { useFormFields } from "../../lib/hooksLib";
import { READMISSION_SUPERVISOR, READMISSION_STUDENT } from "../../Utils/API";
import {
  ERROR_READMISSION,
  ERROR_INVALID_PASSWORD,
  ERROR_PASSWORD_NOT_IDENTICAL,
  CONFIRM_READMISSION,
} from "../../Utils/ERRORS";

const ConfirmReadmissionModal = ({ show, handleClose, session }) => {
  const [errorMessageModal, setErrorMessageModal] = useState("");
  const [passwordFields, handleFieldChange] = useFormFields({
    password: "",
    confirmPassword: "",
  });

  let url = auth.isSupervisor()
    ? READMISSION_SUPERVISOR
    : auth.isStudent()
    ? READMISSION_STUDENT
    : "";

  let user = auth.user;

  function onConfirmModal(e) {
    e.preventDefault();
    if (passwordFields.password === passwordFields.confirmPassword) {
      if (
        passwordFields.password === user.password &&
        passwordFields.confirmPassword === user.password
      ) {
        axios
          .post(`${url + user.id}`)
          .then((response) => {
            auth.updateUser(response.data);
            setErrorMessageModal(CONFIRM_READMISSION(session));
            setTimeout(() => {
              setErrorMessageModal("");
              handleClose();
            }, 2000);
          })
          .catch((error) => {
            setErrorMessageModal(ERROR_READMISSION);
            setTimeout(() => {
              setErrorMessageModal("");
            }, 2000);
          });
      } else {
        setErrorMessageModal(ERROR_INVALID_PASSWORD);
      }
    } else {
      setErrorMessageModal(ERROR_PASSWORD_NOT_IDENTICAL);
    }
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>
          Réinscription pour la session "{session}"
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
        <Row style={{ textAlign: "center" }}>
          <Col md={12}>
            <Form>
              <Container className="cont_inputs">
                <Form.Group controlId="password">
                  <Form.Label className="discret mb-0">
                    Veuillez rentrez votre mot de passe
                  </Form.Label>
                  <Form.Control
                    value={passwordFields.password}
                    onChange={handleFieldChange}
                    type="password"
                    placeholder="Entrer votre mot de passe"
                    className="input_form"
                    required
                  />
                </Form.Group>
                <Form.Group controlId="confirmPassword">
                  <Form.Control
                    value={passwordFields.confirmPassword}
                    onChange={handleFieldChange}
                    type="password"
                    placeholder="Confirmer votre mot de passe"
                    className="input_form"
                    required
                  />
                </Form.Group>
              </Container>
            </Form>
          </Col>
        </Row>
        <Row style={{ textAlign: "center" }}>
          <Col md={6}>
            <Button
              variant="success"
              size="lg"
              className="btn_sub"
              onClick={(e) => onConfirmModal(e)}
            >
              Confirmer
            </Button>
          </Col>
          <Col md={6}>
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

export default ConfirmReadmissionModal;
