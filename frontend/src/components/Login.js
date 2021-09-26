import React from "react";
import { useFormFields } from "../lib/hooksLib";
import { useState } from "react";
import "../App.css";
import axios from "axios";
import { useHistory } from "react-router";
import auth from "../services/Auth";

const Login = () => {
    let history = useHistory()

    let type = ""

    const [errorMessage, setErrorMessage] = useState('')

    const [fields, handleFieldChange] = useFormFields({
        username: "",
        password: "",
    })

    function onCreatePost(e) {
        e.preventDefault();

        if(!(fields.username.startsWith('E') || fields.username.startsWith('S') || fields.username.startsWith('M') || fields.username.startsWith('G'))) {
            setErrorMessage("Les noms d'utilisateurs commencent par 'E', 'S', 'M' ou 'G'")
            return;
        }

        switch(fields.username.charAt(0)) {
            case "E":
              type = "student"
              break;
            case "S":
              type = "supervisor"
              break;
            case "M":
              type = "monitor"
              break;
            case "G":
              type = "internshipManager"
              break;
            default :
              type = "error"
        }

        axios
            .get(`http://localhost:9090/login/${type}/${fields.username}/${fields.password}`)
            .then((response) => {
                auth.login(() => {
                    history.push({
                        pathname: `/home/${response.data.username}`,
                        state: response.data 
                    });
                }, response.data);
            }
        ).catch((error) => {
            console.log(error);
            setErrorMessage("Le nom d'utilisateur ou le mot de passe est incorrect.")
        });
    }


    return (
        <div className="cont_principal">
            <div className="cont_centrar">
                <form onSubmit={onCreatePost}>
                <div>
                    <h2 className="pt-3">Connection</h2>
                </div>
                <div>
                    <input id="username"
                    type="text"
                    value={fields.username}
                    onChange={handleFieldChange}
                    placeholder="Entrer votre nom d'utilisateur"
                    className="input_form_sign d_block active_inp_login"
                    required />
                    <input id="password"
                    type="password"
                    value={fields.password}
                    onChange={handleFieldChange}
                    placeholder="Entrer votre mot de passe"
                    className="input_form_sign d_block active_inp_login"
                    required />
                </div>
                <div className="cont_btn">
                    <p>{errorMessage}</p>
                        <button className="btn_sign">Confirmer</button>
                </div>
                </form>
            </div>
        </div>
    );
};
export default Login;