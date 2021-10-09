import axios from "axios";
import { useHistory } from "react-router";
import { Container } from "react-bootstrap";

const InternshipOfferButtonValidate = ({internshipOfferID, errorMessage, setErrorMessage}) => {

    let history = useHistory();

    function validateInternshipOffer() {
        axios
          .post(
            `http://localhost:9090/save/internshipOffer/validate/${internshipOfferID}`
          )
          .then((response) => {
            setErrorMessage(
              "L'offre de stage a été validée, vous allez être redirigé"
            );
            setTimeout(() => {
              history.push({
                pathname: `/listInternshipOffer`,
              });
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
          <button
            className="btn_submit"
            onClick={() => validateInternshipOffer()}
          >
            Valider
          </button>
        </Container>
      );
  };
  export default InternshipOfferButtonValidate;
  