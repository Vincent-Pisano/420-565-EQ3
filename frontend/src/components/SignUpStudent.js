import axios from "axios";
import React from "react";
import { useState } from "react";
import { useFormFields } from "../lib/hooksLib";
import "../App.css";
import { useHistory } from "react-router-dom";
import auth from "../services/Auth";

const SignUpStudent = () => {
  let history = useHistory();

  const [errorMessage, setErrorMessage] = useState('')

  const [fields, handleFieldChange] = useFormFields({
    department: "COMPUTER_SCIENCE",
    username: "",
    password: "",
    email: "",
    firstName: "",
    lastName: "",
  });

  function onCreatePost(e) {
    e.preventDefault();
       
    if (!fields.username.startsWith('E')) {
      setErrorMessage("Le nom d'utilisateur doit commencer par 'E'.")
      return;
    }

    console.log(fields)

    axios
      .post("http://localhost:9090/signUp/student", fields)
      .then((response) => {
        console.log(response.data);
        auth.login(() => {
          history.push({
              pathname: `/home/${response.data.username}`,
              state: response.data 
          });
        }, response.data);
      }
    ).catch((error) => {
      setErrorMessage("Le nom d'utilisateur ou le courriel existe déjà.")
    });
  }

  return (
    <div>
        <form onSubmit={onCreatePost}>
        <div className="cont_tabs_login">
            <h2 className="pt-3">Inscription Étudiant</h2>
        </div>
        <div className="cont_text_inputs">
          <label>Veuillez commencer votre nom d'utilisateur par "E"</label>
          <input
          id="username"
          type="text"
          value={fields.username}
          onChange={handleFieldChange}
          placeholder="Entrer votre nom d'utilisateur"
          className="input_form_sign d_block active_inp_sign_up"
          required/>
          
          <input
          id="password"
          type="password"
          value={fields.password}
          onChange={handleFieldChange}
          placeholder="Entrer votre mot de passe"
          className="input_form_sign d_block active_inp_sign_up"
          required/>
          <input
          id="email"
          type="email"
          value={fields.email}
          onChange={handleFieldChange}
          placeholder="Entrer votre courriel"
          className="input_form_sign d_block active_inp_sign_up"
          required/>
          <input
          id="firstName"
          type="text"
          value={fields.firstName}
          onChange={handleFieldChange}
          placeholder="Entrer votre prénom"
          className="input_form_sign d_block active_inp_sign_up"
          required/>
          <input
          id="lastName"
          type="text"
          value={fields.lastName}
          onChange={handleFieldChange}
          placeholder="Entrer votre nom de famille"
          className="input_form_sign d_block active_inp_sign_up"
          required/>
          <select id="department"
            defaultValue={fields.department}
            onChange={handleFieldChange}
            className="select_form_sign d_block active_select "
            required>
            <option value="COMPUTER_SCIENCE">Informatique</option>
            <option value="ARCHITECTURE">Architecture</option>
            <option value="NURSING">Infirmier</option>
          </select>
        </div>
        <div className="cont_btn">
          <p>{errorMessage}</p>
            <button className="btn_sign">Confirmer</button>
        </div>
        </form>
    </div>
  );
};
export default SignUpStudent;
