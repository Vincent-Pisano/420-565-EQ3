import React from "react";
import { Link } from "react-router-dom";
import "../../App.css";
import auth from "../../services/Auth";
import NavDropdown from "react-bootstrap/NavDropdown";

function NavUserSwitch() {
  function checkIfGS() {
    if (auth.isInternshipManager()) {
      return (
        <>
          <NavDropdown
            className="nav-drop-cust"
            title={<span className="nav-links-header">Options GS</span>}
            menuVariant="dark"
            noCaret
          >
            <NavDropdown.Item as={Link} to="/formInternshipOffer">
              Dépôt Offre
            </NavDropdown.Item>
            <NavDropdown.Item as={Link} to="/listInternshipOffer">
              Liste Offres
            </NavDropdown.Item>
            <NavDropdown.Item as={Link} to="/listStudents">
              Liste CV
            </NavDropdown.Item>
            <NavDropdown.Item as={Link} to="/listSupervisors">
              Assignation
            </NavDropdown.Item>
          </NavDropdown>
        </>
      );
    }
  }

  function checkIfStudent() {
    if (auth.isStudent()) {
      return (
        <>
          <NavDropdown
            className="nav-drop-cust"
            title={<span className="nav-links-header">Options Étudiant</span>}
            menuVariant="dark"
            noCaret
          >
            <NavDropdown.Item as={Link} to="/listInternshipOffer">
              Liste d'offres de stage
            </NavDropdown.Item>
          </NavDropdown>
        </>
      );
    }
  }

  function checkIfSupervisor() {
    if (auth.isSupervisor()) {
      return (
        <>
          <NavDropdown
            className="nav-drop-cust"
            title={<span className="nav-links-header">Options Superviseur</span>}
            menuVariant="dark"
            noCaret
          >
            <NavDropdown.Item as={Link} to="/listStudents">
              Liste des étudiants
            </NavDropdown.Item>
          </NavDropdown>
        </>
      );
    }
  }

  function checkIfMonitor() {
    if (auth.isMonitor()) {
      return (
        <>
          <NavDropdown
            className="nav-drop-cust"
            title={<span className="nav-links-header">Options Moniteur</span>}
            menuVariant="dark"
            noCaret
          >
            <NavDropdown.Item as={Link} to="/formInternshipOffer">
              Dépôt d'offres de stage
            </NavDropdown.Item>
          </NavDropdown>
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
