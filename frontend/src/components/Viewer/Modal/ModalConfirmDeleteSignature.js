import { React, useState } from "react";
import { Button, Modal, Row, Col } from "react-bootstrap";
import axios from "axios";
import auth from "../../../services/Auth";
import { DELETE_SIGNATURE } from "../../../Utils/API";
import {
  ERROR_DELETE_SIGNATURE,
  CONFIRM_DELETE_SIGNATURE,
} from "../../../Utils/Errors_Utils";

const ModalConfirmDeleteSignature = ({
  show,
  handleClose,
  username,
  setHasASignature,
}) => {
  const [errorMessageModal, setErrorMessageModal] = useState("");

  function deleteSignature() {
    axios
      .post(DELETE_SIGNATURE(username))
      .then((response) => {
        auth.updateUser(response.data);
        setTimeout(() => {
          handleClose();
          setHasASignature(false)
        }, 3000);
        setErrorMessageModal(CONFIRM_DELETE_SIGNATURE);
      })
      .catch((err) => {
        setErrorMessageModal(ERROR_DELETE_SIGNATURE);
      });
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>
          Voulez-vous confirmer la suppresion de votre signature ?
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
        <Row style={{ textAlign: "center" }}>
          <Col md={3}>
            <Button
              variant="success"
              size="lg"
              className="btn_sub"
              onClick={deleteSignature}
            >
              Valider
            </Button>
          </Col>
          <Col md={3}>
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

export default ModalConfirmDeleteSignature;
