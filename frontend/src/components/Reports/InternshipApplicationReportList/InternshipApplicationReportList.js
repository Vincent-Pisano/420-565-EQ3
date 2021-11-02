import React, { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router";
import axios from "axios";
import "../../../styles/List.css";
import { Container } from "react-bootstrap";
import InternshipApplication from "../../InternshipApplicationList/InternshipApplication";
import InternshipApplicationDetailsModal from "./InternshipApplicationDetailsModal";

function InternshipApplicationReportList() {
  let history = useHistory();
  let params = useParams();
  let username = params.username;

  let state = history.location.state;

  let title = state.title;

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [currentInternshipApplication, setCurrentInternshipApplication] =
    useState(undefined);
  const [internshipApplications, setInternshipApplications] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    setErrorMessage("");
    setInternshipApplications([]);
    if (title === "Rapport des étudiants en attente d’entrevue")
      axios
        .get(
          `http://localhost:9090/getAll/internshipApplication/student/${username}`
        )
        .then((response) => {
          setInternshipApplications(response.data);
        })
        .catch((err) => {
          setErrorMessage(
            "Erreur lors de la récupération des applications de stages"
          );
        });
  }, [title, username]);

  function showModal(internshipApplication) {
    setCurrentInternshipApplication(internshipApplication);
    handleShow();
  }

  function showIntershipOffer(internshipOffer) {
    history.push({
      pathname: "/formInternshipOffer",
      state: internshipOffer,
    });
  }

  function checkForModal() {
    return (
      <InternshipApplicationDetailsModal
        show={show}
        handleClose={handleClose}
        currentInternshipApplication={currentInternshipApplication}
        showIntershipOffer={showIntershipOffer}
      />
    );
  }

  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">{title}</h2>
        <Container className="cont_list">
          <p>{errorMessage}</p>
          <ul>
            {internshipApplications.map((internshipApplication) => (
              <InternshipApplication
                key={internshipApplication.id}
                internshipApplication={internshipApplication}
                onDoubleClick={showModal}
              />
            ))}
          </ul>
        </Container>
      </Container>
      {checkForModal()}
    </Container>
  );
}

export default InternshipApplicationReportList;
