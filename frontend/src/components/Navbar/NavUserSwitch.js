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
          <Nav.Link>
            <Link className="nav-links-header" to="/formInternshipOffer">
              <li>Dépôt d'offres de stage</li>
            </Link>
            <Link className="nav-links-header" to="/listInternshipOffer">
              <li>Liste offres de stage non validées</li>
            </Link>
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
          <Nav.Link>
            <Link className="nav-links-header" to="/listInternshipOffer">
              <li>Listes d'offres de stage</li>
            </Link>
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
          <Nav.Link>
            <Link className="nav-links-header" to="/listStudents">
              <li>Liste des étudiants</li>
            </Link>
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
          <Nav.Link>
            <Link className="nav-links-header" to="/formInternshipOffer">
              <li>Dépôt d'offres de stage</li>
            </Link>
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
