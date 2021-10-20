import { React, useState, useEffect } from "react";
import { Button, Container, Modal, Row, Col, Form } from "react-bootstrap";
import { useHistory } from "react-router";
import auth from "../../services/Auth";
import axios from "axios";
import "../../styles/Form.css";

const InternshipApplicationInternshipManagerModal = ({
  show,
  handleClose,
  currentInternshipApplication,
  showIntershipOffer,
  internshipApplications,
  setInternshipApplications,
  setErrorMessage,
}) => {
  let currentInternshipOffer = currentInternshipApplication.internshipOffer;
  let history = useHistory();

  const [errorMessageModal, setErrorMessageModal] = useState("");
  const [showEngagements, setShowEngagements] = useState(true);
  const [engagements, setEngagements] = useState();

  useEffect(() => {
    axios
      .get(`http://localhost:9090/get/default/engagements`)
      .then((response) => {
        setEngagements(response.data);
      })
      .catch((err) => {
        setErrorMessageModal("Erreur lors de la requête des engagements");
      });
  }, []);

  function onConfirmModal(e) {
    e.preventDefault();
    CreateInternship();
  }

  function changeEngagements(event) {
    setEngagements({
      ...engagements,
      [event.currentTarget.id]: event.target.value,
    });
  }

  function CreateInternship() {
    if (currentInternshipApplication.status !== "ACCEPTED") {
      currentInternshipApplication.student.cvlist = [];
      currentInternshipApplication.student.signature = undefined;
      currentInternshipApplication.internshipOffer.pdfdocument = undefined;
      currentInternshipApplication.internshipOffer.monitor.signature =
        undefined;
      let internship = {
        internshipApplication: currentInternshipApplication,
        engagements: engagements,
      };
      axios
        .post(`http://localhost:9090/save/internship`, internship)
        .then((response) => {
          setInternshipApplications(
            internshipApplications.filter((internshipApplication) => {
              return (
                internshipApplication.id !== currentInternshipApplication.id
              );
            })
          );
          if (internshipApplications.length === 1) {
            setTimeout(() => {
              handleClose();
              history.push({
                pathname: `/home/${auth.user.username}`,
              });
            }, 3000);
            setErrorMessage(
              "Plus aucun étudiant à assigner, vous allez être redirigé"
            );
          }
          setTimeout(() => {
            setErrorMessageModal("");
            handleClose();
          }, 1000);
          setErrorMessageModal("Confirmation des changements");
        })
        .catch((err) => {
          setErrorMessageModal("Erreur lors de la mise à jour");
        });
    } else {
      setTimeout(() => {
        setErrorMessageModal("");
        handleClose();
      }, 1000);
      setErrorMessageModal("Aucune modification effectuée");
    }
  }

  function showEngagementsTextArea() {
    if (!showEngagements) {
      return (
        <>
          <hr className="modal_separator mx-auto" />
          <Form.Group>
            <Form.Label className="labelFields">
              Engagement du collège
            </Form.Label>
            <textarea
              id="College"
              rows="4"
              cols="50"
              className="my-3 textarea_form"
              defaultValue={engagements ? engagements.College : ""}
              onChange={(event) => changeEngagements(event)}
            />
          </Form.Group>
          <Form.Group>
            <Form.Label className="labelFields">
              Engagement de l'entreprise
            </Form.Label>
            <textarea
              id="Enterprise"
              rows="4"
              cols="50"
              className="my-3 textarea_form"
              defaultValue={engagements ? engagements.Enterprise : ""}
              onChange={(event) => changeEngagements(event)}
            />
          </Form.Group>
          <Form.Group>
            <Form.Label className="labelFields">
              Engagement de l'étudiant
            </Form.Label>
            <textarea
              id="Student"
              rows="4"
              cols="50"
              className="my-3 textarea_form"
              defaultValue={engagements ? engagements.Student : ""}
              onChange={(event) => changeEngagements(event)}
            />
          </Form.Group>
        </>
      );
    }
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>
          Informations de l'application de stage
        </Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
        <Row style={{ textAlign: "center" }}>
          <Col md={12}>
            <Form>
              <Container className="cont_inputs">
                <Form.Group controlId="status">
                  <Form.Label className="labelFields">
                    Status de l'application
                  </Form.Label>
                  <Form.Select
                    aria-label="Default select example"
                    defaultValue={currentInternshipApplication.status}
                    onChange={(event) => {
                      currentInternshipApplication.status = event.target.value;
                    }}
                    className="select_form d_block"
                    required
                  >
                    <option disabled value="ACCEPTED">
                      Attente de validation
                    </option>
                    <option value="VALIDATED">Validée</option>
                  </Form.Select>
                </Form.Group>
                <Container fluid>
                  <Form.Group className="mb-3 checkboxes">
                    <Row>
                      <Col sm={10}>
                        <Form.Label className="mt-3 px-0">
                          <span>Engagements par défaut</span>
                        </Form.Label>
                      </Col>
                      <Col sm={2}>
                        <Form.Check
                          className="checkboxes_input mt-3"
                          type="checkbox"
                          onChange={() => setShowEngagements(!showEngagements)}
                          defaultChecked={showEngagements}
                        />
                      </Col>
                    </Row>
                  </Form.Group>
                </Container>
                {showEngagementsTextArea()}
              </Container>
            </Form>
          </Col>
          <hr className="modal_separator mx-auto" />
        </Row>
        <Row style={{ textAlign: "center" }}>
          <Col md={4}>
            <Button
              variant="success"
              size="lg"
              className="btn_sub"
              onClick={(e) => onConfirmModal(e)}
            >
              Confirmer
            </Button>
          </Col>
          <Col md={4}>
            <Button
              variant="warning"
              size="lg"
              className="btn_sub"
              onClick={() => showIntershipOffer(currentInternshipOffer)}
            >
              Offre de Stage
            </Button>
          </Col>
          <Col md={4}>
            <Button
              variant="danger"
              size="lg"
              className="btn_sub"
              onClick={handleClose}
            >
              Retour
            </Button>
          </Col>
        </Row>
      </Modal.Body>
      <Modal.Footer>
        <Row>
          <Col>
            <p
              className="error_p"
              style={{
                color: errorMessageModal.startsWith("Erreur") ? "red" : "green",
              }}
            >
              {errorMessageModal}
            </p>
          </Col>
        </Row>
      </Modal.Footer>
    </Modal>
  );
};

export default InternshipApplicationInternshipManagerModal;
