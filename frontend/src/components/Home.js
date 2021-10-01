import React from "react";
import auth from "../services/Auth";
import "../App.css"
import { Container, Row, Col, Card } from 'react-bootstrap';
import pfp from './../assets/img/pfp.png';
import CVList from "../components/CV/CVList"
import "./../styles/Home.css"

function Home() {

  let user = auth.user

  let dateFormat = formatDate(user.creationDate)

  function formatDate(dateString) {
    let date = new Date(dateString);
    let dateFormatted = date.toISOString().split("T")[0];
    return dateFormatted;
  }

  function checkIfStudent() {
    if (user.username.startsWith("E")) {
      return (
        <>
          <CVList />
        </>
      );
    }
  }
  
  return (
    <>
      <Container className="cont_home">
        <Row className="cont_central">
          <Col md="auto" className="cont_form">
            <h2>Bonjour {user.firstName}!</h2>
            <Row>
              <Card bg="secondary" text="white" className="pfp_card">
                <br />
                <Card.Img variant="top" src={pfp}/>
                <Card.Body>
                  <Card.Title>
                    <h4>Nom d'utilisateur: {user.username}</h4>
                  </Card.Title>
                  <h5>Prénom: {user.firstName}</h5>
                  <h5>Nom: {user.lastName}</h5>
                  <h5>Adresse courriel: {user.email}</h5>
                  <h5>Depuis {dateFormat}</h5>
                </Card.Body>
                <br />
              </Card>
            </Row>
          </Col>
        </Row> 
      </Container>
      {checkIfStudent()}
    </>
  );
}

export default Home;
