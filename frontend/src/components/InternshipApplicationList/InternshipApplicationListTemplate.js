import React from "react";
import "../../styles/List.css";
import "../../styles/Session.css";
import { Container } from "react-bootstrap";
import InternshipApplication from "./InternshipApplication";
import SessionDropdown from "../SessionDropdown/SessionDropdown";
import auth from "../../services/Auth";

function InternshipApplicationListTemplate({
    title,
    internshipApplications,
    errorMessage,
    onClick,
    sessions,
    currentSession,
    setCurrentSession,
}) {

  function changeCurrentSession(session) {
    setCurrentSession(session);
  }

  function showSessionsList() {
      if (auth.isStudent())
      return (
        <SessionDropdown
        sessions={sessions}
        currentSession={currentSession}
        changeCurrentSession={changeCurrentSession}
      />
      );
  }

  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">{title}</h2>
        {showSessionsList()}
        <Container className="cont_list">
          <p>{errorMessage}</p>
          <ul>
            {internshipApplications.map((internshipApplication) => (
              <InternshipApplication
                key={internshipApplication.id}
                internshipApplication={internshipApplication}
                onClick={onClick}
              />
            ))}
          </ul>
        </Container>
      </Container>
    </Container>
  );
}

export default InternshipApplicationListTemplate;
