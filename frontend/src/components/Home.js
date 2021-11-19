import React, { useState } from "react";
import auth from "../services/Auth";
import axios from "axios";
import { session } from "../Utils/Store";
import { Container, Row, Col, Card, Form } from "react-bootstrap";
import pfp from "./../assets/img/pfp.png";
import CVList from "../components/CV/CVList";
import ConfirmSubscribeModal from "./Readmission/ConfirmReadmissionModal";
import Readmission from "./Readmission/Readmission";
import "./../styles/Home.css";
import "./../styles/Form.css";
import "../App.css";
import { SAVE_SIGNATURE } from "../Utils/API";
import { ERROR_SAVE_SIGNATURE, ERROR_SELECT_PNG } from "../Utils/Errors_Utils";

function Home() {
  let user = auth.user;

  let dateFormat = formatDate(user.creationDate);
  const [errorMessage, setErrorMessage] = useState("");
  const [hasASignature, setHasASignature] = useState(
    user.signature !== undefined && user.signature !== null
  );
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  function formatDate(dateString) {
    let date = new Date(dateString);
    let dateFormatted = date.toISOString().split("T")[0];
    return dateFormatted;
  }

  function checkUser() {
    if (auth.isStudent()) {
      if (!user.sessions.includes(session)) {
        return (
          <>
            <Readmission showModal={showModal} session={session} />
            <CVList />
          </>
        );
      } else {
        return (
          <>
            <CVList />
          </>
        );
      }
    } else if (auth.isSupervisor()) {
      if (!user.sessions.includes(session)) {
        return <Readmission showModal={showModal} session={session} />;
      }
    }
  }

  function showModal() {
    handleShow();
  }

  function checkForModal() {
    if (auth.isSupervisor() || auth.isStudent()) {
      return (
        <>
          <ConfirmSubscribeModal
            show={show}
            handleClose={handleClose}
            session={session}
          />
        </>
      );
    }
  }

  function saveSignature(signature) {
    if (signature.type === "image/png") {
      let formData = new FormData();
      formData.append("signature", signature);
      axios
        .post(SAVE_SIGNATURE + user.username, formData) 
        .then((response) => {
          user.signature = response.data;
          auth.user = user;
          setHasASignature(true);
        })
        .catch((error) => {
          setErrorMessage(ERROR_SAVE_SIGNATURE);
        });
    } else {
      setErrorMessage(ERROR_SELECT_PNG);
    }
  }

  function checkSignature() {
    if (!auth.isSupervisor()) {
      if (hasASignature) {
        return (
          <Container className="cont_btn_file">
            <p className="btn_submit" disabled>
              Signature déposée
            </p>
          </Container>
        );
      } else {
        return (
          <>
            <Form className="mb-5 mt-2">
              <Form.Group controlId="document" className="cont_file_form">
                <Form.Label className="labelFields">Signature</Form.Label>
                <Form.Control
                  type="file"
                  onChange={(e) => {
                    saveSignature(e.target.files[0]);
                  }}
                  className="input_file_form"
                  accept="image/png"
                />
              </Form.Group>
            </Form>
            <p
              style={{
                color: "red",
              }}
            >
              {errorMessage}
            </p>
          </>
        );
      }
    }
  }

  return (
    <>
      <Container className="cont_home mb-5">
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
            </Row>
          </Col>
          <Col xs={12} md={8}>
            {checkUser()}
          </Col>
        </Row>
      </Container>
      {checkForModal()}
    </>
  );
}

export default Home;
