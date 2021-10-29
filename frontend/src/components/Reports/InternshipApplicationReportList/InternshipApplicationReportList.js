import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import axios from "axios";
import "../../../styles/List.css";
import { Container } from "react-bootstrap";
import InternshipApplication from "./InternshipApplication";

function InternshipApplicationReportList() {
  
  let history = useHistory();

  let state = history.location.state;

  let title = state.title

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [currentInternshipApplication, setCurrentInternshipApplication] =
    useState({});
  const [internshipApplications, setInternshipApplications] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    setErrorMessage("");
    setInternshipApplications([]);
      axios
        .get(
            `http://localhost:9090/`
        )
        .then((response) => {
            setInternshipApplications(response.data);
        })
        .catch((err) => {
            setErrorMessage("Erreur");
        });
  }, []);

  function showModal(internshipApplication) {
    setCurrentInternshipApplication(internshipApplication);
    handleShow();
  }

  function checkForModal() {
    console.log("temp")
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
