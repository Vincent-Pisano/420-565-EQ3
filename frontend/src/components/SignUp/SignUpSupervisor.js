import axios from "axios";
import React from "react";
import auth from "../../services/Auth";
import { useState } from "react";
import { useFormFields } from "../../lib/hooksLib";
import { useHistory } from "react-router-dom";
import { Container, Form } from "react-bootstrap";
import { DEPARTMENTS } from "../../Utils/DEPARTMENTS";
import {
  ERROR_INVALID_SUPERVISOR_USERNAME,
  ERROR_USERNAME_EMAIL_ALREADY_EXISTS,
} from "../../Utils/Errors_Utils";
import { SIGN_UP_SUPERVISOR } from "../../Utils/API";

const SignUpSupervisor = () => {
  let history = useHistory();

  const [errorMessage, setErrorMessage] = useState("");

  const [fields, handleFieldChange] = useFormFields({
    department: DEPARTMENTS[0] !== undefined ? DEPARTMENTS[0].key : undefined,
    username: "",
    password: "",
    email: "",
    firstName: "",
    lastName: "",
  });

  function onCreatePost(e) {
    e.preventDefault();

    if (!fields.username.startsWith("S")) {
      setErrorMessage(ERROR_INVALID_SUPERVISOR_USERNAME);
      return;
    }

    axios
      .post(SIGN_UP_SUPERVISOR, fields)
      .then((response) => {
        if (response.data === "") {
        } else {
          auth.login(() => {
            history.push({
              pathname: `/home/${response.data.username}`,
              state: response.data,
            });
          }, response.data);
        }
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
            Veuillez commencez votre nom d'utilisateur par "S"
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
            {DEPARTMENTS.map((department) => {
              return (
                <option key={department.key} value={department.key}>
                  {department.name}
                </option>
              );
            })}
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
export default SignUpSupervisor;
