import { React, useState, useEffect } from "react";
import { Container } from "react-bootstrap";
import { useHistory } from "react-router";
import auth from "../../services/Auth";
import axios from "axios";
import AssignSupervisorModal from "./AssignSupervisorModal";
import ValidCVModal from "./ValidCVModal";
import Student from "./Student";
import "../../styles/List.css";

function StudentList() {
  let history = useHistory();
  let supervisor = history.location.supervisor;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [students, setStudents] = useState([]);
  const [currentStudent, setCurrentStudent] = useState(undefined);
  const [errorMessage, setErrorMessage] = useState("");

  let title = !auth.isInternshipManager()
    ? "Étudiants de votre département"
    : supervisor !== undefined
    ? "Étudiants de ce département à assigner"
    : "Étudiants avec un CV à valider";

  useEffect(() => {
    if (auth.isSupervisor()) {
      axios
        .get(`http://localhost:9090/getAll/students/${auth.user.department}`)
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage(
            "Erreur! Aucun étudiant ne s'est inscrit pour le moment"
          );
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
        axios
          .get(`http://localhost:9090/getAll/student/CVActiveNotValid`)
          .then((response) => {
            setStudents(response.data);
          })
          .catch((err) => {
            setErrorMessage("Erreur! Aucun CV à valider actuellement");
          });
      }
    }
  }, [history, supervisor, title]);

  function showModal(student) {
    setCurrentStudent(student);
    handleShow();
  }

  function checkIfGS() {
    if (auth.isInternshipManager()) {
      if (supervisor !== undefined) {
        return (
          <AssignSupervisorModal
            show={show}
            handleClose={handleClose}
            students={students}
            setStudents={setStudents}
            setErrorMessage={setErrorMessage}
            supervisor={supervisor}
            currentStudent={currentStudent}
          />
        );
      } else {
        return (
          <ValidCVModal
            show={show}
            handleClose={handleClose}
            students={students}
            setStudents={setStudents}
            setErrorMessage={setErrorMessage}
            currentStudent={currentStudent}
          />
        );
      }
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
                onDoubleClick={auth.isInternshipManager() ? showModal : null}
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
