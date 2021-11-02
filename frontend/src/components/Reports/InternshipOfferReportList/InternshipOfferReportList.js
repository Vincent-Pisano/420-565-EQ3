import React, { useEffect, useState } from "react";
import axios from "axios";
import { useHistory } from "react-router";
import InternshipOffer from "../../IntershipOfferList/InternshipOffer";
import "../../../styles/List.css";
import { Container } from "react-bootstrap";

function InternshipOfferReportList() {
  let history = useHistory();
  let state = history.location.state;

  const [internshipOffers, setInternshipOffers] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  let title = state.title;

  useEffect(() => {
    if (title === "Rapport des offres non-validées") {
      axios
        .get(`http://localhost:9090/getAll/internshipOffer/unvalidated`)
        .then((response) => {
          setInternshipOffers(response.data);
        })
        .catch((err) => {
          setErrorMessage("Aucune Offre de stage à valider");
        });
    } else if (title === "Rapport des offres validées") {
      axios
        .get(`http://localhost:9090/getAll/internshipOffer/validated`)
        .then((response) => {
          setInternshipOffers(response.data);
        })
        .catch((err) => {
          setErrorMessage("Aucune Offre de stage validée");
        });
    }
  }, [title]);

  function showInternshipOffer(internshipOffer) {
    history.push({
      pathname: "/formInternshipOffer",
      state: internshipOffer,
    });
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

export default InternshipOfferReportList;
