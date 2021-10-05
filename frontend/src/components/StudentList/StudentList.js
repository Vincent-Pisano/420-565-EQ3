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
          setErrorMessage("Aucun étudiant ne s'est inscrit pour le moment");
        });
    } else if (auth.isInternshipManager()) {
      let supervisor = history.location.supervisor;
      if (supervisor !== undefined) {
        axios
          .get(
            `http://localhost:9090/getAll/students/noSupervisor/${supervisor.department}`
          )
          .then((response) => {
            setStudents(response.data);
          })
          .catch((err) => {
            setErrorMessage("Aucun étudiant ne s'est inscrit pour le moment");
          });
      } else {
        setErrorMessage("Erreur durant la sélection du Superviseur");
      }
    }
  }, [history]);

  function reset(student) {
    setCurrentStudent(student);
    handleShow();
    setErrorMessageModal("");
  }

  function AssignStudent(e) {
    e.preventDefault();
    if (auth.isInternshipManager()) {
      let supervisor = history.location.supervisor;
      if (supervisor !== undefined) {
        axios
          .post(
            `http://localhost:9090/assign/supervisor/${currentStudent.idUser}/${supervisor.idUser}`
          )
          .then((response) => {
            setStudents(response.data);
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
              Voulez-vous assigner le superviseur à
              {currentStudent !== undefined
                ? " " + currentStudent.firstName + " " + currentStudent.lastName
                : " l'étudiant choisi"}
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
          <p>{errorMessage}</p>
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
