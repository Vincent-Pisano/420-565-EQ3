import { Container } from "react-bootstrap";

const InternshipOfferButtonApply = ({ applyInternshipOffer, errorMessage }) => {
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
