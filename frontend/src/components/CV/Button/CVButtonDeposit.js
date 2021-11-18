import { Button } from "react-bootstrap";
import { useState } from "react";
import "./../../../styles/CV.css";
import CVModalDeposit from "../Modal/CVModalDeposit";

const CVButtonDeposit = () => {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  function reset() {
    handleShow();
  }

  return (
    <>
      <Button variant="secondary" onClick={reset} className="btn_modal">
        Déposer un CV
      </Button>

      <CVModalDeposit handleClose={handleClose} show={show} />
    </>
  );
};

export default CVButtonDeposit;
