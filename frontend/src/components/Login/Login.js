import React from "react";
import { useFormFields } from "../../lib/hooksLib";
import { useState } from "react";
import axios from "axios";
import { useHistory } from "react-router";
import auth from "../../services/Auth";
import { Container, Row, Col, Form } from 'react-bootstrap';

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
        <Container fluid className="d-flex justify-content-md-center 
        align-items-center text-center min-vh-100">
            <Row className="cont_central">
                <Col md="auto">
                    <Row>
                        <div className="cont_title_form">
                            <h2>Inscription</h2>
                        </div>
                    </Row>
                    <Row>
                        <Form onSubmit={e => onCreatePost(e)}>
                            <div className="cont_inputs">
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
                                    required/>
                                </Form.Group>
                                <Form.Group controlId="password">
                                    <Form.Control
                                    value={fields.password}
                                    onChange={handleFieldChange}
                                    type="password"
                                    placeholder="Entrer votre mot de passe"
                                    className="input_form"
                                    required/>
                                </Form.Group>
                            </div>
                            <div className="cont_btn">
                                <p>{errorMessage}</p>
                                    <button className="btn_submit">Confirmer</button>
                            </div>
                        </Form>
                    </Row>
                </Col>
            </Row>
        </Container>
    );
};
export default Login;