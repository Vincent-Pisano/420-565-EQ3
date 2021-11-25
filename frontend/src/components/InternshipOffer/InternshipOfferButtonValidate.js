import axios from "axios";
import { Container } from "react-bootstrap";
import {
  VALIDATE_INTERNSHIP_OFFER,
  REFUSE_INTERNSHIP_OFFER,
} from "../../Utils/API";
import {
  ERROR_VALIDATION_INTERNSHIP_OFFER,
  ERROR_REFUSING_INTERNSHIP_OFFER,
  CONFIRM_VALIDATION_INTERNSHIP_OFFER,
  CONFIRM_REFUSING_INTERNSHIP_OFFER,
} from "../../Utils/Errors_Utils";

const InternshipOfferButtonValidate = ({
  internshipOfferID,
  errorMessage,
  setErrorMessage,
  redirect,
}) => {
  function validateInternshipOffer() {
    axios
      .post(VALIDATE_INTERNSHIP_OFFER + internshipOfferID)
      .then((response) => {
        setErrorMessage(
          CONFIRM_VALIDATION_INTERNSHIP_OFFER
        );
        setTimeout(() => {
          redirect();
        }, 2000);
      })
      .catch((error) => {
        setErrorMessage(ERROR_VALIDATION_INTERNSHIP_OFFER);
      });
  }

  function refuseInternshipOffer() {
    axios
      .post(REFUSE_INTERNSHIP_OFFER + internshipOfferID)
      .then((response) => {
        setErrorMessage(
          CONFIRM_REFUSING_INTERNSHIP_OFFER
        );
        setTimeout(() => {
          redirect();
        }, 2000);
      })
      .catch((error) => {
        setErrorMessage(ERROR_REFUSING_INTERNSHIP_OFFER);
      });
  }

  return (
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
        onClick={() => refuseInternshipOffer()}
      >
        Refuser
      </button>
    </Container>
  );
};
export default InternshipOfferButtonValidate;
