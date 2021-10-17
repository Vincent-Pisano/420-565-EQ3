import React, { useState } from "react";
import auth from "../services/Auth";
import axios from "axios";
import "../App.css";
import { Container, Row, Col, Card, Form } from "react-bootstrap";
import pfp from "./../assets/img/pfp.png";
import CVList from "../components/CV/CVList";
import ImgViewer from "../components/Viewer/IMGViewer";
import "./../styles/Home.css";
import "./../styles/Form.css";

function Home() {
  let user = auth.user;

  let dateFormat = formatDate(user.creationDate);
  const [errorMessage, setErrorMessage] = useState("");
  const [hasASignature, setHasASignature] = useState(user.signature !== undefined && user.signature !== null );

  function formatDate(dateString) {
    let date = new Date(dateString);
    let dateFormatted = date.toISOString().split("T")[0];
    return dateFormatted;
  }

  function checkIfStudent() {
    if (auth.isStudent()) {
      return (
        <>
          <CVList />
        </>
      );
    }
  }

  function saveSignature(signature) {
    console.log(signature);
    if (signature.type === "image/png" || signature.type === "image/jpeg") {
      setHasASignature(true)
      let formData = new FormData();
      formData.append("signature", signature);

      //à mettre dans then plus tard
      user.signature = signature;
      auth.user = user;

      axios
        .post(`http://localhost:9090/save/signature/${user.username}`, formData)
        .then((response) => {})
        .catch((error) => {
          setErrorMessage("Erreur lors de la sauvegarde de la signature");
        });
    } else {
      setErrorMessage("Sélectionnez une image PNG/JPG");
    }
  }

  function checkSignature() {
    if (hasASignature) {
      return (
        <>
          <ImgViewer image={user.signature}/>
        </>
      );
    } else {
      return (
        <Form className="mb-5 mt-2">
          <Form.Group controlId="document" className="cont_file_form">
            <Form.Label className="labelFields">Signature</Form.Label>
            <Form.Control
              type="file"
              onChange={(e) => {
                saveSignature(e.target.files[0]);
              }}
              className="input_file_form"
              accept="image/png, image/jpeg"
            />
          </Form.Group>
        </Form>
      );
    }
  }

  return (
    <>
      <Container className="cont_home">
        <Row className="cont_central">
          <Col xs={12} md={4}>
            <Row>
              <Card bg="secondary" text="white" className="pfp_card">
                <br />
                <Card.Img variant="top" src={pfp} />
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
              {checkSignature()}
              <p
                style={{
                  color: "red",
                }}
              >
                {errorMessage}
              </p>
            </Row>
          </Col>
          <Col xs={12} md={8}>
            {checkIfStudent()}
          </Col>
        </Row>
      </Container>
    </>
  );
}

export default Home;
