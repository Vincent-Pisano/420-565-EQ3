import React from "react";
import "../App.css";
import { Link } from "react-router-dom"

function NavUserSwitch({ userStatus }) {
  function checkIfGS() {
    let user = userStatus.user;
    if (user.username.startsWith("G")) {
      return <>
        <Link className="nav-links-header" to="/formInternshipOffer">
          <li>Dépôt offres de stage</li>
        </Link>
      </>;
    }
  }

  function checkIfStudent() {
    let user = userStatus.user;
    if (user.username.startsWith("E")) {
      return <>
        <Link className="nav-links-header" to="/listInternshipOffer">
          <li>Listes d'offres de stage</li>
        </Link>
      </>;
    }
  }

  function checkIfSupervisor() {
    let user = userStatus.user;
    if (user.username.startsWith("S")) {
      return <>
        <Link className="nav-links-header" to="/listStudents">
          <li>Liste des étudiants</li>
        </Link>
      </>;
    }
  }

  function checkIfMonitor() {
    let user = userStatus.user;
    if (user.username.startsWith("M")) {
      return <>
      <Link className="nav-links-header" to="/formInternshipOffer">
          <li>Dépôt offres de stage</li>
        </Link>
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
