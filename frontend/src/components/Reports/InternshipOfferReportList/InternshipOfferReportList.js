import React, { useEffect, useState } from "react";
import axios from "axios";
import { useHistory } from "react-router";
import InternshipOffer from "../../IntershipOfferList/InternshipOffer";
import "../../../styles/List.css";
import { Container } from "react-bootstrap";
import SessionDropdown from "../../SessionDropdown/SessionDropdown"
import "../../../styles/Session.css"

function InternshipOfferReportList() {
  let history = useHistory();
  let state = history.location.state;

  const [internshipOffers, setInternshipOffers] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  const [sessions, setSessions] = useState([]);
  const [currentSession, setCurrentSession] = useState(sessions[0]);
  let title = state.title;

  useEffect(() => {
    if (title === "Offres en attente de validation") {
      if (sessions.length === 0 && currentSession === undefined) {
        axios
          .get(`http://localhost:9090/getAll/sessions/invalid/internshipOffer`)
          .then((response) => {
            setSessions(response.data);
            setCurrentSession(response.data[0]);
          })
          .catch((err) => {
            setErrorMessage(`Aucune offre de stage déposée à valider...`);
          });
      } else if (currentSession !== undefined) {
      axios
        .get(`http://localhost:9090/getAll/internshipOffer/unvalidated/${currentSession}`)
        .then((response) => {
          setInternshipOffers(response.data);
        })
        .catch((err) => {
          setErrorMessage("Aucune Offre de stage à valider");
        });
      }
    } else if (title === "Offres validées") {
      if (sessions.length === 0 && currentSession === undefined) {
        axios
          .get(`http://localhost:9090/getAll/sessions/valid/internshipOffer`)
          .then((response) => {
            setSessions(response.data);
            setCurrentSession(response.data[0]);
          })
          .catch((err) => {
            setErrorMessage(`Aucune offre de stage validée...`);
          });
      } else if (currentSession !== undefined) {
      axios
        .get(`http://localhost:9090/getAll/internshipOffer/validated/${currentSession}`)
        .then((response) => {
          setInternshipOffers(response.data);
        })
        .catch((err) => {
          setErrorMessage("Aucune Offre de stage validée");
        });
      }
    }
  }, [currentSession, sessions.length, title]);

  function showInternshipOffer(internshipOffer) {
    history.push({
      pathname: "/formInternshipOffer",
      state: internshipOffer,
    });
  }

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
        <h2 className="cont_title_form">{title}</h2>
        {showSessionsList()}
        <Container className="cont_list">
          <p className="cont_title_form">{errorMessage}</p>
          <ul>
            {internshipOffers.map((internshipOffer) => (
              <InternshipOffer
                key={internshipOffers.indexOf(internshipOffer)}
                internshipOffer={internshipOffer}
                onClick={showInternshipOffer}
              />
            ))}
          </ul>
        </Container>
      </Container>
    </Container>
  );
}

export default InternshipOfferReportList;
