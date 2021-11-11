import { Container } from "react-bootstrap";

const Readmission = ({ showModal, session }) => {
  return (
    <>
      <hr className="modal_separator mx-auto" />
      <h2 className="cont_title_form">Réinscription nécessaire</h2>
      <p>Veuillez vous réinscrire pour la session : "{session}"</p>
      <Container className="cont_btn">
        <button className="btn_submit" onClick={showModal}>
          Réinscrivez-vous
        </button>
      </Container>
      <hr className="modal_separator mx-auto" />
    </>
  );
};

export default Readmission;
