import { Container } from "react-bootstrap";
import axios from "axios";
import auth from "../../services/Auth";
import { useHistory } from "react-router";

const InternshipOfferButtonApply = ({fields, setHasApplied, errorMessage, setErrorMessage}) => {

  let history = useHistory()
  let user = auth.user;

  function applyInternshipOffer() {
    axios
      .post(
        `http://localhost:9090/apply/internshipOffer/${user.username}`,
        fields
      )
      .then((response) => {
        setHasApplied(true);
        setTimeout(() => {
          history.push({
            pathname: `/listInternshipOffer`,
          });
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
      <button
        className="btn_submit"
        onClick={() => applyInternshipOffer()}
      >
        Appliquer
      </button>
    </Container>
      );
  };
  export default InternshipOfferButtonApply;
  