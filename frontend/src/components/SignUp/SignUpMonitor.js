import axios from "axios";
import React from "react";
import auth from "../../services/Auth";
import { useState } from "react";
import { useFormFields } from "../../lib/hooksLib";
import { useHistory } from "react-router-dom";
import { Container, Form } from "react-bootstrap";

const SignUpMonitor = () => {
  let history = useHistory();

  const [errorMessage, setErrorMessage] = useState("");

  const [fields, handleFieldChange] = useFormFields({
    enterpriseName: "",
    jobTitle: "",
    username: "",
    password: "",
    email: "",
    firstName: "",
    lastName: "",
  });

  function onCreatePost(e) {
    e.preventDefault();

    if (!fields.username.startsWith("M")) {
      setErrorMessage("Le nom de moniteur doit commencer par 'M'.");
      return;
    }

    axios
      .post("http://localhost:9090/signUp/monitor", fields)
      .then((response) => {
        auth.login(() => {
          history.push({
            pathname: `/home/${response.data.username}`,
            state: response.data,
          });
        }, response.data);
      })
      .catch((error) => {
        setErrorMessage("Le nom d'utilisateur ou le courriel existe déjà.");
      });
  }

  return (
    <Form onSubmit={(e) => onCreatePost(e)}>
      <Container className="cont_inputs">
        <Form.Group controlId="username">
          <Form.Label className="discret mb-0">
            Veuillez commencez votre nom d'utilisateur par "M"
          </Form.Label>
          <Form.Control
            value={fields.username}
            onChange={handleFieldChange}
            type="text"
            placeholder="Entrer votre nom d'utilisateur"
            className="input_form"
            required
          />
        </Form.Group>
        <Form.Group controlId="password">
          <Form.Control
            value={fields.password}
            onChange={handleFieldChange}
            type="password"
            placeholder="Entrer votre mot de passe"
            className="input_form"
            required
          />
        </Form.Group>
        <Form.Group controlId="email">
          <Form.Control
            value={fields.email}
            onChange={handleFieldChange}
            type="email"
            placeholder="Entrer votre courriel"
            className="input_form"
            required
          />
        </Form.Group>
        <Form.Group controlId="firstName">
          <Form.Control
            value={fields.firstName}
            onChange={handleFieldChange}
            type="text"
            placeholder="Entrer votre prénom"
            className="input_form"
            required
          />
        </Form.Group>
        <Form.Group controlId="lastName">
          <Form.Control
            value={fields.lastName}
            onChange={handleFieldChange}
            type="text"
            placeholder="Entrer votre nom de famille"
            className="input_form"
            required
          />
        </Form.Group>
        <Form.Group controlId="jobTitle">
          <Form.Control
            value={fields.jobTitle}
            onChange={handleFieldChange}
            type="text"
            placeholder="Entrer votre nom de poste"
            className="input_form"
            required
          />
        </Form.Group>
        <Form.Group controlId="enterpriseName">
          <Form.Control
            value={fields.enterpriseName}
            onChange={handleFieldChange}
            type="text"
            placeholder="Entrer votre nom d'entreprise"
            className="input_form"
            required
          />
        </Form.Group>
        <Container className="cont_btn">
          <p>{errorMessage}</p>
          <button className="btn_submit">Confirmer</button>
        </Container>
      </Container>
    </Form>
  );
};
export default SignUpMonitor;
