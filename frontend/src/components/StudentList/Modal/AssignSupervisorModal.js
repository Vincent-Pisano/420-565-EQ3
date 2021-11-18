import { React, useState } from "react";
import { Button, Modal, Row, Col } from "react-bootstrap";
import { useHistory } from "react-router";
import axios from "axios";
import auth from "../../../services/Auth";
import { ASSIGN_SUPERVISOR_TO_STUDENT } from "../../../Utils/API";
import {
  ERROR_NO_MORE_STUDENT_TO_ASSIGN,
  ERROR_ASSIGN_SUPERVISOR,
  CONFIRM_ASSIGN_SUPERVISOR,
} from "../../../Utils/ERRORS";

const AssignSupervisorModal = ({
  show,
  handleClose,
  students,
  setStudents,
  setErrorMessage,
  supervisor,
  currentStudent,
}) => {
  const [errorMessageModal, setErrorMessageModal] = useState("");

  let history = useHistory();

  function AssignStudent() {
    axios
      .post(
        ASSIGN_SUPERVISOR_TO_STUDENT + currentStudent.id + "/" + supervisor.id
      )
      .then((response) => {
        let assignedStudent = response.data;
        setStudents(
          students.filter((student) => {
            return student.id !== assignedStudent.id;
          })
        );
        if (students.length === 1) {
          setTimeout(() => {
            handleClose();
            history.push({
              pathname: `/home/${auth.user.username}`,
            });
          }, 3000);
          setErrorMessage(ERROR_NO_MORE_STUDENT_TO_ASSIGN);
        }
        setTimeout(() => {
          setErrorMessageModal("");
          handleClose();
        }, 1000);
        setErrorMessageModal(CONFIRM_ASSIGN_SUPERVISOR);
      })
      .catch((err) => {
        setErrorMessageModal(ERROR_ASSIGN_SUPERVISOR);
      });
  }

  function onConfirmModal(e) {
    e.preventDefault();
    AssignStudent();
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>
          Voulez-vous assigner le superviseur{" "}
          {supervisor !== undefined
            ? " " + supervisor.firstName + " " + supervisor.lastName
            : " choisi"}{" "}
          à l'étudiant
          {currentStudent !== undefined
            ? " " + currentStudent.firstName + " " + currentStudent.lastName
            : " choisi"}{" "}
          ?
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Row>
          <Col xs={6}>
            <Button
              variant="success"
              size="lg"
              className="btn_sub"
              onClick={(e) => onConfirmModal(e)}
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

export default AssignSupervisorModal;
