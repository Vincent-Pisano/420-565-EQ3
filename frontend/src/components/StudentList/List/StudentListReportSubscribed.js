import StudentList from "../StudentListTemplate";
import { useHistory } from "react-router";
import StudentInfoModal from "../Modal/StudentInfoModal";
import { React, useState, useEffect } from "react";
import axios from "axios";
import {
  ERROR_NO_STUDENT_SUBSCRIBED,
  ERROR_NO_STUDENT_SUBSCRIBED_THIS_SESSION,
} from "../../../Utils/Errors_Utils";
import {
  GET_ALL_SESSIONS_OF_STUDENTS,
  GET_ALL_STUDENTS,
} from "../../../Utils/API";

function StudentListReportSubscribed() {
  let history = useHistory();
  let state = history.location.state;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [students, setStudents] = useState([]);
  const [currentStudent, setCurrentStudent] = useState(undefined);
  const [sessions, setSessions] = useState([]);
  const [currentSession, setCurrentSession] = useState(sessions[0]);
  const [errorMessage, setErrorMessage] = useState("");

  let title = state.title;

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
        .get(GET_ALL_STUDENTS + currentSession)
        .then((response) => {
          setStudents(response.data);
          setErrorMessage("");
        })
        .catch((err) => {
          setErrorMessage(ERROR_NO_STUDENT_SUBSCRIBED_THIS_SESSION);
          setStudents([]);
        });
    }
  }, [currentSession, sessions.length]);

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

export default StudentListReportSubscribed;
