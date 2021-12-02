import { React, useState } from "react";
import auth from "../../services/Auth";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import "../../styles/Navbar.css";
import "../../styles/Session.css";
import { STUDENT_URL, SUPERVISOR_URL, MONITOR_URL, INTERNSHIP_MANAGER_URL } from "../../Utils/URL";
import { DOWNLOAD_ENTERPRISE_EVALUATION_DOCUMENT,
  DOWNLOAD_STUDENT_EVALUATION_DOCUMENT } from "../../Utils/API";

function NavigationBar() {
  let history = useHistory();
  let choices = auth.isStudent()
    ? STUDENT_URL
    : auth.isSupervisor()
    ? SUPERVISOR_URL
    : auth.isMonitor() 
    ? MONITOR_URL 
    : auth.isInternshipManager
    ? INTERNSHIP_MANAGER_URL
    : [];

  const [userStatus, setUserStatus] = useState({
    isLoggedIn: auth.authenticated,
  });

  const [currentChoice, setCurrentChoice] = useState(choices[0]);

  function changeRoute(choice) {
    setCurrentChoice(choice);
    history.push({
      pathname: choice.link,
    });
  }

  function checkForDownload() {
    if (auth.isSupervisor()) {
      return (
        <li>
          <a
            className={"menu-item-button menu-item-a"}
            href={DOWNLOAD_ENTERPRISE_EVALUATION_DOCUMENT}
            target="_blank" rel="noreferrer"
          >
            Document d'évaluation d'entreprise
          </a>
        </li>
      );
    } else if (auth.isMonitor()) {
      return (
        <li>
          <a
            className={"menu-item-button menu-item-a"}
            href={DOWNLOAD_STUDENT_EVALUATION_DOCUMENT}
            target="_blank" rel="noreferrer"
          >
            Document d'évaluation d'étudiant
          </a>
        </li>
      );
    }
  }

  history.listen(() => {
    setUserStatus({
      isLoggedIn: auth.authenticated,
    });
  });

  function checkIfLogin() {
    if (userStatus.isLoggedIn) {
      return (
        <>
          <div className="menu-item menu-navbar">
            <p className="menu-item-title py-2">{currentChoice.name}</p>
            <ul>
              {choices.map((choice, i) => (
                <li key={i}>
                  <button
                    className={
                      "menu-item-button" +
                      (currentChoice.key === choice.key
                        ? " menu-item-button-selected"
                        : "")
                    }
                    onClick={() => changeRoute(choice)}
                  >
                    {choice.name}
                  </button>
                </li>
              ))}
              {checkForDownload()}
            </ul>
          </div>
          <Nav.Link>
            <li
              className="nav-links-header"
              onClick={() => {
                auth.logout(() => {
                  history.push("/");
                });
              }}
            >
              Déconnexion
            </li>
          </Nav.Link>
        </>
      );
    } else {
      return (
        <>
          <Nav.Link as={Link} to="/signUp">
            <li className="nav-links-header">Inscriptions</li>
          </Nav.Link>

          <Nav.Link as={Link} to="/">
            <li className="nav-links-header">Connexion</li>
          </Nav.Link>
        </>
      );
    }
  }
  return (
    <Navbar
      id="main-navbar"
      collapseOnSelect
      expand="lg"
      bg="white"
      variant="light"
    >
      <Navbar.Brand className="title-navbar">GDS</Navbar.Brand>
      <Navbar.Toggle aria-controls="responsive-navbar-nav" />
      <Navbar.Collapse id="responsive-navbar-nav">
        <Nav className="mr">{checkIfLogin()}</Nav>
      </Navbar.Collapse>
    </Navbar>
  );
}

export default NavigationBar;
