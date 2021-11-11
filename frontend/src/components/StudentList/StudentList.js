import { React, useState, useEffect } from "react";
import { Container } from "react-bootstrap";
import { useHistory } from "react-router";
import auth from "../../services/Auth";
import axios from "axios";
import AssignSupervisorModal from "./AssignSupervisorModal";
import ValidCVModal from "./ValidCVModal";
import Student from "./Student";
import SessionDropdown from "../SessionDropdown/SessionDropdown";
import { session } from "../../Utils/Store";
import "../../styles/List.css";

function StudentList() {
  let history = useHistory();
  let supervisor = history.location.supervisor;

  let isStudentListAssigned =
    history.location.pathname === "/listStudents/assigned";
  let user = auth.user;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [students, setStudents] = useState([]);
  // eslint-disable-next-line no-unused-vars
  const [sessions, setSessions] = useState(
    auth.isSupervisor() ? user.sessions : []
  );
  const [currentSession, setCurrentSession] = useState(sessions[0]);
  const [currentStudent, setCurrentStudent] = useState(undefined);
  const [errorMessage, setErrorMessage] = useState("");

  let title = !auth.isInternshipManager()
    ? isStudentListAssigned
      ? "Étudiants qui vous sont assignés"
      : "Étudiants de votre département"
    : supervisor !== undefined
    ? "Étudiants de ce département à assigner"
    : "Étudiants avec un CV à valider";

  useEffect(() => {
    if (auth.isSupervisor()) {
      if (isStudentListAssigned) {
        axios
          .get(
            `http://localhost:9090/getAll/students/supervisor/${user.id}/${currentSession}`
          )
          .then((response) => {
            setStudents(response.data);
            setErrorMessage("");
          })
          .catch((err) => {
            setStudents([]);
            setErrorMessage(
              "Erreur! Aucun étudiant n'a été assigné pour le moment"
            );
          });
      } else {
        axios
          .get(
            `http://localhost:9090/getAll/students/${user.department}/${currentSession}`
          )
          .then((response) => {
            setStudents(response.data);
            setErrorMessage("");
          })
          .catch((err) => {
            setStudents([]);
            setErrorMessage(
              "Erreur! Aucun étudiant ne s'est inscrit pour le moment"
            );
          });
      }
    } else if (auth.isInternshipManager()) {
      if (supervisor !== undefined) {
        axios
          .get(
            `http://localhost:9090/getAll/students/noSupervisor/${supervisor.department}/${session}`
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
  }, [
    history,
    isStudentListAssigned,
    supervisor,
    title,
    user.department,
    user.id,
    currentSession,
  ]);

  function showModal(student) {
    setCurrentStudent(student);
    handleShow();
  }

  function checkIfSupervisor(student) {
    let state = {
      title: `Application aux offres de stage de : ${student.firstName} ${student.lastName}`,
    };
    history.push({
      pathname: `/listInternshipApplication/${student.username}`,
      state: state,
    });
  }

  function showSessionsList() {
    if (auth.isSupervisor() && sessions.length !== 0) {
      return (
        <SessionDropdown
          sessions={sessions}
          currentSession={currentSession}
          changeCurrentSession={changeCurrentSession}
        />
      );
    }
  }

  function changeCurrentSession(session) {
    setCurrentSession(session);
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
        {showSessionsList()}
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
                  auth.isInternshipManager()
                    ? showModal
                    : auth.isSupervisor()
                    ? checkIfSupervisor
                    : null
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

export default StudentList;
