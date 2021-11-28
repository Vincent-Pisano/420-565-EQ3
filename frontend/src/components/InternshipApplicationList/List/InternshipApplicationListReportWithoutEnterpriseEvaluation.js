import React, { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router";
import axios from "axios";
import "../../../styles/List.css";
import InternshipApplicationListTemplate from "../InternshipApplicationListTemplate";
import InternshipApplicationCompletedSupervisorInfo from "../Modal/InternshipApplicationCompletedSupervisorInfo";
import { GET_ALL_COMPLETED_INTERNSHIP_APPLICATIONS_OF_STUDENT } from "../../../Utils/API";
import { ERROR_NO_INTERNSHIP_APPLICATION_YET } from "../../../Utils/Errors_Utils";
import { URL_INTERNSHIP_OFFER_FORM } from "../../../Utils/URL";

function InternshipApplicationListReportWithoutEnterpriseEvaluation() {
  let history = useHistory();
  let params = useParams();
  let username = params.username;

  let state = history.location.state;
  let title = state.title;
  let session = state.session;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [currentInternshipApplication, setCurrentInternshipApplication] =
    useState(undefined);
  const [internshipApplications, setInternshipApplications] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    axios
      .get(
        GET_ALL_COMPLETED_INTERNSHIP_APPLICATIONS_OF_STUDENT(session) + username
      )
      .then((response) => {
        setInternshipApplications(response.data);
        setErrorMessage("");
      })
      .catch((err) => {
        setErrorMessage(ERROR_NO_INTERNSHIP_APPLICATION_YET);
        setInternshipApplications([]);
      });
  }, [session, username]);

  function showModal(internshipApplication) {
    setCurrentInternshipApplication(internshipApplication);
    handleShow();
  }

  function showIntershipOffer(internshipOffer) {
    history.push({
      pathname: URL_INTERNSHIP_OFFER_FORM,
      state: {
        internshipOffer: internshipOffer,
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
      <InternshipApplicationCompletedSupervisorInfo
        show={show}
        handleClose={handleClose}
        currentInternshipApplication={currentInternshipApplication}
        showIntershipOffer={showIntershipOffer}
      />
    </>
  );
}

export default InternshipApplicationListReportWithoutEnterpriseEvaluation;
