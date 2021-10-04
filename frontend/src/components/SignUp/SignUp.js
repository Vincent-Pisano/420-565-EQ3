import { Container, Row, Col } from "react-bootstrap";
import { useState } from "react";
import SignUpStudent from "./SignUpStudent";
import SignUpMonitor from "./SignUpMonitor";
import SignUpSupervisor from "./SignUpSupervisor";
import "../../styles/Form.css";

const SignUp = () => {
  const [currentSignUp, setCurrentSignUp] = useState("student");

  function handleClick(newChoice) {
    return function () {
      setCurrentSignUp(newChoice);
    };
  }

  function chooseSignUp() {
    if (currentSignUp === "student") {
      return <SignUpStudent />;
    }
    if (currentSignUp === "monitor") {
      return <SignUpMonitor />;
    }
    if (currentSignUp === "supervisor") {
      return <SignUpSupervisor />;
    }
    return <p>Not Implemented yet</p>;
  }

  return (
    <Container fluid className="cont_principal">
      <Row className="cont_central">
        <Col md="auto">
          <Container className="cont_title_form">
            <h2>Inscription</h2>
          </Container>
          <Row className="cont_buttons_sign_up">
            <Col xs={3} className="px-0">
              <button
                size="md"
                className={
                  currentSignUp === "student"
                    ? "btn_link btn_link_selected"
                    : "btn_link"
                }
                onClick={handleClick("student")}
              >
                Étudiant
              </button>
            </Col>
            <Col xs={4} className="px-0">
              <button
                size="md"
                className={
                  currentSignUp === "supervisor"
                    ? "btn_link btn_link_selected"
                    : "btn_link"
                }
                onClick={handleClick("supervisor")}
              >
                Superviseur
              </button>
            </Col>
            <Col xs={3} className="px-0">
              <button
                size="md"
                className={
                  currentSignUp === "monitor"
                    ? "btn_link btn_link_selected"
                    : "btn_link"
                }
                onClick={handleClick("monitor")}
              >
                Moniteur
              </button>
            </Col>
          </Row>
          <Row>{chooseSignUp()}</Row>
        </Col>
      </Row>
    </Container>
  );
};

export default SignUp;
