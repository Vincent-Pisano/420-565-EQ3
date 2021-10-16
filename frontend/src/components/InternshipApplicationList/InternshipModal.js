import { React, useState } from "react";
import { Button, Container, Modal, Row, Col, Form } from "react-bootstrap";
import { useHistory } from "react-router";
import auth from "../../services/Auth";
import axios from "axios";
import "../../styles/Form.css";

const InternshipModal = ({
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

  function onConfirmModal(e) {
    e.preventDefault();
    CreateInternship();
  }

  function CreateInternship() {
    currentInternshipApplication.student.cvlist = [];
    currentInternshipApplication.internshipOffer.pdfdocument = undefined;
    axios
      .post(
        `http://localhost:9090//save/internship`,
        currentInternshipApplication
      )
      .then((response) => {
        setInternshipApplications(
          internshipApplications.filter((internshipApplication) => {
            return internshipApplication.id !== currentInternshipApplication.id;
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
  }

  function showEngagementsTextArea() {
    if (!showEngagements) {
      return (
        <>
          <hr className="modal_separator mx-auto" />
          <Form.Group controlId="status">
            <Form.Label className="labelFields">
              Engagement du collège
            </Form.Label>
            <textarea className="my-3 select_form" />
          </Form.Group>
          <Form.Group controlId="status">
            <Form.Label className="labelFields">
              Engagement de l'entreprise
            </Form.Label>
            <textarea className="my-3 select_form" />
          </Form.Group>
          <Form.Group controlId="status">
            <Form.Label className="labelFields">
              Engagement de l'étudiant
            </Form.Label>
            <textarea className="my-3 select_form" />
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
                    <option value="VALIDATED" active>
                      Validée
                    </option>
                  </Form.Select>
                </Form.Group>
                <Container fluid>
                  <Form.Group className="mb-3" controlId="Sunday">
                    <Row>
                      <Col xs={9}>
                        <Form.Label className="labelFields mt-3">
                          <span>Engagements pré-enregistrés</span>
                        </Form.Label>
                      </Col>
                      <Col xs={3}>
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

export default InternshipModal;
