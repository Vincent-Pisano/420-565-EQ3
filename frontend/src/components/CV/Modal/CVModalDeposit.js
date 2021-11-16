import { Button, Modal, Row, Col, Form } from "react-bootstrap";
import { useState } from "react";
import "./../../../styles/CV.css";
import auth from "../../../services/Auth";
import axios from "axios";
import { useHistory } from "react-router";
import { SAVE_CV } from "../../../Utils/API";
import {
  ERROR_SAVE_CV,
  ERROR_CV_INVALID_FORMAT,
  ERROR_CV_LIST_MAX_SIZE,
  CONFIRM_SAVE_CV,
} from "../../../Utils/ERRORS";

const CVModalDelete = ({ handleClose, show }) => {
  const [document, setDocument] = useState(undefined);
  const [errorMessage, setErrorMessage] = useState("");

  let history = useHistory();

  let user = auth.user;

  function onCreatePost(e) {
    e.preventDefault();
    if (document.type === "application/pdf") {
      if (user.cvlist.length < 10) {
        let formData = new FormData();
        formData.append("document", document);
        axios
          .post(SAVE_CV + user.id, formData)
          .then((response) => {
            user = response.data;
            auth.updateUser(user);
            setErrorMessage(CONFIRM_SAVE_CV);
            setTimeout(() => {
              handleClose();
              setErrorMessage("")
              history.push({
                pathname: `/home/${user.username}`,
              });
            }, 1000);
          })
          .catch((error) => {
            setErrorMessage(ERROR_SAVE_CV);
          });
      } else {
        setErrorMessage(ERROR_CV_LIST_MAX_SIZE);
      }
    } else {
      setErrorMessage(ERROR_CV_INVALID_FORMAT);
    }
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title>Ajout d'un CV</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Row>
          <Col xs={9}>
            <Form.Group controlId="document" className="cont_file_form">
              <Form.Control
                type="file"
                onChange={(e) => {
                  setDocument(e.target.files[0]);
                }}
                accept=".pdf"
              />
            </Form.Group>
          </Col>
          <Col xs={3}>
            <Button
              variant="success"
              size="md"
              className="btn_sub"
              onClick={(e) => onCreatePost(e)}
              disabled={document === undefined}
            >
              Déposer
            </Button>
          </Col>
        </Row>
        <Row>
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
      </Modal.Body>
      <Modal.Footer>
        <Button variant="danger" size="lg" onClick={handleClose}>
          Fermer
        </Button>
      </Modal.Footer>
    </Modal>
  );
};
export default CVModalDelete;
