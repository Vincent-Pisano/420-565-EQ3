import axios from "axios";
import { useState } from "react";
import { Container } from "react-bootstrap";
import { VALIDATE_INTERNSHIP_OFFER } from "../../Utils/API";
import {
  ERROR_VALIDATION_INTERNSHIP_OFFER,
  CONFIRM_VALIDATION_INTERNSHIP_OFFER,
} from "../../Utils/Errors_Utils";
import InternshipOfferModalConfirmRefusal from "./Modal/InternshipOfferModalConfirmRefusal";

const InternshipOfferButtonValidate = ({
  internshipOffer,
  errorMessage,
  setErrorMessage,
  redirect,
}) => {
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  function validateInternshipOffer() {
    axios
      .post(VALIDATE_INTERNSHIP_OFFER + internshipOffer.id)
      .then((response) => {
        setErrorMessage(CONFIRM_VALIDATION_INTERNSHIP_OFFER);
        setTimeout(() => {
          redirect();
        }, 2000);
      })
      .catch((error) => {
        setErrorMessage(ERROR_VALIDATION_INTERNSHIP_OFFER);
      });
  }

  return (
    <>
      <Container className="cont_btn">
        <p
          style={{
            color: errorMessage.startsWith("Erreur") ? "red" : "green",
          }}
        >
          {errorMessage}
        </p>
        <button
          className="btn btn-lg btn-success mx-3 mb-3"
          onClick={() => validateInternshipOffer()}
        >
          Valider
        </button>
        <button
          className="btn btn-lg btn-danger mx-3 mb-3"
          onClick={() => handleShow()}
        >
          Refuser
        </button>
      </Container>
      <InternshipOfferModalConfirmRefusal
        show={show}
        handleClose={handleClose}
        redirect={redirect}
        internshipOffer={internshipOffer}
        setErrorMessage={setErrorMessage}
      />
    </>
  );
};
export default InternshipOfferButtonValidate;
