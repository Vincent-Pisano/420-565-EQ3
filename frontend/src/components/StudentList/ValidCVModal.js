import { React, useState } from "react";
import { Button, Modal, Row, Col } from "react-bootstrap";
import { useHistory } from "react-router";
import axios from "axios";
import auth from "../../services/Auth";

const ValidCVModal = ({
  show,
  handleClose,
  students,
  setStudents,
  setErrorMessage,
  currentStudent,
}) => {
  const [errorMessageModal, setErrorMessageModal] = useState("");

  let history = useHistory();

  function onConfirmModal(e) {
    e.preventDefault();
    ValidCV();
  }

  function ValidCV() {
    axios
      .post(`http://localhost:9090/validate/CV/${currentStudent.id}`)
      .then((response) => {
        let validatedStudent = response.data;
        setStudents(
          students.filter((student) => {
            return student.id !== validatedStudent.id;
          })
        );
        if (students.length === 1) {
          setTimeout(() => {
            handleClose();
            history.push({
              pathname: `/home/${auth.user.username}`,
            });
          }, 3000);
          setErrorMessage("Plus aucun CV à Valider, vous allez être redirigé");
        }
        setTimeout(() => {
          setErrorMessageModal("");
          handleClose();
        }, 1000);
        setErrorMessageModal("Confirmation de la validation");
      })
      .catch((err) => {
        setErrorMessageModal("Erreur durant la validation du CV");
      });
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>
          Voulez-vous confirmer le cv de l'étudiant choisi ?
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
        <Row style={{ textAlign: "center" }}>
          <Col md={3}>
            <Button
              variant="success"
              size="lg"
              className="btn_sub"
              onClick={(e) => onConfirmModal(e)}
            >
              Oui
            </Button>
          </Col>
          <Col md={6}>
            <a
              className="btn btn-warning btn-lg mt-3"
              download
              href={`http://localhost:9090/get/CV/document/${
                currentStudent === undefined
                  ? ""
                  : currentStudent.id +
                    "/" +
                    currentStudent.cvlist.filter(
                      (cv) => cv.isActive === true
                    )[0].id
              }`}
              target="_blank"
              rel="noreferrer"
            >
              Télécharger
            </a>
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

export default ValidCVModal;
