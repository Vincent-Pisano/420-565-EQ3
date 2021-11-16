import StudentList from "../StudentListTemplate";
import { useHistory } from "react-router";
import auth from "../../../services/Auth";
import { React, useState, useEffect } from "react";
import axios from "axios";
import {
  TITLE_STUDENT_LIST_OF_DEPARTMENT,
  TITLE_APPLICATION_LIST_OF_STUDENT,
} from "../../../Utils/TITLE";
import {
  GET_ALL_SESSIONS_OF_STUDENTS,
  GET_ALL_STUDENT_FROM_DEPARTMENT,
} from "../../../Utils/API";
import {
  ERROR_NO_STUDENT_SUBSCRIBED,
  ERROR_NO_STUDENT_SUBSCRIBED_TO_THIS_SESSION,
} from "../../../Utils/ERRORS";

function StudentListOfDepartment() {
  let history = useHistory();
  let user = auth.user;

  const [students, setStudents] = useState([]);

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

  function showStudent(student) {
    let state = {
      title:
        TITLE_APPLICATION_LIST_OF_STUDENT +
        student.firstName +
        " " +
        student.lastName,
      session: currentSession,
    };
    history.push({
      pathname: `/listInternshipApplication/${student.username}`,
      state: state,
    });
  }

  return (
    <StudentList
      title={title}
      students={students}
      errorMessage={errorMessage}
      onClick={showStudent}
      sessions={sessions}
      currentSession={currentSession}
      setCurrentSession={setCurrentSession}
    />
  );
}

export default StudentListOfDepartment;
