import { useState } from "react";
import "./../../../styles/CV.css";
import CVModalDelete from "../Modal/CVModalDelete";

const CVButtonDelete = ({ documentId }) => {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  function reset() {
    handleShow();
  }

  return (
    <>
      <button className="btn btn-danger btn-sm" onClick={reset}>
        Supprimer
      </button>
      <CVModalDelete
        handleClose={handleClose}
        show={show}
        documentId={documentId}
      />
    </>
  );
};
export default CVButtonDelete;
