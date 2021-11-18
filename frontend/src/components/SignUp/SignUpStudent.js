import axios from "axios";
import React from "react";
import auth from "../../services/Auth";
import { useState } from "react";
import { useFormFields } from "../../lib/hooksLib";
import { useHistory } from "react-router-dom";
import { Container, Form } from "react-bootstrap";
import { ARCHITECTURE_DEPT, COMPUTER_SCIENCE_DEPT, NURSING_DEPT } from "../../Utils/DEPARTMENTS";
import { ERROR_INVALID_STUDENT_USERNAME, ERROR_USERNAME_EMAIL_ALREADY_EXISTS } from "../../Utils/Errors_Utils";
import { SIGN_UP_STUDENT } from "../../Utils/API";

const SignUpStudent = () => {
  let history = useHistory();

  const [errorMessage, setErrorMessage] = useState("");

  const [fields, handleFieldChange] = useFormFields({
    department: COMPUTER_SCIENCE_DEPT,
    username: "",
    password: "",
    email: "",
    firstName: "",
    lastName: "",
  });

  function onCreatePost(e) {
    e.preventDefault();

    if (!fields.username.startsWith("E")) {
      setErrorMessage(ERROR_INVALID_STUDENT_USERNAME);
      return;
    }

    axios
      .post(SIGN_UP_STUDENT , fields)
      .then((response) => {
        auth.login(() => {
          history.push({
            pathname: `/home/${response.data.username}`,
            state: response.data,
          });
        }, response.data);
      })
      .catch((error) => {
        setErrorMessage(ERROR_USERNAME_EMAIL_ALREADY_EXISTS);
      });
  }

  return (
    <Form onSubmit={(e) => onCreatePost(e)}>
      <Container className="cont_inputs">
        <Form.Group controlId="username">
          <Form.Label className="discret mb-0">
            Veuillez commencez votre nom d'utilisateur par "E"
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
        <Form.Group controlId="department">
          <Form.Control
            as="select"
            defaultValue={fields.department}
            onChange={handleFieldChange}
            className="select_form active_select "
            required
          >
            <option value={COMPUTER_SCIENCE_DEPT}>Informatique</option>
            <option value={ARCHITECTURE_DEPT}>Architecture</option>
            <option value={NURSING_DEPT}>Infirmier</option>
          </Form.Control>
        </Form.Group>
        <Container className="cont_btn">
          <p>{errorMessage}</p>
          <button className="btn_submit">Confirmer</button>
        </Container>
      </Container>
    </Form>
  );
};
export default SignUpStudent;
