import { Container } from "react-bootstrap";
import axios from "axios";
import auth from "../../services/Auth";

const InternshipOfferButtonApply = ({
  fields,
  setHasApplied,
  errorMessage,
  setErrorMessage,
  redirect,
}) => {
  let user = auth.user;

  function applyInternshipOffer() {
    fields.monitor.signature = undefined;
    axios
      .post(
        `http://localhost:9090/apply/internshipOffer/${user.username}`,
        fields
      )
      .then((response) => {
        setHasApplied(true);
        setTimeout(() => {
          redirect();
        }, 3000);
        setErrorMessage(
          "Votre demande a été acceptée, vous allez être redirigé"
        );
      })
      .catch((error) => {
        setErrorMessage("Erreur lors de l'application à l'ofre de stage stage");
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
      <button className="btn_submit" onClick={() => applyInternshipOffer()}>
        Appliquer
      </button>
    </Container>
  );
};
export default InternshipOfferButtonApply;
