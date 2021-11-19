import { useState } from "react";
import "./../../../styles/CV.css";
import CVModalActive from "../Modal/CVModalActive";

const CVButtonActive = ({ documentId, documentActive }) => {
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  function reset() {
    handleShow();
  }

  return (
    <>
      <button
        className="btn btn-warning btn-sm"
        onClick={reset}
        disabled={documentActive}
      >
        {documentActive ? "Déjà Actif" : "Mettre Actif"}
      </button>

      <CVModalActive
        handleClose={handleClose}
        show={show}
        documentId={documentId}
      />
    </>
  );
};
export default CVButtonActive;
