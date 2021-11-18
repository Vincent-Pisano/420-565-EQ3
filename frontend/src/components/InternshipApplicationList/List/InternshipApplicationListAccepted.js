import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import axios from "axios";
import InternshipApplicationListTemplate from "../InternshipApplicationListTemplate";
import InternshipApplicationInternshipManagerModal from "../Modal/InternshipApplicationInternshipManagerModal";
import { GET_ALL_INTERNSHIP_APPLICATIONS_ACCEPTED_NEXT_SESSIONS } from "../../../Utils/API";
import { ERROR_NO_INTERNSHIP_APPLICATION_ACCEPTED_THIS_SESSION } from "../../../Utils/ERRORS";
import { TITLE_INTERNSHIP_APPLICATION_LIST_ACCEPTED } from "../../../Utils/TITLE";
import { URL_INTERNSHIP_OFFER_FORM } from "../../../Utils/URL";

function InternshipApplicationListAccepted() {
  let history = useHistory();

  let title = TITLE_INTERNSHIP_APPLICATION_LIST_ACCEPTED;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [currentInternshipApplication, setCurrentInternshipApplication] =
    useState({});
  const [internshipApplications, setInternshipApplications] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    axios
      .get(GET_ALL_INTERNSHIP_APPLICATIONS_ACCEPTED_NEXT_SESSIONS)
      .then((response) => {
        setInternshipApplications(response.data);
        setErrorMessage("");
      })
      .catch((err) => {
        setErrorMessage(ERROR_NO_INTERNSHIP_APPLICATION_ACCEPTED_THIS_SESSION);
        setInternshipApplications([]);
      });
  }, []);

  function showModal(internshipApplication) {
    setCurrentInternshipApplication(internshipApplication);
    handleShow();
  }

  function showIntershipOffer(internshipOffer) {
    history.push({
      pathname: URL_INTERNSHIP_OFFER_FORM,
      state: {
        internshipOffer: internshipOffer
      },
    });
  }

  return (
    <>
      <InternshipApplicationListTemplate
        title={title}
        internshipApplications={internshipApplications}
        errorMessage={errorMessage}
        onClick={showModal}
      />
      <InternshipApplicationInternshipManagerModal
        show={show}
        handleClose={handleClose}
        currentInternshipApplication={currentInternshipApplication}
        showIntershipOffer={showIntershipOffer}
        internshipApplications={internshipApplications}
        setInternshipApplications={setInternshipApplications}
        setErrorMessage={setErrorMessage}
      />
    </>
  );
}

export default InternshipApplicationListAccepted;
