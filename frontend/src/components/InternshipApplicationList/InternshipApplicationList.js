import React, { useEffect, useState } from "react";
import axios from "axios";
import auth from "../../services/Auth"
import "../../styles/List.css";
import { Container } from "react-bootstrap";
import { useHistory } from "react-router";
import Supervisor from "./InternshipApplication";

function InternshipApplicationList() {
  let history = useHistory();
  let user = auth.user;


  return (
    <Container className="cont_principal">
      <Container className="cont_list_centrar">
        <h2 className="cont_title_form">Liste de vos applications</h2>
        <Container className="cont_list">
          <ul>
            
          </ul>
        </Container>
      </Container>
    </Container>
  );
}

export default InternshipApplicationList;
