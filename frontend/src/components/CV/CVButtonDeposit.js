import { Button, Modal, Form, Row, Col } from "react-bootstrap";
import { useState } from "react";
import "./CV.css"
import auth from "../../services/Auth";
import axios from "axios";

const CVButtonDeposit = () => {

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [document, setDocument] = useState(undefined);

    let user = auth.user

    function onCreatePost(e) {
        e.preventDefault();
        if (document !== undefined && document.type === "application/pdf") {
            let formData = new FormData();
            formData.append("document", document);
            axios
            .post(`http://localhost:9090/save/student/${user.idUser}/CV`, formData)
            .then((response) => {
                user = response.data;
                auth.user = user;
                handleClose();
                console.log(user);
            })
            .catch((error) => {
                console.log(error)
            });
        }
        else{
            console.log("Erreur, il faut ajouteer un fichier")
        }
    }

    return (
        <>
        <Button variant="info" onClick={handleShow} className="btn_modal">
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