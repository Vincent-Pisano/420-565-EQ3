import React, { useEffect, useState } from "react";
import axios from "axios";
import auth from "../../services/Auth"
import "../../styles/List.css";
import { Container } from "react-bootstrap";
import InternshipApplication from "./InternshipApplication"

function InternshipApplicationList() {
  let user = auth.user;

  const [internshipApplications, setInternshipApplications] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    axios
      .get(`http://localhost:9090/getAll/internshipApplication/${user.username}`)
      .then((response) => {
        setInternshipApplications(response.data);
      })
      .catch((err) => {
        setErrorMessage("Aucune Application enregistrée pour le moment");
      });
  }, [user.username]);

  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">Liste de vos applications</h2>
        <Container className="cont_list">
          <p>{errorMessage}</p>
          <ul>
          {internshipApplications.map((internshipApplication) => (
              <InternshipApplication
                key={internshipApplication.id}
                internshipApplication={internshipApplication}
              />
            ))}
          </ul>
        </Container>
      </Container>
    </Container>
  );
}

export default InternshipApplicationList;
