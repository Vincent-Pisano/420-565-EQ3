import axios from "axios";
import { Container } from "react-bootstrap";

const InternshipOfferButtonValidate = ({
  internshipOfferID,
  errorMessage,
  setErrorMessage,
  redirect,
}) => {
  function validateInternshipOffer() {
    axios
      .post(
        `http://localhost:9090/validate/internshipOffer/${internshipOfferID}`
      )
      .then((response) => {
        setErrorMessage(
          "L'offre de stage a été validée, vous allez être redirigé"
        );
        setTimeout(() => {
          redirect();
        }, 2000);
      })
      .catch((error) => {
        setErrorMessage("Erreur lors de la validation");
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
      <button className="btn_submit" onClick={() => validateInternshipOffer()}>
        Valider
      </button>
    </Container>
  );
};
export default InternshipOfferButtonValidate;
