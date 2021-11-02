import React, { useEffect, useState } from "react";
import axios from "axios";
import auth from "../../services/Auth";
import { useHistory } from "react-router";
import InternshipOffer from "./InternshipOffer";
import "../../styles/List.css";
import { Container } from "react-bootstrap";

function InternshipOfferList() {
  let history = useHistory();
  let state = history.location.state || {};

  const [internshipOffers, setInternshipOffers] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  let title = auth.isInternshipManager()
    ? Object.keys(state).length === 0
      ? "Liste des offres de stages non validées"
      : state.title
    : auth.isStudent()
    ? "Liste des offres de stages de votre département"
    : "Liste de vos offres de stage";

  useEffect(() => {
    if (auth.isInternshipManager()) {
      if (
        title === "Liste des offres de stages non validées" ||
        title === "Rapport des offres non-validées"
      ) {
        axios
          .get(`http://localhost:9090/getAll/internshipOffer/unvalidated`)
          .then((response) => {
            setInternshipOffers(response.data);
          })
          .catch((err) => {
            setErrorMessage("Aucune Offre de stage à valider");
          });
      } else if (title === "Rapport des offres validées")
        axios
          .get(`http://localhost:9090/getAll/internshipOffer/validated`)
          .then((response) => {
            setInternshipOffers(response.data);
          })
          .catch((err) => {
            setErrorMessage("Aucune Offre de stage validée");
          });
    } else if (auth.isStudent()) {
      axios
        .get(
          `http://localhost:9090/getAll/internshipOffer/${auth.user.department}`
        )
        .then((response) => {
          setInternshipOffers(response.data);
        })
        .catch((err) => {
          setErrorMessage(
            "Aucune Offre de stage n'a été validé pour le moment"
          );
        });
    } else if (auth.isMonitor()) {
      axios
        .get(
          `http://localhost:9090/getAll/internshipOffer/monitor/${auth.user.id}`
        )
        .then((response) => {
          setInternshipOffers(response.data);
        })
        .catch((err) => {
          setErrorMessage(
            "Vous n'avez déposé aucune offre de stage pour le moment"
          );
        });
    }
  }, [title]);

  function showInternshipOffer(internshipOffer) {
    if (auth.isInternshipManager() || auth.isStudent()) {
      history.push({
        pathname: "/formInternshipOffer",
        state: internshipOffer,
      });
    } else if (auth.isMonitor()) {
      history.push({
        pathname: "/listInternshipApplication",
        state: internshipOffer,
      });
    }
  }

  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">{title}</h2>
        <Container className="cont_list">
          <p className="cont_title_form">{errorMessage}</p>
          <ul>
            {internshipOffers.map((internshipOffer) => (
              <InternshipOffer
                key={internshipOffers.indexOf(internshipOffer)}
                internshipOffer={internshipOffer}
                onDoubleClick={showInternshipOffer}
              />
            ))}
          </ul>
        </Container>
      </Container>
    </Container>
  );
}

export default InternshipOfferList;
