import StudentList from "../StudentListTemplate";
import { useHistory } from "react-router";
import { React, useState, useEffect } from "react";
import axios from "axios";
import {
  ERROR_NO_STUDENT_SUBSCRIBED,
  ERROR_NO_STUDENTS_WITH_INTERNSHIP,
} from "../../../Utils/ERRORS";
import {
  GET_ALL_SESSIONS_OF_STUDENTS,
  GET_ALL_STUDENTS_WITH_INTERNSHIP,
} from "../../../Utils/API";
import { URL_INTERNSHIP_APPLICATION_LIST_COMPLETED_REPORT } from "../../../Utils/URL";
import { TITLE_INTERNSHIP_APPLICATION_LIST_COMPLETED } from "../../../Utils/TITLE";

function StudentListReportWithInternship() {
  let history = useHistory();
  let state = history.location.state;

  const [students, setStudents] = useState([]);
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
        .get(GET_ALL_STUDENTS_WITH_INTERNSHIP + currentSession)
        .then((response) => {
          setStudents(response.data);
          setErrorMessage("");
        })
        .catch((err) => {
          setErrorMessage(ERROR_NO_STUDENTS_WITH_INTERNSHIP);
          setStudents([]);
        });
    }
  }, [currentSession, sessions.length]);

  function showInternshipApplications(student) {
    history.push({
      pathname:
        URL_INTERNSHIP_APPLICATION_LIST_COMPLETED_REPORT + student.username,
      state: {
        title: TITLE_INTERNSHIP_APPLICATION_LIST_COMPLETED(
          student,
          currentSession
        ),
        session: currentSession,
      },
    });
  }

  return (
    <>
      <StudentList
        title={title}
        students={students}
        errorMessage={errorMessage}
        onClick={showInternshipApplications}
        sessions={sessions}
        currentSession={currentSession}
        setCurrentSession={setCurrentSession}
      />
    </>
  );
}

export default StudentListReportWithInternship;
