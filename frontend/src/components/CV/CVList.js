import React from "react";
import auth from "../../services/Auth";
import "../../styles/List.css";
import { Container } from "react-bootstrap";
import CVButtonDeposit from "./CVButtonDeposit";
import CVTable from "./CVTable";

const CVList = () => {
  let user = auth.user;

  return (
    <Container className="cont_list_cv">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">Liste de vos CVs</h2>
        <Container className="cont_list">
          <CVTable cvlist={user.cvlist} />
        </Container>
        <CVButtonDeposit />
      </Container>
    </Container>
  );
};

export default CVList;
