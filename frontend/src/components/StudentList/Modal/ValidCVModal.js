import { React, useState } from "react";
import { Button, Modal, Row, Col } from "react-bootstrap";
import { useHistory } from "react-router";
import axios from "axios";
import auth from "../../../services/Auth";
import { VALIDATE_CV, VIEW_CV } from "../../../Utils/API"
import { ERROR_NO_MORE_CV_TO_VALID, ERROR_VALID_CV, CONFIRM_VALID_CV } from "../../../Utils/ERRORS"

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
      .post(VALIDATE_CV + currentStudent.id)
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
          setErrorMessage(ERROR_NO_MORE_CV_TO_VALID);
        }
        setTimeout(() => {
          setErrorMessageModal("");
          handleClose();
        }, 1000);
        setErrorMessageModal(ERROR_VALID_CV);
      })
      .catch((err) => {
        setErrorMessageModal(CONFIRM_VALID_CV);
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
              href={`${VIEW_CV}${
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
