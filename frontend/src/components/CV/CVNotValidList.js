import React, { useEffect, useState } from "react";
import axios from "axios";
import { useHistory } from "react-router";
import "../../styles/List.css";
import { Container } from "react-bootstrap";
import CVTable from "./CVTable"

function CVNotValidList() {
  let history = useHistory();

  const [cvs, setCvs] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
      axios
        .get(`http://localhost:9090/getAll/CV/activeNotValid`)
        .then((response) => {
            setCvs(response.data);
        })
        .catch((err) => {
          setErrorMessage(
            "Aucun CV à valider"
          );
        });
  }, []);

  function showCvDetails(cv) {
    history.push({
      pathname: "/cvDetails",
      state: cv,
    });
  }

  function showList() {
    let pageTitle;

    pageTitle = "Liste des CV non validés";
   
    return (
      <Container className="cont_principal">
        <Container className="cont_list_centrar">
          <h2 className="cont_title_form">{pageTitle}</h2>
          <Container className="cont_list">
            <p className="cont_title_form">{errorMessage}</p>       
                <CVTable
                  cvlist={cvs}
                  onToggle={showCvDetails}
                />
          </Container>
        </Container>
      </Container>
    );
  }
  return <>{showList()}</>;
}
export default CVNotValidList; 