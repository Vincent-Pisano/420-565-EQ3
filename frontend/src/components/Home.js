import React, { useState } from "react";
import auth from "../services/Auth";
import { session } from "../Utils/Store";
import { Container, Row, Col, Card, Button } from "react-bootstrap";
import pfp from "./../assets/img/pfp.png";
import CVList from "../components/CV/CVList";
import ConfirmSubscribeModal from "./Readmission/ConfirmReadmissionModal";
import Readmission from "./Readmission/Readmission";
import "./../styles/Home.css";
import "./../styles/Form.css";
import "../App.css";
import IMGViewer from "./Viewer/IMGViewer";
import ModalDepositSignature from "./Viewer/Modal/ModalDepositSignature";

function Home() {
  let user = auth.user;

  let dateFormat = formatDate(user.creationDate);
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

  function checkSignature() {
    if (!auth.isSupervisor()) {
      if (hasASignature) {
        return (
          <IMGViewer
            username={user.username}
            setHasASignature={setHasASignature}
          />
        );
      } else {
        return (
          <>
            <Container className="cont_btn_file">
              <Row>
                <Col md={12}>
                  <Button className="btn_link mb-3" onClick={handleShow}>
                    Déposer la signature
                  </Button>
                </Col>
              </Row>
            </Container>
            <ModalDepositSignature
              show={show}
              handleClose={handleClose}
              username={user.username}
              setHasASignature={setHasASignature}
            />
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
