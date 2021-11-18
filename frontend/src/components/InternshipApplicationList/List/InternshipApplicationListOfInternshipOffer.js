import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import axios from "axios";
import InternshipApplicationListTemplate from "../InternshipApplicationListTemplate";
import InternshipApplicationSignatureModal from "../Modal/InternshipApplicationSignatureModal";
import InternshipApplicationMonitorModal from "../Modal/InternshipApplicationMonitorModal";
import { GET_ALL_INTERNSHIP_APPLICATIONS_OF_INTERNSHIP_OFFER } from "../../../Utils/API";
import { ERROR_NO_INTERNSHIP_APPLICATION_YET } from "../../../Utils/ERRORS";
import { TITLE_INTERNSHIP_APPLICATION_LIST_ACCEPTED } from "../../../Utils/TITLE";

function InternshipApplicationListOfInternshipOffer() {
  let history = useHistory();
  let state = history.location.state;
  let internshipOffer = state !== undefined ? state.internshipOffer : undefined;

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
      .get(
        GET_ALL_INTERNSHIP_APPLICATIONS_OF_INTERNSHIP_OFFER + internshipOffer.id
      )
      .then((response) => {
        setInternshipApplications(response.data);
      })
      .catch((err) => {
        setErrorMessage(ERROR_NO_INTERNSHIP_APPLICATION_YET);
      });
  }, [internshipOffer.id]);

  function showModal(internshipApplication) {
    setCurrentInternshipApplication(internshipApplication);
    handleShow();
  }

  function isCurrentApplicationCompleted() {
    return currentInternshipApplication.status === "COMPLETED";
  }

  function checkForModal() {
    if (isCurrentApplicationCompleted()) {
      return (
        <>
          <InternshipApplicationMonitorModal
            show={show}
            handleClose={handleClose}
            currentInternshipApplication={currentInternshipApplication}
          />
        </>
      );
    } else {
      return (
        <>
          <InternshipApplicationSignatureModal
            show={show}
            handleClose={handleClose}
            currentInternshipApplication={currentInternshipApplication}
          />
        </>
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

export default InternshipApplicationListOfInternshipOffer;
