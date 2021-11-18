import React, { useEffect, useState } from "react";
import axios from "axios";
import auth from "../../../services/Auth";
import { useHistory } from "react-router";
import InternshipOfferListTemplate from "../InternshipOfferListTemplate";
import { TITLE_INTERNSHIP_OFFER_LIST_OF_MONITOR } from "../../../Utils/TITLE";
import {
  GET_ALL_SESSIONS_OF_INTERNSHIP_OFFERS_OF_MONITOR,
  GET_ALL_INTERNSHIP_OFFER_OF_MONITOR,
} from "../../../Utils/API";
import {
  ERROR_NO_INTERNSHIP_OFFER_DEPOSITED,
  ERROR_NO_INTERNSHIP_OFFER_DEPOSITED_FOR_THIS_SESSION,
} from "../../../Utils/ERRORS";

function InternshipOfferListOfMonitor() {
  let history = useHistory();
  let user = auth.user;

  const [internshipOffers, setInternshipOffers] = useState([]);
  const [sessions, setSessions] = useState([]);
  const [currentSession, setCurrentSession] = useState(sessions[0]);
  const [errorMessage, setErrorMessage] = useState("");
  let title = TITLE_INTERNSHIP_OFFER_LIST_OF_MONITOR;

  useEffect(() => {
    if (sessions.length === 0 && currentSession === undefined) {
      axios
        .get(GET_ALL_SESSIONS_OF_INTERNSHIP_OFFERS_OF_MONITOR + user.id)
        .then((response) => {
          setSessions(response.data);
          setCurrentSession(response.data[0]);
        })
        .catch((err) => {
          setErrorMessage(ERROR_NO_INTERNSHIP_OFFER_DEPOSITED);
        });
    } else if (currentSession !== undefined) {
      axios
        .get(GET_ALL_INTERNSHIP_OFFER_OF_MONITOR(currentSession) + user.id)
        .then((response) => {
          setInternshipOffers(response.data);
        })
        .catch((err) => {
          setErrorMessage(ERROR_NO_INTERNSHIP_OFFER_DEPOSITED_FOR_THIS_SESSION);
        });
    }
  }, [currentSession, sessions.length, user.id]);

  function showInternshipOffer(internshipOffer) {
    history.push({
      pathname: "/listInternshipApplication",
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

export default InternshipOfferListOfMonitor;
