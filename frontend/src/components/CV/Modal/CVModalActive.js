import { Button, Modal, Row, Col } from "react-bootstrap";
import { useState } from "react";
import "./../../../styles/CV.css";
import auth from "../../../services/Auth";
import axios from "axios";
import { useHistory } from "react-router";
import { UPDATE_ACTIVE_CV } from "../../../Utils/API";
import {
  ERROR_ACTIVE_CV,
  CONFIRM_ACTIVE_CV,
  WAIT_SENDING_EMAIL_ACTIVE_CV
} from "../../../Utils/Errors_Utils";

const CVModalActive = ({ handleClose, show, documentId }) => {
  const [errorMessage, setErrorMessage] = useState("");
  let history = useHistory();
  let user = auth.user;

  function onCreatePost(e) {
    e.preventDefault();
    setErrorMessage(WAIT_SENDING_EMAIL_ACTIVE_CV);
    axios
      .post(UPDATE_ACTIVE_CV + user.id + "/" + documentId)
      .then((response) => {
        user = response.data;
        auth.updateUser(user);
        setErrorMessage(CONFIRM_ACTIVE_CV);
        setTimeout(() => {
          handleClose();
          setErrorMessage("");
          history.push({
            pathname: `/home/${user.username}`,
          });
        }, 1000);
      })
      .catch((error) => {
        setErrorMessage(ERROR_ACTIVE_CV);
      });
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title>Êtes-vous certain de mettre ce CV actif?</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Row>
          <Col xs={6}>
            <Button
              variant="success"
              size="lg"
              className="btn_sub"
              onClick={(e) => onCreatePost(e)}
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
      </Modal.Body>
      <Modal.Footer>
        <Row style={{textAlign:"center"}}>
          <Col>
            <p
              className="error_p"
              style={{
                color: errorMessage.startsWith("Erreur") ? "red" : "green",
              }}
            >
              {errorMessage}
            </p>
          </Col>
        </Row>
      </Modal.Footer>
    </Modal>
  );
};
export default CVModalActive;
