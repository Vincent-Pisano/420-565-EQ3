import { Button, Modal, Form, Row, Col } from "react-bootstrap";
import { useState } from "react";
import "./../../styles/CV.css"
import auth from "../../services/Auth";
import axios from "axios";

const CVButtonDeposit = () => {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [document, setDocument] = useState(undefined);
    const [errorMessage, setErrorMessage] = useState('')

    let user = auth.user

    function reset(){
        handleShow();
        setErrorMessage("");
        setDocument(undefined)
    }

    function onCreatePost(e) {
        e.preventDefault();
        if (document !== undefined && document.type === "application/pdf" && user.cvlist.length < 10) {
            let formData = new FormData();
            formData.append("document", document);
            axios
            .post(`http://localhost:9090/save/CV/${user.idUser}/`, formData)
            .then((response) => {
                user = response.data;
                auth.user = user;
                setErrorMessage("Le fichier a été déposé");
                setTimeout(() => {
                    handleClose();
                }, 1000);
            })
            .catch((error) => {
                setErrorMessage("Erreur d'envoi de fichier");
            });
        }
        else{
            if(!(user.cvlist.length < 10)){
                setErrorMessage("Erreur! Taille maximale de fichiers atteinte(10)");
            }
            else{
                setErrorMessage("Erreur! Aucun fichier est sélectionné");
            }
        }
    }

    return (
        <>
        <Button variant="info" onClick={reset} className="btn_modal">
            Déposer un CV
        </Button>

        <Modal show={show} onHide={handleClose}>
            <Modal.Header>
                <Modal.Title>Ajout d'un CV</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Row>
                    <Col xs={9} >
                        <Form.Group controlId="document" className="cont_file_form">
                            <Form.Control
                            type="file"
                            onChange={(e) => {
                                setDocument(e.target.files[0]);
                            }}
                            accept=".pdf"
                            />
                        </Form.Group>
                    </Col>
                    <Col xs={3} >
                        <Button variant="success" size="md" className="btn_sub" onClick={(e) => onCreatePost(e)}>
                            Déposer
                        </Button>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <p className="error_p" style={{
                            color: errorMessage.startsWith("Erreur")
                            ? "red"
                            : "green",
                            }}>{errorMessage}</p>
                    </Col>
                </Row>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="danger" size="lg" onClick={handleClose}>
                    Fermer
                </Button>
            </Modal.Footer>
        </Modal>
      </ >
    );
}

export default CVButtonDeposit;