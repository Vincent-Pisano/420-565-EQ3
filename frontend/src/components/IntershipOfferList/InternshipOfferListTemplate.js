import React from "react";
import InternshipOffer from "./InternshipOffer";
import SessionDropdown from "../SessionDropdown/SessionDropdown";
import "../../styles/List.css";
import "../../styles/Session.css";
import { Container } from "react-bootstrap";

function InternshipOfferListTemplate({
  internshipOffers,
  sessions,
  currentSession,
  setCurrentSession,
  title,
  errorMessage,
  onClick,
}) {
  function showSessionsList() {
    if (sessions.length !== 0) {
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
        <h2 className="cont_title_form mb-3 mt-3">{title}</h2>
        {showSessionsList()}
        <Container className="cont_list">
          <p className="cont_title_form">{errorMessage}</p>
          <ul>
            {internshipOffers.map((internshipOffer) => (
              <InternshipOffer
                key={internshipOffers.indexOf(internshipOffer)}
                internshipOffer={internshipOffer}
                onClick={onClick}
              />
            ))}
          </ul>
        </Container>
      </Container>
    </Container>
  );
}

export default InternshipOfferListTemplate;
