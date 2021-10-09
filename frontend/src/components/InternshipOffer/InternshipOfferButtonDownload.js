import { Container } from "react-bootstrap";

const InternshipOfferButtonDownload = ({internshipOfferID}) => {
    return (
        <Container className="cont_btn_file">
          <a
            className="btn_file"
            href={
              "http://localhost:9090/get/internshipOffer/document/" +
              internshipOfferID
            }
            download
          >
            Télécharger le document
          </a>
        </Container>
      );
  };
  export default InternshipOfferButtonDownload;
  