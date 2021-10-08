import React from "react";
import { Link } from "react-router-dom";
import "../../App.css";
import Nav from "react-bootstrap/Nav";
import auth from "../../services/Auth";

function NavUserSwitch() {
  function checkIfGS() {
    if (auth.isInternshipManager()) {
      return (
        <>
          <Nav.Link as={Link} to="/formInternshipOffer">
            <li className="nav-links-header">Dépôt Offre</li>
          </Nav.Link>
          <Nav.Link as={Link} to="/listInternshipOffer">
            <li className="nav-links-header">Liste Offres</li>
          </Nav.Link>
          <Nav.Link as={Link} to="/listStudents">
            <li className="nav-links-header">Liste CV</li>
          </Nav.Link>
          <Nav.Link as={Link} to="/listSupervisors">
            <li className="nav-links-header">Assignation</li>
          </Nav.Link>
        </>
      );
    }
  }

  function checkIfStudent() {
    if (auth.isStudent()) {
      return (
        <>
          <Nav.Link as={Link} to="/listInternshipOffer">
            <li className="nav-links-header">Liste d'offres de stage</li>
          </Nav.Link>
        </>
      );
    }
  }

  function checkIfSupervisor() {
    if (auth.isSupervisor()) {
      return (
        <>
          <Nav.Link as={Link} to="/listStudents">
            <li className="nav-links-header">Liste des étudiants</li>
          </Nav.Link>
        </>
      );
    }
  }

  function checkIfMonitor() {
    if (auth.isMonitor()) {
      return (
        <>
          <Nav.Link as={Link} to="/formInternshipOffer">
            <li className="nav-links-header">Dépôt d'offres de stage</li>
          </Nav.Link>
        </>
      );
    }
  }

  return (
    <>
      {checkIfGS()}
      {checkIfStudent()}
      {checkIfSupervisor()}
      {checkIfMonitor()}
    </>
  );
}

export default NavUserSwitch;
