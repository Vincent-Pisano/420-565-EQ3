import React from "react";
import { Nav } from "react-bootstrap";
import { Link } from "react-router-dom"

function SideBarUserSwitch({ userStatus }) {
  function checkIfGS() {
    let user = userStatus.user;
    if (user.username.startsWith("G")) {
      return <>
        <Nav.Item>
            <Link className="link_li" to="/formInternshipOffer">
                <li>Dépôt offres de stage</li>
            </Link>
        </Nav.Item>
      </>;
    }
  }

  function checkIfStudent() {
    let user = userStatus.user;
    if (user.username.startsWith("E")) {
      return <>
        <Nav.Item>
            <Link className="link_li" to="/listInternshipOffer">
                <li>Listes d'offres de stage</li>
            </Link>
        </Nav.Item>
      </>;
    }
  }

  function checkIfSupervisor() {
    let user = userStatus.user;
    if (user.username.startsWith("S")) {
      return <>
        <Nav.Item>
            <Link className="link_li" to="/listStudents">
                <li>Liste des étudiants</li>
            </Link>
        </Nav.Item>
      </>;
    }
  }

  function checkIfMonitor() {
    let user = userStatus.user;
    if (user.username.startsWith("M")) {
      return <>
        <Nav.Item>
            <Link className="link_li" to="/formInternshipOffer">
                <li>Dépôt offres de stage</li>
            </Link>
        </Nav.Item>
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

export default SideBarUserSwitch;
