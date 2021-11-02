import { React, useState, useEffect } from "react";
import { Container } from "react-bootstrap";
import { useHistory } from "react-router";
import auth from "../../services/Auth";
import axios from "axios";
import AssignSupervisorModal from "./AssignSupervisorModal";
import ValidCVModal from "./ValidCVModal";
import Student from "./Student";
<<<<<<< HEAD
import ReportStudent from "../Reports/ReportStudent";

=======
>>>>>>> master
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
  const [currentStudent, setCurrentStudent] = useState(undefined);
  const [errorMessage, setErrorMessage] = useState("");

  let title = !auth.isInternshipManager()
    ? isStudentListAssigned 
      ? "Étudiants qui vous sont assignés" 
      : "Étudiants de votre département"
    : supervisor !== undefined
<<<<<<< HEAD
      ? state === undefined
        ? "Étudiants avec un CV à valider"
        : "Étudiants de ce département à assigner"
      : state.title;
=======
    ? "Étudiants de ce département à assigner"
    : "Étudiants avec un CV à valider";
>>>>>>> master

  useEffect(() => {
    if (auth.isSupervisor()) {
      if(isStudentListAssigned){
        axios
        .get(`http://localhost:9090/getAll/students/supervisor/${user.id}`)
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage(
            "Erreur! Aucun étudiant n'a été assigné pour le moment"
          );
        });
      }else{      
        axios
        .get(`http://localhost:9090/getAll/students/${user.department}`)
        .then((response) => {
          setStudents(response.data);
        })
        .catch((err) => {
          setErrorMessage(
            "Erreur! Aucun étudiant ne s'est inscrit pour le moment"
          );
        });
      }
    } else if (auth.isInternshipManager()) {
<<<<<<< HEAD
      if (title === "Rapport des étudiants avec aucun CV") {
        axios
          .get(`http://localhost:9090/getAll/students/without/CV`)
          .then((response) => {
            setStudents(response.data);
          })
          .catch((err) => {
            setErrorMessage("Aucun étudiants est enregistrés");
          });
      } else if (title === "Rapport non validé") {
        axios
          .get(`http://localhost:9090/getAll/student/CVActiveNotValid`)
          .then((response) => {
            setStudents(response.data);
          })
          .catch((err) => {
            setErrorMessage("Aucun étudiants est enregistrés");
          });
        } else if (title === "Rapport des étudiants n'ayant aucune convocation à entrevue") {
            axios
              .get(`http://localhost:9090/getAll/students/without/InterviewDate`)
              .then((response) => {
                setStudents(response.data);
              })
              .catch((err) => {
                setErrorMessage("Aucun étudiants est enregistrés");
            });
        } else if (title === "Rapport des étudiants ayant trouvé un stage") {
            axios
              .get(`http://localhost:9090/getAll/students/with/Internship`)
              .then((response) => {
                setStudents(response.data);
              })
              .catch((err) => {
                setErrorMessage("Aucun étudiant a trouvé un stage");
            });
      } else if (supervisor !== undefined) {
=======
      if (supervisor !== undefined) {
>>>>>>> master
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
<<<<<<< HEAD
      } else if (title === "Rapport des étudiants avec aucun CV") {
        // Ajouter studentDetails pour ceux qui n'ont pas de CV ici
      }
      else if (title === "Rapport des étudiants n'ayant aucune convocation à entrevue") {
        // Ajouter studentDetails pour ceux qui n'ont pas d'entrevue ici
      }
      else if (title === "Rapport des étudiants ayant trouvé un stage") {
        // Ajouter studentDetails pour ceux qui ont trouvé un stage
      }
      else {
=======
      } else {
>>>>>>> master
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

  function checkFromReport() {
    if (title === "Rapport des étudiants enregistrés" || title === "Rapport des étudiants ayant trouvé un stage") {
      return (
        <ul>
          {students.map((student) => (
            <ReportStudent
              key={student.id}
              student={student}
              
            />
          ))}
        </ul>
      )
    } else {
      return (
        <ul>
          {students.map((student) => (
            <Student
              key={student.id}
              student={student}
              onDoubleClick={auth.isInternshipManager() ? showModal : null}
            />
          ))}
        </ul>
      )
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
          {checkFromReport()}
        </Container>
      </Container>
      {checkIfGS()}
    </Container>
  );
}

export default StudentList;
