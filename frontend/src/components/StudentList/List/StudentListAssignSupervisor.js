import StudentList from "../StudentListTemplate";
import { session } from "../../../Utils/Store";
import AssignSupervisorModal from "../Modal/AssignSupervisorModal";
import { TITLE_STUDENT_SUPERVISOR_TO_ASSIGN } from "../../../Utils/TITLE";
import { GET_ALL_STUDENTS_WITHOUT_SUPERVISOR } from "../../../Utils/API";
import { React, useState, useEffect } from "react";
import axios from "axios";
import { ERROR_NO_STUDENT_TO_ASSIGN } from "../../../Utils/Errors";

function StudentListAssignSupervisor() {
  let supervisor =
    sessionStorage.getItem("supervisor") !== null
      ? JSON.parse(sessionStorage.getItem("supervisor"))
      : undefined;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [students, setStudents] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  const [currentStudent, setCurrentStudent] = useState(undefined);

  const title = TITLE_STUDENT_SUPERVISOR_TO_ASSIGN;

  useEffect(() => {
    axios
      .get(
        GET_ALL_STUDENTS_WITHOUT_SUPERVISOR +
          supervisor.department +
          "/" +
          session
      )
      .then((response) => {
        setStudents(response.data);
        setErrorMessage("");
      })
      .catch((err) => {
        setErrorMessage(ERROR_NO_STUDENT_TO_ASSIGN);
        setStudents([]);
      });
  }, []);

  function showModal(student) {
    setCurrentStudent(student);
    handleShow();
  }

  return (
    <>
      <StudentList
        title={title}
        students={students}
        errorMessage={errorMessage}
        onClick={showModal}
      />
      <AssignSupervisorModal
        show={show}
        handleClose={handleClose}
        students={students}
        setStudents={setStudents}
        setErrorMessage={setErrorMessage}
        currentStudent={currentStudent}
        supervisor={supervisor}
      />
    </>
  );
}

export default StudentListAssignSupervisor;
