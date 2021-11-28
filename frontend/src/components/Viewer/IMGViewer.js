import React, { useState } from "react";
import Viewer from "react-viewer";
import { Container, Button, Row, Col } from "react-bootstrap";
import { GET_SIGNATURE } from "../../Utils/API";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrashAlt } from "@fortawesome/free-solid-svg-icons";
import ModalConfirmDeleteSignature from "./Modal/ModalConfirmDeleteSignature";

const ImgViewer = ({ username, setHasASignature }) => {
  const [visible, setVisible] = useState(false);

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <div>
      <Container className="cont_btn_file">
        <Row>
          <Col md={9}>
            <Button
              className="btn_submit mb-3"
              onClick={() => {
                setVisible(true);
              }}
            >
              Visualiser la signature
            </Button>
          </Col>
          <Col md={3}>
            <FontAwesomeIcon
              className="fa-3x text-danger"
              icon={faTrashAlt}
              onClick={handleShow}
            />
          </Col>
        </Row>
      </Container>{" "}
      <Viewer
        visible={visible}
        onClose={() => {
          setVisible(false);
        }}
        images={[{ src: GET_SIGNATURE + username, alt: "" }]}
      />
      <ModalConfirmDeleteSignature
        show={show}
        handleClose={handleClose}
        username={username}
        setHasASignature={setHasASignature}
      />
    </div>
  );
};

export default ImgViewer;
