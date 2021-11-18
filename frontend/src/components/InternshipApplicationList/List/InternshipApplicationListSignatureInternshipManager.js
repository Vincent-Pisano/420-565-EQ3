import React, { useEffect, useState } from "react";
import axios from "axios";
import InternshipApplicationListTemplate from "../InternshipApplicationListTemplate";
import InternshipApplicationSignatureModal from "../Modal/InternshipApplicationSignatureModal";
import { TITLE_INTERNSHIP_APPLICATION_LIST_OF_USER } from "../../../Utils/TITLE";
import { GET_ALL_INTERNSHIP_APPLICATIONS_VALIDATED_NEXT_SESSIONS } from "../../../Utils/API";
import { ERROR_NO_INTERNSHIP_APPLICATION_VALIDATED_THIS_SESSION } from "../../../Utils/Errors_Utils";

function InternshipApplicationListSignatureInternshipManager() {
  let title = TITLE_INTERNSHIP_APPLICATION_LIST_OF_USER;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [currentInternshipApplication, setCurrentInternshipApplication] =
    useState({});
  const [internshipApplications, setInternshipApplications] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    axios
      .get(GET_ALL_INTERNSHIP_APPLICATIONS_VALIDATED_NEXT_SESSIONS)
      .then((response) => {
        setInternshipApplications(response.data);
      })
      .catch((err) => {
        setErrorMessage(ERROR_NO_INTERNSHIP_APPLICATION_VALIDATED_THIS_SESSION);
      });
  }, []);

  function showModal(internshipApplication) {
    setCurrentInternshipApplication(internshipApplication);
    handleShow();
  }

  return (
    <>
      <InternshipApplicationListTemplate
        title={title}
        internshipApplications={internshipApplications}
        errorMessage={errorMessage}
        onClick={showModal}
      />
      <InternshipApplicationSignatureModal
        show={show}
        handleClose={handleClose}
        currentInternshipApplication={currentInternshipApplication}
        internshipApplications={internshipApplications}
        setInternshipApplications={setInternshipApplications}
        setErrorMessage={setErrorMessage}
      />
    </>
  );
}

export default InternshipApplicationListSignatureInternshipManager;
