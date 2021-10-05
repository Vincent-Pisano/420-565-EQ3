import React, { useEffect, useState } from "react";
import axios from "axios";
import Student from "./Student";
import auth from "../../services/Auth";
import "../../styles/List.css";
import { Container } from "react-bootstrap";

function ListStudents() {
  const [students, setStudents] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  let user = auth.user;
    useEffect(() => {
      axios
        .get(`http://localhost:9090/getAll/students/${user.department}`)
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage("Aucun étudiant ne s'est inscrit pour le moment");
        });
    }, [students.length]);

  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">Étudiants de votre département</h2>
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

export default ListStudents;
