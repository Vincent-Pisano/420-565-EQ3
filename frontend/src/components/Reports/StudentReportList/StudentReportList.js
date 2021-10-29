import { React, useState, useEffect } from "react";
import { Container } from "react-bootstrap";
import { useHistory } from "react-router";
import auth from "../../../services/Auth";
import axios from "axios";
import Student from "../../StudentList/Student";

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
      if (title === "Rapport des étudiants avec aucun CV") {
        axios
          .get(`http://localhost:9090/getAll/students/without/CV`)
          .then((response) => {
            setStudents(response.data);
          })
          .catch((err) => {
            setErrorMessage("Erreur! Aucun étudiants n'a pas de CV");
          });
      } else if (title === "Rapport des étudiants enregistrés") {
        axios
          .get(`http://localhost:9090/getAll/student/CVActiveNotValid`)
          .then((response) => {
            setStudents(response.data);
          })
          .catch((err) => {
            setErrorMessage("Erreur! Aucun étudiants est enregistrés");
          });
      } else if (
        title ===
        "Rapport des étudiants n'ayant aucune convocation à une entrevue"
      ) {
        axios
          .get(`http://localhost:9090/getAll/students/without/InterviewDate`)
          .then((response) => {
            setStudents(response.data);
          })
          .catch((err) => {
            setErrorMessage(
              "Erreur! Aucun étudiants n'a pas de convocation à une entrevue"
            );
          });
      } else if (title === "Rapport des étudiants en attente d’entrevue") {
        axios
          .get(`http://localhost:9090/getAll/students/waiting/interview`)
          .then((response) => {
            setStudents(response.data);
          })
          .catch((err) => {
            setErrorMessage("Erreur! Aucun étudiants en attente d'entrevue");
          });
      }
  }, [title]);

  function onDoubleClick(student) {
    if (title === "Rapport des étudiants en attente d’entrevue") {
      history.push({
        pathname: `/listInternshipApplication/${student.id}`,
        state: student,
      });
    }
    else {
      setCurrentStudent(student);
      handleShow();
    }
    
  }

  function checkIfGS() {
    console.log("temp")
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
      {checkIfGS()}
    </Container>
  );
}

export default StudentReportList;
