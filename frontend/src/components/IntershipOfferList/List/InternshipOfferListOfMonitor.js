import React, { useEffect, useState } from "react";
import axios from "axios";
import auth from "../../../services/Auth";
import { useHistory } from "react-router";
import InternshipOfferListTemplate from "../InternshipOfferListTemplate";
import { TITLE_INTERNSHIP_OFFER_LIST_OF_MONITOR } from "../../../Utils/TITLE";
import { URL_INTERNSHIP_APPLICATION_LIST_OF_INTERNSHIP_OFFER, URL_INTERNSHIP_OFFER_FORM } from "../../../Utils/URL";
import {
  GET_ALL_SESSIONS_OF_INTERNSHIP_OFFERS_OF_MONITOR,
  GET_ALL_INTERNSHIP_OFFER_OF_MONITOR,
} from "../../../Utils/API";
import {
  ERROR_NO_INTERNSHIP_OFFER_DEPOSITED,
  ERROR_NO_INTERNSHIP_OFFER_DEPOSITED_FOR_THIS_SESSION,
} from "../../../Utils/Errors_Utils";
import InternshipOfferModalMonitorRefusal from "../Modal/InternshipOfferModalMonitorRefusal"

function InternshipOfferListOfMonitor() {
  let history = useHistory();
  let user = auth.user;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [internshipOffers, setInternshipOffers] = useState([]);
  const [currentInternshipOffer, setCurrentInternshipOffer] = useState(undefined);
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

  function onInternshipOfferClicked(internshipOffer) {
    if (internshipOffer.status === "REFUSED") {
      setCurrentInternshipOffer(internshipOffer)
      handleShow()
    } else {
      showInternshipApplications(internshipOffer)
    }
  }

  function showInternshipApplications(internshipOffer) {
    history.push({
      pathname: URL_INTERNSHIP_APPLICATION_LIST_OF_INTERNSHIP_OFFER,
      state: {
        internshipOffer: internshipOffer,
      },
    });
  }

  function showInternshipOffer(internshipOffer) {
    history.push({
      pathname: URL_INTERNSHIP_OFFER_FORM,
      state: {
        internshipOffer: internshipOffer,
      },
    });
  }

  return (
    <>
    <InternshipOfferListTemplate
      internshipOffers={internshipOffers}
      sessions={sessions}
      currentSession={currentSession}
      setCurrentSession={setCurrentSession}
      title={title}
      errorMessage={errorMessage}
      onClick={onInternshipOfferClicked}
    />
<InternshipOfferModalMonitorRefusal
    show={show}
    handleClose={handleClose}
    currentInternshipOffer={currentInternshipOffer}
    showInternshipOffer={showInternshipOffer}
  />
    </>

  );
}

export default InternshipOfferListOfMonitor;
