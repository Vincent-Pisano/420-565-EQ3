import React, { useEffect, useState } from "react";
import axios from "axios";
import auth from "../../../services/Auth";
import { useHistory } from "react-router";
import InternshipOfferListTemplate from "../InternshipOfferListTemplate";
import { TITLE_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT } from "../../../Utils/TITLE";
import {
  GET_ALL_NEXT_SESSIONS_OF_VALIDATED_INTERNSHIP_OFFERS,
  GET_ALL_SESSIONS_INTERNSHIP_OFFERS_OF_DEPARTMENT_NOT_APPLIED,
} from "../../../Utils/API";
import {
  ERROR_NO_INTERNSHIP_OFFER_FOUND,
  ERROR_NO_INTERNSHIP_OFFER_VALIDATED_YET,
} from "../../../Utils/Errors_Utils";
import { URL_INTERNSHIP_OFFER_FORM } from "../../../Utils/URL";

function InternshipOfferListOfDepartmentNotApplied() {
  let history = useHistory();
  let user = auth.user;

  const [internshipOffers, setInternshipOffers] = useState([]);
  const [sessions, setSessions] = useState([]);
  const [currentSession, setCurrentSession] = useState(sessions[0]);
  const [errorMessage, setErrorMessage] = useState("");
  let title = TITLE_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT;

  useEffect(() => {
    if (sessions.length === 0 && currentSession === undefined) {
      axios
        .get(GET_ALL_NEXT_SESSIONS_OF_VALIDATED_INTERNSHIP_OFFERS)
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
          GET_ALL_SESSIONS_INTERNSHIP_OFFERS_OF_DEPARTMENT_NOT_APPLIED +
            currentSession +
            "/" +
            user.username
        )
        .then((response) => {
          setInternshipOffers(response.data);
        })
        .catch((err) => {
          setErrorMessage(ERROR_NO_INTERNSHIP_OFFER_VALIDATED_YET);
        });
    }
  }, [currentSession, sessions.length, user.username]);

  function showInternshipOffer(internshipOffer) {
    history.push({
      pathname: URL_INTERNSHIP_OFFER_FORM,
      state: {
        internshipOffer: internshipOffer,
      },
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

export default InternshipOfferListOfDepartmentNotApplied;
