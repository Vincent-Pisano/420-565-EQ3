import { React, useState, useEffect } from "react";
import { Container } from "react-bootstrap";
import { useHistory } from "react-router";
import auth from "../../../services/Auth";
import axios from "axios";
import Student from "../../StudentList/Student";

import { Row, Col, Modal, Button } from "react-bootstrap";

import "../../../styles/List.css";

function StudentReportList() {
  let history = useHistory();
  let state = history.location.state;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [students, setStudents] = useState([]);
  const [currentStudent, setCurrentStudent] = useState(undefined);
  const [errorMessage, setErrorMessage] = useState("");

  let title = state.title;

  useEffect(() => {
    if (title === "Étudiants avec aucun CV") {
      axios
        .get(`http://localhost:9090/getAll/students/without/CV`)
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage("Erreur! Aucun étudiants n'a pas de CV");
        });
    } else if (title === "Étudiants avec un CV non validé") {
      axios
        .get(`http://localhost:9090/getAll/student/CVActiveNotValid`)
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage("Erreur! Aucun étudiants a un CV à valider");
        });
    }else if (title === "Étudiants enregistrés") {
      axios
        .get(`http://localhost:9090/getAll/students`)
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage("Erreur! Aucun étudiants est enregistrés");
        });
    } else if (
      title === "Étudiants n'ayant aucune convocation à une entrevue"
    ) {
      axios
        .get(`http://localhost:9090/getAll/students/without/interviewDate`)
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage(
            "Erreur! Aucun étudiant n'a pas de convocation à une entrevue"
          );
        });
    } else if (title === "Étudiants en attente d’entrevue") {
      axios
        .get(`http://localhost:9090/getAll/students/waiting/interview`)
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage("Erreur! Aucun étudiant en attente d'entrevue");
        });
    } else if (
      title === "Étudiants n’ayant pas encore été évalués par leur moniteur"
    ) {
      axios
        .get(
          `http://localhost:9090/getAll/student/studentEvaluation/unevaluated`
        )
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage(
            "Erreur! Tous les étudiants ont été évalués par leur moniteur"
          );
        });
    } else if (title === "Étudiants ayant trouvé un stage") {
      axios
        .get(`http://localhost:9090/getAll/students/with/Internship`)
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage("Erreur! Aucuns étudiants n'a trouvé de stage.");
        });
    } else if (
      title ===
      "Étudiants dont le superviseur n’a pas encore évalué l’entreprise"
    ) {
      axios
        .get(
          `http://localhost:9090/getAll/student/enterpriseEvaluation/unevaluated`
        )
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage(
            "Erreur! Tous les étudiants ont leur entreprise évaluée par leurs superviseurs"
          );
        });
    }
    else if (
      title ===
      "Étudiants en attente d'une réponse d'entrevue"
    ) {
      axios
        .get(
          `http://localhost:9090/getAll/students/with/applicationStatus/waiting/and/interviewDate/passed/today`
        )
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage(
            "Erreur! Tous les étudiants ont leur entreprise évaluée par leurs superviseurs"
          );
        });
    }
  }, [title]);

  function onDoubleClick(student) {
    if (title === "Étudiants en attente d’entrevue") {
      history.push({
        pathname: `/reports/listInternshipApplication/${student.username}`,
        state: state,
      });
    } else {
      setCurrentStudent(student);
      handleShow();
    }
  }

  function formatDate(dateString) {
    let date = new Date(dateString);
    let dateFormatted = date.toISOString().split("T")[0];
    return dateFormatted;
  }

  function checkIfReport() {
    if (currentStudent !== undefined) {
      return (
        <Modal show={show} onHide={handleClose}>
          <Modal.Header>
            <Modal.Title>
              <h3>
                Informations de {currentStudent.firstName}{" "}
                {currentStudent.lastName}
              </h3>
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Row>
              <Col>
                <h4>Identifiant de l'étudiant: {currentStudent.username}</h4>
              </Col>
            </Row>
            <Row>
              <Col>
                <h5>Courriel de l'étudiant: {currentStudent.email}</h5>
              </Col>
            </Row>
            <Row>
              <Col>
                <h5>
                  Date d'inscription: {formatDate(currentStudent.creationDate)}
                </h5>
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
    }
  }

  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">{title}</h2>
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
                key={student.id}
                student={student}
                onDoubleClick={
                  auth.isInternshipManager() ? onDoubleClick : null
                }
              />
            ))}
          </ul>
        </Container>
      </Container>
      {checkIfReport()}
    </Container>
  );
}

export default StudentReportList;
