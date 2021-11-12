import { Container } from "react-bootstrap";

const InternshipOfferButtonApply = ({
  fields,
  applyInternshipOffer,
  errorMessage,
}) => {
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
