import { useState } from "react";
import "./../../../styles/CV.css";
import CVModalActive from "../Modal/CVModalActive";

const CVButtonActive = ({ cv }) => {
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
        disabled={cv.isActive || cv.status === "REFUSED"}
      >
        {cv.status === "REFUSED" ? "Refusé" : cv.isActive ? "Déjà Actif" : "Mettre Actif"}
      </button>

      <CVModalActive
        handleClose={handleClose}
        show={show}
        documentId={cv.id}
      />
    </>
  );
};
export default CVButtonActive;
