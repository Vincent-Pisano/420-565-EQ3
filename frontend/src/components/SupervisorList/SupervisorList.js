import React, { useEffect, useState } from "react";
import axios from "axios";
import { session } from "../../Utils/Store"
import { Container } from "react-bootstrap";
import { useHistory } from "react-router";
import Supervisor from "./Supervisor";
import "../../styles/List.css";

function SupervisorList() {
  let history = useHistory();

  const [supervisors, setSupervisors] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    axios
      .get(`http://localhost:9090/getAll/supervisors/${session}`)
      .then((response) => {
        setSupervisors(response.data);
      })
      .catch((err) => {
        setErrorMessage("Aucun Superviseur enregistré pour le moment");
      });
  }, [supervisors.length]);

  function showAssignableStudents(supervisor) {
    sessionStorage.setItem("supervisor", JSON.stringify(supervisor));
    history.push({
      pathname: "/listStudents/assigned",
    });
  }

  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">Liste des superviseurs de stages</h2>
        <Container className="cont_list">
          <p>{errorMessage}</p>
          <ul>
            {supervisors.map((supervisor) => (
              <Supervisor
                key={supervisor.id}
                supervisor={supervisor}
                onClick={showAssignableStudents}
              />
            ))}
          </ul>
        </Container>
      </Container>
    </Container>
  );
}

export default SupervisorList;
