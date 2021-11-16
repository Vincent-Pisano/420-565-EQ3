import StudentList from "../StudentListTemplate";
import ValidCVModal from "../Modal/ValidCVModal";

import { React, useState, useEffect } from "react";
import axios from "axios";
import { TITLE_STUDENT_LIST_CV_TO_VALIDATE } from "../../../Utils/TITLE";
import {
  GET_ALL_SESSIONS_OF_STUDENTS,
  GET_ALL_STUDENT_WITH_CV_ACTIVE_NOT_VALID,
} from "../../../Utils/API";
import { ERROR_NO_STUDENT_SUBSCRIBED, ERROR_NO_CV_TO_VALIDATE } from "../../../Utils/ERRORS";

function StudentListCVToValidate() {
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

  let title = TITLE_STUDENT_LIST_CV_TO_VALIDATE;

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
        .get(GET_ALL_STUDENT_WITH_CV_ACTIVE_NOT_VALID + currentSession)
        .then((response) => {
          setStudents(response.data);
          setErrorMessage("");
        })
        .catch((err) => {
          setErrorMessage(ERROR_NO_CV_TO_VALIDATE);
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
      <ValidCVModal
        show={show}
        handleClose={handleClose}
        students={students}
        setStudents={setStudents}
        setErrorMessage={setErrorMessage}
        currentStudent={currentStudent}
      />
    </>
  );
}

export default StudentListCVToValidate;
