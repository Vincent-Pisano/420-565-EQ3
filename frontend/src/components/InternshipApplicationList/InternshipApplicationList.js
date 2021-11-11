import React, { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router";
import axios from "axios";
import auth from "../../services/Auth";
import "../../styles/List.css";
import "../../styles/Session.css";
import { Container } from "react-bootstrap";
import InternshipApplication from "./InternshipApplication";
import InternshipApplicationStudentModal from "./Modal/InternshipApplicationStudentModal";
import InternshipApplicationInternshipManagerModal from "./Modal/InternshipApplicationInternshipManagerModal";
import InternshipApplicationSignatureModal from "./Modal/InternshipApplicationSignatureModal";
import InternshipApplicationMonitorModal from "./Modal/InternshipApplicationMonitorModal";
import InternshipApplicationSupervisorModal from "./Modal/InternshipApplicationSupervisorModal";
import InternshipApplicationSupervisorEnterpriseEvaluationModal from "./Modal/InternshipApplicationSupervisorEnterpriseEvaluationModal";
import SessionDropdown from "../SessionDropdown/SessionDropdown";

function InternshipApplicationList() {
  let user = auth.user;
  let history = useHistory();
  let params = useParams();
  let username = params.username;

  let state = history.location.state;
  let internshipOffer = history.location.state;
  let session = state !== undefined ? state.session : undefined;
  
  let isInternshipManagerSignature =
    history.location.pathname === "/listInternshipApplication/signature";

  let title = auth.isStudent()
    ? "Liste de vos applications de stage"
    : auth.isMonitor()
    ? "Listes des applications pour l'offre : " + internshipOffer.jobName
    : auth.isInternshipManager()
    ? isInternshipManagerSignature
      ? "Liste des applications de stages à signer"
      : "Liste des applications de stages acceptées"
    : auth.isSupervisor()
    ? state.title
    : "Vous ne devriez pas voir cette page";

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [currentInternshipApplication, setCurrentInternshipApplication] =
    useState({});
  const [internshipApplications, setInternshipApplications] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  // eslint-disable-next-line no-unused-vars
  const [sessions, setSessions] = useState(auth.isStudent() ? user.sessions : []);
  const [currentSession, setCurrentSession] = useState(sessions[0]);

  useEffect(() => {
    setErrorMessage("");
    setInternshipApplications([]);
    if (auth.isStudent()) {
    axios
      .get(
        `http://localhost:9090/getAll/internshipApplication/${currentSession}/student/${user.username}`
      )
      .then((response) => {
        setInternshipApplications(response.data);
      })
      .catch((err) => {
        setErrorMessage("Aucune Application enregistrée pour le moment");
      });
    } else if (auth.isInternshipManager()) {
      if (isInternshipManagerSignature) {
        axios
          .get(`http://localhost:9090/getAll/validated/internshipApplication`)
          .then((response) => {
            setInternshipApplications(response.data);
          })
          .catch((err) => {
            setErrorMessage("Aucune Application validée pour le moment");
          });
      } else {
        axios
          .get(`http://localhost:9090/getAll/accepted/internshipApplication`)
          .then((response) => {
            setInternshipApplications(response.data);
          })
          .catch((err) => {
            setErrorMessage("Aucune Application acceptée pour le moment");
          });
      }
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
    } else if (auth.isSupervisor()) {
      axios
        .get(
          `http://localhost:9090/getAll/internshipApplication/${session}/student/${username}`
        )
        .then((response) => {
          setInternshipApplications(response.data);
        })
        .catch((err) => {
          setErrorMessage("Erreur ! Aucune application de stage");
        });
    }
  }, [user.username, internshipOffer, isInternshipManagerSignature, username, sessions, currentSession, session]);

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

  function isCurrentApplicationCompleted() {
    return currentInternshipApplication.status === "COMPLETED";
  }

  function checkForModal() {
    if (auth.isStudent()) {
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
    } else if (auth.isInternshipManager()) {
      if (isInternshipManagerSignature) {
        return (
          <>
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
      } else {
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
      }
    } else if (auth.isMonitor()) {
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
    } else if (auth.isSupervisor()) {
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
  }

  function showSessionsList() {
    if (auth.isStudent()) {
      if (sessions.length !== 0) {
        return (
          <SessionDropdown
            sessions={sessions}
            currentSession={currentSession}
            changeCurrentSession={changeCurrentSession}
          />
        );
      }
    }
  }

  function changeCurrentSession(session) {
    setCurrentSession(session);
  }

  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">{title}</h2>
        {showSessionsList()}
        <Container className="cont_list">
          <p>{errorMessage}</p>
          <ul>
            {internshipApplications.map((internshipApplication) => (
              <InternshipApplication
                key={internshipApplication.id}
                internshipApplication={internshipApplication}
                onDoubleClick={showModal}
                isInternshipManagerSignature={isInternshipManagerSignature}
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
