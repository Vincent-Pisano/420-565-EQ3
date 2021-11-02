import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLandmark, faLaptopCode, faStethoscope, faUserCircle } from "@fortawesome/free-solid-svg-icons";
import { Row, Col, Modal, Button } from "react-bootstrap";
import { useState } from "react";

const ReportStudent = ({ student }) => {
    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    let icon = faUserCircle
    function checkWhichDepartment() {
        if (student.department === "COMPUTER_SCIENCE") {
            icon = faLaptopCode;
        } else if (student.department === "ARCHITECTURE") {
            icon = faLandmark
        } else if (student.department === "NURSING") {
            icon = faStethoscope
        }
    }
    return (
        <>
            <Row className="list_node" onClick = {handleShow} >
                {checkWhichDepartment()}
                <Col xs={3}>
                    <FontAwesomeIcon className="fa-3x" icon={icon} />
                </Col>
                <Col xs={9} className="list_node_text">
                    <li>
                        {student.firstName} {student.lastName}
                    </li>
                </Col>
            </Row>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header >
                    <Modal.Title>Informations de {student.firstName} {student.lastName}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Row>
                        <Col>
                            Identifiant de l'étudiant: {student.username}
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            Courriel de l'étudiant: {student.email}
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            Date d'inscription: {student.creationDate.substring(0, 10)}
                        </Col>
                    </Row>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Fermer
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default ReportStudent;