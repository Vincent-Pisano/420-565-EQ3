import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import axios from "axios";
import auth from "../../../services/Auth";
import InternshipApplicationListTemplate from "../InternshipApplicationListTemplate";
import InternshipApplicationStudentModal from "../Modal/InternshipApplicationStudentModal";
import InternshipApplicationSignatureModal from "../Modal/InternshipApplicationSignatureModal";
import { GET_ALL_INTERNSHIP_APPLICATIONS_OF_STUDENT } from "../../../Utils/API";
import { ERROR_NO_INTERNSHIP_APPLICATION_OF_STUDENT_THIS_SESSION } from "../../../Utils/Errors_Utils";
import { TITLE_INTERNSHIP_APPLICATION_LIST_OF_USER } from "../../../Utils/TITLE";
import { URL_INTERNSHIP_OFFER_FORM } from "../../../Utils/URL";

function InternshipApplicationListOfStudent() {
  let user = auth.user;
  let history = useHistory();
  let state = history.location.state;
  let session = state !== undefined ? state.session : undefined;

  let sessions = auth.isStudent() ? user.sessions.reverse() : session;

  let title = TITLE_INTERNSHIP_APPLICATION_LIST_OF_USER;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [currentInternshipApplication, setCurrentInternshipApplication] =
    useState({});
  const [internshipApplications, setInternshipApplications] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  const [currentSession, setCurrentSession] = useState(sessions[0]);

  useEffect(() => {
    axios
      .get(
        GET_ALL_INTERNSHIP_APPLICATIONS_OF_STUDENT(currentSession) +
          user.username
      )
      .then((response) => {
        setInternshipApplications(response.data);
        setErrorMessage("");
      })
      .catch((err) => {
        setErrorMessage(
          ERROR_NO_INTERNSHIP_APPLICATION_OF_STUDENT_THIS_SESSION
        );
        setInternshipApplications([]);
      });
  }, [currentSession, user.username]);

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

  function checkForModal() {
    if (currentInternshipApplication.status === "VALIDATED") {
      return (
        <>
          <InternshipApplicationSignatureModal
            show={show}
            handleClose={handleClose}
            currentInternshipApplication={currentInternshipApplication}
          />
        </>
      );
    } else {
      return (
        <InternshipApplicationStudentModal
          show={show}
          handleClose={handleClose}
          currentInternshipApplication={currentInternshipApplication}
          showIntershipOffer={showIntershipOffer}
        />
      );
    }
  }

  return (
    <>
      <InternshipApplicationListTemplate
        title={title}
        internshipApplications={internshipApplications}
        errorMessage={errorMessage}
        onClick={showModal}
        sessions={sessions}
        currentSession={currentSession}
        setCurrentSession={setCurrentSession}
      />
      {checkForModal()}
    </>
  );
}

export default InternshipApplicationListOfStudent;
