import axios from "axios";
import auth from "../../services/Auth";
import { React, useState, useEffect } from "react";
import { Button, Modal, Row, Col } from "react-bootstrap";
import "../../styles/Form.css";
import { useHistory } from "react-router";

const InternshipApplicationSignatureModal = ({
  show,
  handleClose,
  currentInternshipApplication,
  internshipApplications,
  setInternshipApplications,
  setErrorMessage,
}) => {
  let title =
    currentInternshipApplication.status === "VALIDATED"
      ? "Signature du stage"
      : "Informations sur l'application de stage";
  let student = currentInternshipApplication.student;
  let history = useHistory();

  let user = auth.user;

  const [errorMessageModal, setErrorMessageModal] = useState("");
  const [internship, setInternship] = useState(undefined);

  useEffect(() => {
    axios
      .get(
        `http://localhost:9090/get/internship/${currentInternshipApplication.id}`
      )
      .then((response) => {
        setInternship(response.data);
      })
      .catch((err) => {
        setInternship(undefined);
      });
  }, [currentInternshipApplication.id]);

  function onConfirmModal(e) {
    e.preventDefault();
    signInternship();
  }

  function signInternship() {
    if (user.signature !== null) {
      if (auth.isMonitor()) {
        if (internship !== undefined && !internship.signedByMonitor) {
          axios
            .post(
              `http://localhost:9090/sign/internshipContract/monitor/${internship.id}`
            )
            .then((response) => {
              setInternship(response.data);
              setTimeout(() => {
                setErrorMessageModal("");
                handleClose();
              }, 1000);
              setErrorMessageModal("Confirmation de la signature");
            })
            .catch((err) => {
              setInternship(undefined);
            });
        }
      } else if (auth.isStudent()) {
        if (internship !== undefined && internship.signedByMonitor) {
          if (!internship.signedByStudent) {
            axios
              .post(
                `http://localhost:9090/sign/internshipContract/student/${internship.id}`
              )
              .then((response) => {
                setInternship(response.data);
                setTimeout(() => {
                  setErrorMessageModal("");
                  handleClose();
                }, 1000);
                setErrorMessageModal("Confirmation de la signature");
              })
              .catch((err) => {
                setInternship(undefined);
              });
          }
        } else {
          setTimeout(() => {
            setErrorMessageModal("");
            handleClose();
          }, 1000);
          setErrorMessageModal(
            "Erreur ! En attente de la signature du Moniteur"
          );
        }
      } else if (auth.isInternshipManager()) {
        if (internship !== undefined && internship.signedByMonitor) {
          if (internship.signedByStudent) {
            if (!internship.signedByInternshipManager) {
              axios
                .post(
                  `http://localhost:9090/sign/internshipContract/internshipManager/${internship.id}`
                )
                .then((response) => {
                  setInternship(response.data);
                  setTimeout(() => {
                    setErrorMessageModal("");
                    handleClose();
                  }, 1000);
                  setInternshipApplications(
                    internshipApplications.filter((internshipApplication) => {
                      return (
                        internshipApplication.id !==
                        currentInternshipApplication.id
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
                  setErrorMessageModal("Confirmation de la signature");
                })
                .catch((err) => {
                  setInternship(undefined);
                });
            }
          } else {
            setTimeout(() => {
              setErrorMessageModal("");
              handleClose();
            }, 1000);
            setErrorMessageModal(
              "Erreur ! En attente de la signature de l'Étudiant"
            );
          }
        } else {
          setTimeout(() => {
            setErrorMessageModal("");
            handleClose();
          }, 1000);
          setErrorMessageModal(
            "Erreur ! En attente de la signature du Moniteur"
          );
        }
      }
    } else {
      setTimeout(() => {
        setErrorMessageModal("");
        handleClose();
      }, 1000);
      setErrorMessageModal("Erreur ! La signature n'a pas été déposé!");
    }
  }

  function checkIfValidated() {
    if (internship !== undefined) {
      return (
        <Col md={4}>
          <a
            className="btn btn-lg btn-warning mt-3"
            href={`http://localhost:9090/get/internship/document/${internship.id}`}
            target="_blank"
            rel="noreferrer"
          >
            Contrat
          </a>
        </Col>
      );
    } else {
      let idCVActiveValid = undefined;
      if (student !== undefined) {
        student.cvlist.forEach((cv) => {
          if (cv.isActive && cv.status === "VALID") {
            idCVActiveValid = cv.id;
          }
        });
        if (idCVActiveValid !== undefined) {
          return (
            <Col md={4}>
              <a
                className="btn btn-lg btn-warning mt-3"
                href={`http://localhost:9090/get/CV/document/${student.id}/${idCVActiveValid}`}
                target="_blank"
                rel="noreferrer"
              >
                CV
              </a>
            </Col>
          );
        } else {
          return (
            <Col md={4}>
              <Button variant="warning" size="lg" className="btn_sub" disabled>
                Pas de CV
              </Button>
            </Col>
          );
        }
      }
    }
  }

  function checkIfNeedSignature() {
    if (internship !== undefined) {
      return (
        <Col md={4}>
          <Button
            variant="success"
            size="lg"
            className="btn_sub"
            onClick={(e) => onConfirmModal(e)}
            disabled={
              auth.isMonitor()
                ? internship.signedByMonitor
                : auth.isStudent()
                ? internship.signedByStudent
                : auth.isInternshipManager()
                ? internship.signedByInternshipManager
                : false
            }
          >
            {auth.isMonitor()
              ? internship.signedByMonitor
                ? "Déjà signé"
                : "Signer"
              : auth.isStudent()
              ? internship.signedByStudent
                ? "Déjà signé"
                : "Signer"
              : auth.isInternshipManager()
              ? internship.signedByInternshipManager
                ? "Déjà signé"
                : "Signer"
              : "Confirmer"}
          </Button>
        </Col>
      );
    }
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header>
        <Modal.Title style={{ textAlign: "center" }}>{title}</Modal.Title>
      </Modal.Header>
      <Modal.Body style={{ margin: "0%" }}>
        <Row style={{ textAlign: "center" }}>
          {checkIfNeedSignature()}
          {checkIfValidated()}
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

export default InternshipApplicationSignatureModal;
