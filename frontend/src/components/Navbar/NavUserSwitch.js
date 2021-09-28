import React from "react";
import { Link } from "react-router-dom"
import "../../App.css";
import NavDropdown from 'react-bootstrap/NavDropdown';

function NavUserSwitch({ userStatus }) {
  function checkIfGS() {
    let user = userStatus.user;
    if (user.username.startsWith("G")) {
      return <>
        <NavDropdown title="Offre de stage" className="nav-dropdown">
            <NavDropdown.Item>
              <Link className="nav-links-header" to="/formInternshipOffer">
                <li>Dépôt d'offres de stage</li>
              </Link>
            </NavDropdown.Item>
        </NavDropdown>
      </>;
    }
  }

  function checkIfStudent() {
    let user = userStatus.user;
    if (user.username.startsWith("E")) {
      return <>
      <NavDropdown title="Offre de stage" className="nav-dropdown">
            <NavDropdown.Item>
              <Link className="nav-links-header" to="/listInternshipOffer">
                <li>Listes d'offres de stage</li>
              </Link>
            </NavDropdown.Item>
        </NavDropdown>
      </>;
    }
  }

  function checkIfSupervisor() {
    let user = userStatus.user;
    if (user.username.startsWith("S")) {
      return <>
        <NavDropdown title="Étudiant" className="nav-dropdown">
          <NavDropdown.Item>
            <Link className="nav-links-header" to="/listStudents">
              <li>Liste des étudiants</li>
            </Link>
          </NavDropdown.Item>
        </NavDropdown>
      </>;
    }
  }

  function checkIfMonitor() {
    let user = userStatus.user;
    if (user.username.startsWith("M")) {
      return <>
        <NavDropdown title="Offre de stage" className="nav-dropdown">
            <NavDropdown.Item>
              <Link className="nav-links-header" to="/formInternshipOffer">
                <li>Dépôt d'offres de stage</li>
              </Link>
            </NavDropdown.Item>
        </NavDropdown>
      </>;
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
