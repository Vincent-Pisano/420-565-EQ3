import React, { useEffect, useState } from "react";
import axios from "axios";
import { useHistory } from "react-router";
import InternshipOfferListTemplate from "../InternshipOfferListTemplate";
import { TITLE_INTERNSHIP_OFFER_LIST_UNVALIDATED } from "../../../Utils/TITLE";
import { GET_ALL_SESSIONS_OF_UNVALIDATED_INTERNSHIP_OFFERS, GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS } from "../../../Utils/API";
import { ERROR_NO_INTERNSHIP_OFFER_FOUND, ERROR_NO_INTERNSHIP_OFFER_TO_VALIDATE } from "../../../Utils/ERRORS";

function InternshipOfferListUnvalidated() {
  let history = useHistory();
  let state = history.location.state;

  const [internshipOffers, setInternshipOffers] = useState([]);
  const [sessions, setSessions] = useState([]);
  const [currentSession, setCurrentSession] = useState(sessions[0]);
  const [errorMessage, setErrorMessage] = useState("");
  let title = state !== undefined && state.title !== undefined ? state.title : TITLE_INTERNSHIP_OFFER_LIST_UNVALIDATED;

  useEffect(() => {
    if (sessions.length === 0 && currentSession === undefined) {
      axios
        .get(
            GET_ALL_SESSIONS_OF_UNVALIDATED_INTERNSHIP_OFFERS
        )
        .then((response) => {
          setSessions(response.data);
          setCurrentSession(response.data[0]);
        })
        .catch((err) => {
          setErrorMessage(ERROR_NO_INTERNSHIP_OFFER_FOUND);
        });
    } else if (currentSession !== undefined) {
      axios
        .get(
            GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS + currentSession
        )
        .then((response) => {
          setInternshipOffers(response.data);
        })
        .catch((err) => {
          setErrorMessage(ERROR_NO_INTERNSHIP_OFFER_TO_VALIDATE);
        });
    }
  }, [currentSession, sessions.length]);

  function showInternshipOffer(internshipOffer) {
    history.push({
      pathname: "/formInternshipOffer",
      state: internshipOffer,
    });
  }

  return (
    <InternshipOfferListTemplate
      internshipOffers={internshipOffers}
      sessions={sessions}
      currentSession={currentSession}
      setCurrentSession={setCurrentSession}
      title={title}
      errorMessage={errorMessage}
      onClick={showInternshipOffer}
    />
  );
}

export default InternshipOfferListUnvalidated;
