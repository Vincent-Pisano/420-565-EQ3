import React from "react";
import { Link } from "react-router-dom";
import "../../App.css";
import Nav from "react-bootstrap/Nav";

function NavUserSwitch({ userStatus }) {
  function checkIfGS() {
    let user = userStatus.user;
    if (user.username.startsWith("G")) {
      return (
        <>
          <Nav.Link to="/formInternshipOffer">
            <li className="nav-links-header">Dépôt d'offres de stage</li>
          </Nav.Link>
        </>
      );
    }
  }

  function checkIfStudent() {
    let user = userStatus.user;
    if (user.username.startsWith("E")) {
      return (
        <>
          <Nav.Link as={Link} to="/listInternshipOffer">
            <li className="nav-links-header">Listes d'offres de stage</li>
          </Nav.Link>
        </>
      );
    }
  }

  function checkIfSupervisor() {
    let user = userStatus.user;
    if (user.username.startsWith("S")) {
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
    let user = userStatus.user;
    if (user.username.startsWith("M")) {
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
