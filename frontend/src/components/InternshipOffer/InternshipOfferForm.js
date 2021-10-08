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
  const [hasApplied, setHasApplied] = useState(false);

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
          workDays: [],
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

  const [document, setDocument] = useState(undefined);

  let isLoading = false;

  function onCreatePost(e) {
    e.preventDefault();
    if (!isLoading) {
      if (fields.workDays.length > 0) {
        if (new Date(fields.startDate) < new Date(fields.endDate)) {
          if (document === undefined || document.type === "application/pdf") {
            isLoading = true;
            if (auth.isInternshipManager()) {
              axios
                .get(`http://localhost:9090/get/monitor/${monitor.name}/`)
                .then((response) => {
                  fields.monitor = response.data;
                  saveInternshipOffer();
                })
                .catch((error) => {
                  setErrorMessage("Erreur! Le moniteur est inexistant");
                  isLoading = false;
                });
            } else {
              fields.monitor = user;
              saveInternshipOffer();
            }
          } else {
            setErrorMessage("Erreur! Veuillez soumettre un document .pdf");
          }
        } else {
          setErrorMessage("Erreur! Durée de stage invalide !");
        }
      } else {
        setErrorMessage("Erreur! Choisissez au moins une journée de travail !");
      }
    }
  }

  function saveInternshipOffer() {
    let formData = new FormData();
    formData.append("internshipOffer", JSON.stringify(fields));
    formData.append("document", document);
    axios
      .post("http://localhost:9090/save/internshipOffer", formData)
      .then((response) => {
        setTimeout(() => {
          history.push({
            pathname: `/home/${user.username}`,
          });
        }, 3000);
        setErrorMessage(
          "L'offre de stage a été sauvegardé, vous allez être redirigé"
        );
      })
      .catch((error) => {
        isLoading = false;
        setErrorMessage("Erreur! L'offre de stage est invalide");
      });
  }

  function checkIfGS() {
    if (auth.isInternshipManager() && internshipOffer === undefined) {
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

  function validateInternshipOffer() {
    axios
      .post(
        `http://localhost:9090/save/internshipOffer/validate/${internshipOffer.id}`
      )
      .then((response) => {
        setErrorMessage(
          "L'offre de stage a été validée, vous allez être redirigé"
        );
        setTimeout(() => {
          history.push({
            pathname: `/listInternshipOffer`,
          });
        }, 2000);
      })
      .catch((error) => {
        setErrorMessage("Erreur lors de la validation");
      });
  }

  function applyInternshipOffer() {
    axios
      .post(
        `http://localhost:9090/apply/internshipOffer/${user.username}`,
        fields
      )
      .then((response) => {
        auth.user = response.data;
        setHasApplied(true);

        setTimeout(() => {
          history.push({
            pathname: `/listInternshipOffer`,
          });
        }, 3000);
        setErrorMessage(
          "Votre demande a été acceptée, vous allez être redirigé"
        );
      })
      .catch((error) => {
        setErrorMessage("Erreur lors de l'application de stage");
      });
  }

  function checkIfValidated() {
    if (auth.isInternshipManager() && internshipOffer !== undefined) {
      return (
        <>
          <p
            style={{
              color: errorMessage.startsWith("Erreur") ? "red" : "green",
            }}
          >
            {errorMessage}
          </p>
          <button
            className="btn_submit"
            onClick={() => validateInternshipOffer()}
          >
            Valider
          </button>
        </>
      );
    }
  }

  function checkIfDocumentIsDownload() {
    if (internshipOffer !== undefined && internshipOffer.pdfdocument !== null) {
      return (
        <Container className="cont_btn_file">
          <a
            className="btn_file"
            href={
              "http://localhost:9090/get/internshipOffer/document/" +
              internshipOffer.id
            }
            download
          >
            Télécharger le document
          </a>
        </Container>
      );
    }
  }

  function checkIfStudent() {
    if (auth.isStudent() && internshipOffer !== undefined) {
      let internshipOffferList = user.internshipOffers;
      let hasAlredayApplied = false;
      internshipOffferList.forEach((_internshipOffer) => {
        if (_internshipOffer.id === internshipOffer.id) {
          hasAlredayApplied = true;
        }
      });
      if (!hasApplied) {
        if (!hasAlredayApplied) {
          return (
            <>
              <p
                style={{
                  color: errorMessage.startsWith("Erreur") ? "red" : "blue",
                }}
              >
                {errorMessage}
              </p>
              <button
                className="btn_submit"
                onClick={() => applyInternshipOffer()}
              >
                Appliquer
              </button>
            </>
          );
        } else {
          return (
            <p style={{ color: "red" }}>Vous avez déja appliqué à ce stage</p>
          );
        }
      } else {
        return (
          <>
            <p
              style={{
                color: errorMessage.startsWith("Erreur") ? "red" : "blue",
              }}
            >
              {errorMessage}
            </p>
          </>
        );
      }
    }
  }

  function setPageTitle() {
    if (internshipOffer === undefined) {
      return <h2>Ajout d'offre de stages</h2>;
    } else {
      return <h2>Information sur l'offre de stage</h2>;
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
            <Container className="cont_title_form">{setPageTitle()}</Container>
          </Row>
          <Row>
            <fieldset disabled={internshipOffer ? "disabled" : ""}>
              <Form
                onSubmit={(e) => onCreatePost(e)}
                encType="multipart/form-data"
              >
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
                      placeholder="Entrer le salaire en dollars($) par heure"
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
                      className="input_form"
                      required
                    />
                  </Form.Group>
                  <Form.Group controlId="city">
                    <Form.Control
                      value={fields.city}
                      onChange={handleFieldChange}
                      type="text"
                      placeholder="Entrer la ville de l'entreprise"
                      className="input_form"
                      required
                    />
                  </Form.Group>
                  <Form.Group controlId="postalCode">
                    <Form.Control
                      value={fields.postalCode}
                      onChange={handleFieldChange}
                      type="text"
                      placeholder="Entrer le code postal de l'entreprise"
                      className="input_form"
                      required
                      minLength="6"
                      maxLength="6"
                    />
                  </Form.Group>
                  <Form.Group controlId="workShift">
                    <Form.Label className="labelFields">
                      Type d'horaire du stage
                    </Form.Label>
                    <Form.Select
                      aria-label="Default select example"
                      defaultValue={fields.workShift}
                      onChange={handleFieldChange}
                      className="select_form d_block "
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
                      className="select_form d_block"
                      required
                    >
                      <option value="ARCHITECTURE">Architecture</option>
                      <option value="COMPUTER_SCIENCE">Informatique</option>
                      <option value="NURSING">Infirmier</option>
                    </Form.Select>
                  </Form.Group>
                  <Form.Group
                    controlId="document"
                    className="cont_file_form"
                    style={{ display: internshipOffer ? "none" : "" }}
                  >
                    <Form.Control
                      type="file"
                      onChange={(e) => {
                        setDocument(e.target.files[0]);
                      }}
                      className="input_file_form"
                      accept=".pdf"
                    />
                  </Form.Group>
                  <Container
                    className="cont_btn"
                    style={{ display: internshipOffer ? "none" : "" }}
                  >
                    <p
                      style={{
                        color: errorMessage.startsWith("Erreur")
                          ? "red"
                          : "green",
                      }}
                    >
                      {errorMessage}
                    </p>
                    <button className="btn_submit">Confirmer</button>
                  </Container>
                </Container>
              </Form>
            </fieldset>
            {checkIfDocumentIsDownload()}
            <Container className="cont_btn">
              {checkIfValidated()}
              {checkIfStudent()}
            </Container>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};
export default InternshipOfferForm;
