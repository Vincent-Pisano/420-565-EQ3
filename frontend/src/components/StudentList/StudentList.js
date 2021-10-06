import React, { useState, useEffect } from "react";
import { Button, Modal, Row, Col } from "react-bootstrap";
import axios from "axios";
import Student from "./Student";
import { useHistory } from "react-router";
import auth from "../../services/Auth";
import "../../styles/List.css";
import { Container } from "react-bootstrap";

function StudentList() {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  let history = useHistory();
  let supervisor = history.location.supervisor;

  const [students, setStudents] = useState([]);
  const [currentStudent, setCurrentStudent] = useState(undefined);
  const [errorMessage, setErrorMessage] = useState("");
  const [errorMessageModal, setErrorMessageModal] = useState("");

  useEffect(() => {
    if (auth.isSupervisor()) {
      axios
        .get(`http://localhost:9090/getAll/students/${auth.user.department}`)
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage("Erreur! Aucun étudiant ne s'est inscrit pour le moment");
        });
    } else if (auth.isInternshipManager()) {
      if (supervisor !== undefined) {
        axios
          .get(
            `http://localhost:9090/getAll/students/noSupervisor/${supervisor.department}`
          )
          .then((response) => {
            setStudents(response.data);
          })
          .catch((err) => {
            setErrorMessage("Erreur! Aucun étudiant à assigner actuellement");
          });
      } else {
        setErrorMessage("Erreur durant la sélection du Superviseur");
      }
    }
  }, [history, supervisor]);

  function reset(student) {
    setCurrentStudent(student);
    handleShow();
    setErrorMessageModal("");
  }

  function AssignStudent(e) {
    e.preventDefault();
    if (auth.isInternshipManager()) {
      if (supervisor !== undefined) {
        axios
          .post(
            `http://localhost:9090/assign/supervisor/${currentStudent.idUser}/${supervisor.idUser}`
          )
          .then((response) => {
            let assignedStudent = response.data;
            setStudents(
              students.filter((student) => {
                return student.idUser !== assignedStudent.idUser;
              })
            );
            if (students.length === 1) {
              setTimeout(() => {
                handleClose()
                history.push({
                  pathname: `/home/${auth.user.username}`,
                });
              }, 3000);
              setErrorMessage(
                "Plus aucun étudiant à assigner, vous allez être redirigé"
              );
            }
            setTimeout(() => {
              handleClose();
            }, 1000);
            setErrorMessageModal("Confirmation de l'assignation");
          })
          .catch((err) => {
            setErrorMessageModal("Erreur durant l'assignation du Superviseur");
          });
      } else {
        setErrorMessageModal("Erreur durant l'assignation du Superviseur");
      }
    }
  }

  function checkIfGS() {
    if (auth.isInternshipManager()) {
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
                  onClick={(e) => AssignStudent(e)}
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
                    color: errorMessageModal.startsWith("Erreur")
                      ? "red"
                      : "green",
                  }}
                >
                  {errorMessageModal}
                </p>
              </Col>
            </Row>
          </Modal.Footer>
        </Modal>
      );
    }
  }

  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">
          {auth.isSupervisor()
            ? "Étudiants de votre département"
            : "Étudiants de ce département à assigner"}
        </h2>
        <Container className="cont_list">
          <p
            className="error_p"
            style={{
              color: errorMessage.startsWith("Erreur") ? "red" : "green",
            }}
          >
            {errorMessage}
          </p>
          <ul>
            {students.map((student) => (
              <Student
                key={student.idUser}
                student={student}
                onDoubleClick={auth.isInternshipManager() ? reset : null}
              />
            ))}
          </ul>
        </Container>
      </Container>
      {checkIfGS()}
    </Container>
  );
}

export default StudentList;
