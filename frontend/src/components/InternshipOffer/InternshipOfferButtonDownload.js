import React, { useState } from "react";
import { Container, Button } from "react-bootstrap";
import PDFViewer from "../Viewer/PDFViewer";
import { DOWNLOAD_INTERNSHIP_OFFER_DOCUMENT } from "../../Utils/API"

const InternshipOfferButtonDownload = ({ internshipOfferID, document }) => {
  const [show, setShow] = useState(false);
  function handleShow() {
    setShow(!show);
  }

  function showPDFViewer() {
    if (show) {
      return (
        <Container className="pdf-container mb-5">
          <PDFViewer
            url={
              DOWNLOAD_INTERNSHIP_OFFER_DOCUMENT +
              internshipOfferID
            }
            documentName={document.name}
          />
        </Container>
      );
    }
  }

  return (
    <>
      <Container className="cont_btn_file">
        <Button className="btn_submit" onClick={handleShow}>
          Visualiser le document
        </Button>
      </Container>
      {showPDFViewer()}
    </>
  );
};
export default InternshipOfferButtonDownload;
