import React, { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router";
import axios from "axios";
import InternshipApplicationListTemplate from "../InternshipApplicationListTemplate";
import InternshipApplicationSupervisorEnterpriseEvaluationModal from "../Modal/InternshipApplicationSupervisorEnterpriseEvaluationModal";
import InternshipApplicationSupervisorModal from "../Modal/InternshipApplicationSupervisorModal";
import { GET_ALL_INTERNSHIP_APPLICATIONS_OF_STUDENT } from "../../../Utils/API";
import { ERROR_NO_INTERNSHIP_APPLICATION_YET } from "../../../Utils/Errors_Utils";
import { URL_INTERNSHIP_OFFER_FORM } from "../../../Utils/URL";

function InternshipApplicationListOfStudentAssigned() {
  let history = useHistory();
  let params = useParams();
  let username = params.username;
  let state = history.location.state;
  let session = state !== undefined ? state.session : undefined;

  let title = state !== undefined ? state.title : "";

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [currentInternshipApplication, setCurrentInternshipApplication] =
    useState({});
  const [internshipApplications, setInternshipApplications] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    axios
      .get(GET_ALL_INTERNSHIP_APPLICATIONS_OF_STUDENT(session) + username)
      .then((response) => {
        setInternshipApplications(response.data);
      })
      .catch((err) => {
        setErrorMessage(ERROR_NO_INTERNSHIP_APPLICATION_YET);
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

  function isCurrentApplicationCompleted() {
    return currentInternshipApplication.status === "COMPLETED";
  }

  function checkForModal() {
    if (isCurrentApplicationCompleted()) {
      return (
        <>
          <InternshipApplicationSupervisorEnterpriseEvaluationModal
            show={show}
            handleClose={handleClose}
            currentInternshipApplication={currentInternshipApplication}
          />
        </>
      );
    } else {
      return (
        <InternshipApplicationSupervisorModal
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
      />
      {checkForModal()}
    </>
  );
}

export default InternshipApplicationListOfStudentAssigned;
