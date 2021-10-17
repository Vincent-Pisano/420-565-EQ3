import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import axios from "axios";
import auth from "../../services/Auth";
import "../../styles/List.css";
import { Container } from "react-bootstrap";
import InternshipApplication from "./InternshipApplication";
import InternshipApplicationStudentModal from "./InternshipApplicationStudentModal";
import InternshipApplicationInternshipManagerModal from "./InternshipApplicationInternshipManagerModal";
import InternshipApplicationMonitorModal from "./InternshipApplicationMonitorModal";

function InternshipApplicationList() {
  let user = auth.user;
  let history = useHistory();

  let internshipOffer = history.location.state;

  let title = auth.isStudent()
    ? "Liste de vos applications de stage"
    : auth.isInternshipManager()
    ? "Liste des applications de stages acceptées"
    : auth.isMonitor()
    ? "Listes des applications pour l'offre : " + internshipOffer.jobName
    : "Vous ne devriez pas voir cette page";

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [currentInternshipApplication, setCurrentInternshipApplication] =
    useState({});
  const [internshipApplications, setInternshipApplications] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    if (auth.isStudent()) {
      axios
        .get(
          `http://localhost:9090/getAll/internshipApplication/student/${user.username}`
        )
        .then((response) => {
          setInternshipApplications(response.data);
        })
        .catch((err) => {
          setErrorMessage("Aucune Application enregistrée pour le moment");
        });
    } else if (auth.isInternshipManager()) {
      axios
        .get(`http://localhost:9090/getAll/accepted/internshipApplication`)
        .then((response) => {
          setInternshipApplications(response.data);
        })
        .catch((err) => {
          setErrorMessage("Aucune Application acceptée pour le moment");
        });
    } else if (auth.isMonitor()) {
      axios
        .get(
          `http://localhost:9090/getAll/internshipApplication/internshipOffer/${internshipOffer.id}`
        )
        .then((response) => {
          setInternshipApplications(response.data);
        })
        .catch((err) => {
          setErrorMessage("Aucune Application enregistrée pour le moment");
        });
    }
  }, [user.username, internshipOffer]);

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
    if (auth.isStudent()) {
      return (
        <InternshipApplicationStudentModal
          show={show}
          handleClose={handleClose}
          currentInternshipApplication={currentInternshipApplication}
          showIntershipOffer={showIntershipOffer}
        />
      );
    } else if (auth.isInternshipManager()) {
      return (
        <>
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
    } else if (auth.isMonitor()) {
      return (
        <>
          <InternshipApplicationMonitorModal
            show={show}
            handleClose={handleClose}
          />
        </>
      );
    }
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

export default InternshipApplicationList;
