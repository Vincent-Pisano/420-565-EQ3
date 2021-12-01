import { React, useState } from "react";
import { Button, Modal, Row, Col, Form } from "react-bootstrap";
import axios from "axios";
import auth from "../../../services/Auth";
import { SAVE_SIGNATURE } from "../../../Utils/API";
import {
  ERROR_SAVE_SIGNATURE,
  ERROR_SELECT_PNG,
  CONFIRM_SAVE_SIGNATURE,
} from "../../../Utils/Errors_Utils";

const ModalDepositSignature = ({
  show,
  handleClose,
  username,
  setHasASignature,
}) => {
  const [errorMessageModal, setErrorMessageModal] = useState("");
  const [signature, setSignature] = useState(undefined);

  function saveSignature() {
    if (signature.type === "image/png") {
      let formData = new FormData();
      formData.append("signature", signature);
      axios
        .post(SAVE_SIGNATURE + username, formData)
        .then((response) => {
          auth.user.signature = response.data;
          auth.updateUser(auth.user);
          setTimeout(() => {
            handleClose();
            setHasASignature(true)
          }, 3000);
          setErrorMessageModal(CONFIRM_SAVE_SIGNATURE);
        })
        .catch((error) => {
          setErrorMessageModal(ERROR_SAVE_SIGNATURE);
        });
    } else {
      setErrorMessageModal(ERROR_SELECT_PNG);
    }
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>
          Dépôt de la signature
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
        <Row style={{ textAlign: "center" }}>
          <Col md={12}>
            <Form className="mb-5">
              <Form.Group controlId="document" className="cont_file_form">
                <Form.Label className="labelFields">Signature</Form.Label>
                <Form.Control
                  type="file"
                  onChange={(e) => {
                    setSignature(e.target.files[0]);
                  }}
                  className="input_file_form"
                  accept="image/png"
                />
              </Form.Group>
            </Form>
          </Col>
          <hr className="modal_separator mx-auto" />
        </Row>
        <Row style={{ textAlign: "center" }}>
          <Col md={3}>
            <Button
              variant="success"
              size="lg"
              className="btn_sub"
              onClick={saveSignature}
            >
              Déposer
            </Button>
          </Col>
          <Col md={3}>
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

export default ModalDepositSignature;
