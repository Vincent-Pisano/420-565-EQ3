import StudentList from "../StudentListTemplate";
import auth from "../../../services/Auth";
import { React, useState, useEffect } from "react";
import StudentInfoModal from "../Modal/StudentInfoModal";
import axios from "axios";
import { TITLE_STUDENT_LIST_OF_DEPARTMENT } from "../../../Utils/TITLE";
import {
  GET_ALL_SESSIONS_OF_STUDENTS,
  GET_ALL_STUDENT_FROM_DEPARTMENT,
} from "../../../Utils/API";
import {
  ERROR_NO_STUDENT_SUBSCRIBED,
  ERROR_NO_STUDENT_SUBSCRIBED_TO_THIS_SESSION,
} from "../../../Utils/ERRORS";

function StudentListOfDepartment() {
  let user = auth.user;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [students, setStudents] = useState([]);
  const [currentStudent, setCurrentStudent] = useState(undefined);
  const [sessions, setSessions] = useState([]);
  const [currentSession, setCurrentSession] = useState(
    sessions.length > 0 ? sessions[sessions.length - 1] : sessions[0]
  );

  const [errorMessage, setErrorMessage] = useState("");

  let title = TITLE_STUDENT_LIST_OF_DEPARTMENT;

  useEffect(() => {
    if (sessions.length === 0 && currentSession === undefined) {
      axios
        .get(GET_ALL_SESSIONS_OF_STUDENTS)
        .then((response) => {
          setSessions(response.data);
          setCurrentSession(response.data[0]);
        })
        .catch((err) => {
          setErrorMessage(ERROR_NO_STUDENT_SUBSCRIBED);
        });
    } else if (currentSession !== undefined) {
      axios
        .get(
          GET_ALL_STUDENT_FROM_DEPARTMENT +
            user.department +
            "/" +
            currentSession
        )
        .then((response) => {
          setStudents(response.data);
          setErrorMessage("");
        })
        .catch((err) => {
          setErrorMessage(ERROR_NO_STUDENT_SUBSCRIBED_TO_THIS_SESSION);
          setStudents([]);
        });
    }
  }, [currentSession, sessions.length, user.department]);

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
        sessions={sessions}
        currentSession={currentSession}
        setCurrentSession={setCurrentSession}
      />
      <StudentInfoModal
        show={show}
        handleClose={handleClose}
        currentStudent={currentStudent}
      />
    </>
  );
}

export default StudentListOfDepartment;
