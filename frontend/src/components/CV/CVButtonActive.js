import { Button, Modal, Row, Col } from "react-bootstrap";
import { useState } from "react";
import "./../../styles/CV.css";
import auth from "../../services/Auth";
import axios from "axios";
import { useHistory } from "react-router";

const CVButtonActive = ({ documentId }) => {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [errorMessage, setErrorMessage] = useState("");

  let history = useHistory();

  let user = auth.user;

  function reset() {
    handleShow();
    setErrorMessage("");
  }

  function onCreatePost(e) {
    e.preventDefault();
    axios
      .post(
        `http://localhost:9090/update/ActiveCV/${user.idUser}/${documentId}`
      )
      .then((response) => {
        user = response.data;
        auth.user = user;
        setErrorMessage("Le CV est maintenant actif");
        setTimeout(() => {
          handleClose();
          history.push({
            pathname: `/home/${user.username}`,
          });
        }, 1000);
      })
      .catch((error) => {
        setErrorMessage("Erreur de traitement de CV!");
      });
  }

  return (
    <>
      <button className="btn btn-warning btn-sm" onClick={reset}>
        Mettre Actif
      </button>

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
        </Modal.Footer>
      </Modal>
    </>
  );
};
export default CVButtonActive;
