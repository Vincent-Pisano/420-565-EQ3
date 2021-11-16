import StudentList from "../StudentListTemplate";
import { useHistory } from "react-router";
import auth from "../../../services/Auth";
import { React, useState, useEffect } from "react";
import axios from "axios";
import {
  TITLE_STUDENT_LIST_ASSIGNED_TO_SUPERVISOR,
  TITLE_APPLICATION_LIST_OF_STUDENT,
} from "../../../Utils/TITLE";
import { GET_ALL_STUDENTS_OF_SUPERVISOR } from "../../../Utils/API";
import { ERROR_NO_STUDENT_ASSIGNED } from "../../../Utils/ERRORS";

function StudentListAssignedSupervisor() {
  let history = useHistory();
  let user = auth.user;

  const [students, setStudents] = useState([]);

  // eslint-disable-next-line no-unused-vars
  const [sessions, setSessions] = useState(user.sessions);
  const [currentSession, setCurrentSession] = useState(
    sessions.length > 0 ? sessions[sessions.length - 1] : sessions[0]
  );

  const [errorMessage, setErrorMessage] = useState("");

  let title = TITLE_STUDENT_LIST_ASSIGNED_TO_SUPERVISOR;

  useEffect(() => {
    axios
      .get(GET_ALL_STUDENTS_OF_SUPERVISOR + user.id + "/" + currentSession)
      .then((response) => {
        setStudents(response.data);
        setErrorMessage("");
      })
      .catch((err) => {
        setErrorMessage(
          ERROR_NO_STUDENT_ASSIGNED
        );
        setStudents([]);
      });
  }, [currentSession, user.id]);

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

export default StudentListAssignedSupervisor;
