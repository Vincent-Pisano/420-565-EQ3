import React from "react";
import auth from "../services/Auth";
import "../App.css"
import { Container } from 'react-bootstrap';

function Home() {

  let user = auth.user
  console.log(user)

  return (
    <Container className="cont_home">
      <h1>Bonjour {user.firstName} !</h1>
    </Container>
  );
}

export default Home;
