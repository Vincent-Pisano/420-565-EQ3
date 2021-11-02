import { Button, Modal, Row, Col } from "react-bootstrap";
const StudentInfoModal = ({ show, handleClose, currentStudent }) => {
  return (
    <>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>
            Informations de {currentStudent.firstName} {currentStudent.lastName}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row>
            <Col>Nom d'étudiant: {currentStudent.username}</Col>
          </Row>
          <Row>
            <Col>Courriel de l'étudiant: {currentStudent.email}</Col>
          </Row>
          <Row>
            <Col>
              Date d'inscription: {currentStudent.creationDate.substring(0, 10)}
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
export default StudentInfoModal;
