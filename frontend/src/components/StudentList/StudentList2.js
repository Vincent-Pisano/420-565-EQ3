
import { Container } from "react-bootstrap";
import Student from "./Student";
import SessionDropdown from "../SessionDropdown/SessionDropdown";
import "../../styles/List.css";

function StudentList({
  title,
  students,
  errorMessage,
  onClick,
  sessions,
  currentSession,
  setCurrentSession,
}) {
  function showSessionsList() {
    if (sessions !== undefined && sessions.length !== 0) {
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
              <Student key={student.id} student={student} onClick={onClick} />
            ))}
          </ul>
        </Container>
      </Container>
    </Container>
  );
}

export default StudentList;
