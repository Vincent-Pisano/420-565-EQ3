import React, { useState, useEffect } from "react";
import axios from "axios";
import Student from "./Student";
import { useHistory } from "react-router";
import auth from "../../services/Auth";
import "../../styles/List.css";
import { Container } from "react-bootstrap";

function StudentList() {
  let history = useHistory();

  const [students, setStudents] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

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
              <Student key={student.idUser} student={student} />
            ))}
          </ul>
        </Container>
      </Container>
    </Container>
  );
}

export default StudentList;
