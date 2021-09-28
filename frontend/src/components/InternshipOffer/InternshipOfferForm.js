import axios from "axios";
import React from "react";
import auth from "../../services/Auth";
import { useState } from "react";
import { useFormFields } from "../../lib/hooksLib";
import { useHistory } from "react-router";
import { Container, Row, Col, Form } from "react-bootstrap";
import "../../styles/Form.css";

const InternshipOfferForm = () => {
  let user = auth.user;
  let history = useHistory();
  let internshipOffer = history.location.state;

  formatDates();

  const [errorMessage, setErrorMessage] = useState("");

  const [fields, handleFieldChange] = useFormFields(
    internshipOffer !== undefined
      ? internshipOffer
      : {
          jobName: "",
          description: "",
          startDate: "",
          endDate: "",
          weeklyWorkTime: "",
          hourlySalary: "",
          workDays: ["Monday"],
          address: "",
          city: "",
          postalCode: "",
          workShift: "DAY",
          workField: "COMPUTER_SCIENCE",
          monitor: {},
        }
  );

  const [monitor, setMonitor] = useFormFields({
    name: "",
  });

  function onCreatePost(e) {
    e.preventDefault();

    if (user.username.startsWith("G")) {
      axios
        .get(`http://localhost:9090/get/monitor/${monitor.name}/`)
        .then((response) => {
          fields.monitor = response.data;
          saveInternshipOffer();
        })
        .catch((error) => {
          console.log(error);
          setErrorMessage("Erreur! Le moniteur est inexistant");
        });
    } else {
      fields.monitor = user;
      saveInternshipOffer();
    }
  }

  function saveInternshipOffer() {
    axios
      .post("http://localhost:9090/save/internshipOffer", fields)
      .then((response) => {
        setErrorMessage("L'offre de stage a été sauvegardé");
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
        setErrorMessage("Erreur! L'offre de stage est invalide");
      });
  }

  function checkIfGS() {
    if (user.username.startsWith("G")) {
      return (
        <Form.Group controlId="name">
          <Form.Control
            value={fields.name}
            onChange={setMonitor}
            type="text"
            placeholder="Entrer le nom d'utilisateur du moniteur"
            className="input_form active_inp_form"
            required
          />
        </Form.Group>
      );
    }
  }

  function checkIfChecked(checkboxName) {
    return fields.workDays.some((workday) => checkboxName === workday);
  }

  function formatDates() {
    if (internshipOffer) {
      internshipOffer.startDate = formatDate(internshipOffer.startDate);
      internshipOffer.endDate = formatDate(internshipOffer.endDate);
    }
  }

  function formatDate(dateString) {
    let date = new Date(dateString);
    let dateFormatted = date.toISOString().split("T")[0];
    return dateFormatted;
  }

  return (
    <Container fluid className="cont_principal">
      <Row className="cont_central">
        <Col md="auto" className="cont_form">
          <Row>
            <div className="cont_title_form">
              <h2>Offre de stage</h2>
            </div>
          </Row>
          <Row>
            <fieldset disabled={internshipOffer ? "disabled" : ""}>
              <Form onSubmit={(e) => onCreatePost(e)}>
                <Container className="cont_inputs">
                  {checkIfGS()}
                  <Form.Group controlId="jobName">
                    <Form.Control
                      value={fields.jobName}
                      onChange={handleFieldChange}
                      type="text"
                      placeholder="Entrer le nom du poste de travail"
                      className="input_form active_inp_form"
                      required
                    />
                  </Form.Group>
                  <Form.Group controlId="description">
                    <Form.Control
                      value={fields.description}
                      onChange={handleFieldChange}
                      type="text"
                      placeholder="Entrer une description du poste"
                      className="input_form active_inp_form"
                      required
                    />
                  </Form.Group>
                  <Form.Group controlId="startDate">
                    <Form.Label className="labelFields">
                      Date de début du stage
                    </Form.Label>
                    <Form.Control
                      value={fields.startDate}
                      onChange={handleFieldChange}
                      type="date"
                      placeholder="Entrer la date de début du stage"
                      className="input_form active_inp_form"
                      required
                    />
                  </Form.Group>
                  <Form.Group controlId="endDate">
                    <Form.Label className="labelFields">
                      Date de fin du stage
                    </Form.Label>
                    <Form.Control
                      value={fields.endDate}
                      onChange={handleFieldChange}
                      type="date"
                      placeholder="Entrer la date de fin du stage"
                      className="input_form active_inp_form"
                      required
                    />
                  </Form.Group>
                  <Form.Group controlId="weeklyWorkTime">
                    <Form.Control
                      value={fields.weeklyWorkTime}
                      onChange={handleFieldChange}
                      type="number"
                      step="0.1"
                      min="0"
                      max="40"
                      placeholder="Entrer la quantité d'heures par semaine"
                      className="input_form active_inp_form"
                      required
                    />
                  </Form.Group>
                  <Form.Group controlId="hourlySalary">
                    <Form.Control
                      value={fields.hourlySalary}
                      onChange={handleFieldChange}
                      type="number"
                      step=".01"
                      min="0"
                      max="100"
                      placeholder="Entrer le salaire par heure"
                      className="input_form active_inp_form"
                      required
                    />
                  </Form.Group>
                  <Form.Group controlId="endDate">
                    <Form.Label className="labelFields">
                      Les jours de travail
                    </Form.Label>
                    <Form.Control
                      value={fields.endDate}
                      onChange={handleFieldChange}
                      type="date"
                      placeholder="Entrer la date de fin du stage"
                      className="input_form active_inp_form"
                      required
                    />
                  </Form.Group>
                  <Form.Group>
                    <Form.Label className="labelFields">
                      Jours de travail
                    </Form.Label>
                    <Container className="workDays checkboxes">
                      <Form.Group className="mb-3" controlId="Monday">
                        <Row>
                          <Col xs={1}>
                            <Form.Check
                              className="checkboxes_input"
                              type="checkbox"
                              onChange={handleFieldChange}
                              defaultChecked={checkIfChecked("Monday")}
                            />
                          </Col>
                          <Col xs={1}>
                            <Form.Label className="checkboxes_label">
                              <span>Lundi</span>
                            </Form.Label>
                          </Col>
                        </Row>
                      </Form.Group>
                      <Form.Group className="mb-3" controlId="Tuesday">
                        <Row>
                          <Col xs={1}>
                            <Form.Check
                              className="checkboxes_input"
                              type="checkbox"
                              onChange={handleFieldChange}
                              defaultChecked={checkIfChecked("Tuesday")}
                            />
                          </Col>
                          <Col xs={1}>
                            <Form.Label className="checkboxes_label">
                              <span>Mardi</span>
                            </Form.Label>
                          </Col>
                        </Row>
                      </Form.Group>
                      <Form.Group className="mb-3" controlId="Wednesday">
                        <Row>
                          <Col xs={1}>
                            <Form.Check
                              className="checkboxes_input"
                              type="checkbox"
                              onChange={handleFieldChange}
                              defaultChecked={checkIfChecked("Wednesday")}
                            />
                          </Col>
                          <Col xs={1}>
                            <Form.Label className="checkboxes_label">
                              <span>Mercredi</span>
                            </Form.Label>
                          </Col>
                        </Row>
                      </Form.Group>
                      <Form.Group className="mb-3" controlId="Thursday">
                        <Row>
                          <Col xs={1}>
                            <Form.Check
                              className="checkboxes_input"
                              type="checkbox"
                              onChange={handleFieldChange}
                              defaultChecked={checkIfChecked("Thursday")}
                            />
                          </Col>
                          <Col xs={1}>
                            <Form.Label className="checkboxes_label">
                              <span>Jeudi</span>
                            </Form.Label>
                          </Col>
                        </Row>
                      </Form.Group>
                      <Form.Group className="mb-3" controlId="Friday">
                        <Row>
                          <Col xs={1}>
                            <Form.Check
                              className="checkboxes_input"
                              type="checkbox"
                              onChange={handleFieldChange}
                              defaultChecked={checkIfChecked("Friday")}
                            />
                          </Col>
                          <Col xs={1}>
                            <Form.Label className="checkboxes_label">
                              <span>Vendredi</span>
                            </Form.Label>
                          </Col>
                        </Row>
                      </Form.Group>
                      <Form.Group className="mb-3" controlId="Saturday">
                        <Row>
                          <Col xs={1}>
                            <Form.Check
                              className="checkboxes_input"
                              type="checkbox"
                              onChange={handleFieldChange}
                              defaultChecked={checkIfChecked("Saturday")}
                            />
                          </Col>
                          <Col xs={1}>
                            <Form.Label className="checkboxes_label">
                              <span>Samedi</span>
                            </Form.Label>
                          </Col>
                        </Row>
                      </Form.Group>
                      <Form.Group className="mb-3" controlId="Sunday">
                        <Row>
                          <Col xs={1}>
                            <Form.Check
                              className="checkboxes_input"
                              type="checkbox"
                              onChange={handleFieldChange}
                              defaultChecked={checkIfChecked("Sunday")}
                            />
                          </Col>
                          <Col xs={1}>
                            <Form.Label className="checkboxes_label">
                              <span>Dimanche</span>
                            </Form.Label>
                          </Col>
                        </Row>
                      </Form.Group>
                    </Container>
                  </Form.Group>
                  <Form.Group controlId="address">
                    <Form.Control
                      value={fields.address}
                      onChange={handleFieldChange}
                      type="text"
                      placeholder="Entrer l'adresse de l'entreprise"
                      className="input_form active_inp_form"
                      required
                    />
                  </Form.Group>
                  <Form.Group controlId="city">
                    <Form.Control
                      value={fields.city}
                      onChange={handleFieldChange}
                      type="text"
                      placeholder="Entrer la ville de l'entreprise"
                      className="input_form active_inp_form"
                      required
                    />
                  </Form.Group>
                  <Form.Group controlId="postalCode">
                    <Form.Control
                      value={fields.postalCode}
                      onChange={handleFieldChange}
                      type="text"
                      placeholder="Entrer le code postal de l'entreprise"
                      className="input_form active_inp_form"
                      required
                      minLength="6"
                      maxLength="6"
                    />
                  </Form.Group>
                  <Form.Group controlId="workShift">
                    <Form.Label className="labelFields">
                      Type d'offre de stage
                    </Form.Label>
                    <Form.Select
                      aria-label="Default select example"
                      defaultValue={fields.workShift}
                      onChange={handleFieldChange}
                      className="select_form d_block active_select "
                      required
                    >
                      <option value="DAY">Jour</option>
                      <option value="NIGHT">Nuit</option>
                      <option value="FLEXIBLE">Flexible</option>
                    </Form.Select>
                  </Form.Group>
                  <Form.Group controlId="workField">
                    <Form.Label className="labelFields">
                      Type d'offre de stage
                    </Form.Label>
                    <Form.Select
                      aria-label="Default select example"
                      defaultValue={fields.workField}
                      onChange={handleFieldChange}
                      className="select_form d_block active_select "
                      required
                    >
                      <option value="ARCHITECTURE">Architecture</option>
                      <option value="COMPUTER_SCIENCE">Informatique</option>
                      <option value="NURSING">Infirmier</option>
                    </Form.Select>
                  </Form.Group>
                  <div className="cont_btn">
                    <p>{errorMessage}</p>
                    <button className="btn_submit">Confirmer</button>
                  </div>
                </Container>
              </Form>
            </fieldset>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};
export default InternshipOfferForm;
