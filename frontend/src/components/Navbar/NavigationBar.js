import { React, useState } from "react";
import auth from "../../services/Auth";
import { Link } from "react-router-dom";
import { useHistory } from "react-router";
import NavUserSwitch from "./NavUserSwitch";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import "../../styles/Navbar.css";

function NavigationBar() {
  let history = useHistory();
  const [userStatus, setUserStatus] = useState({
    isLoggedIn: auth.authenticated,
  });

  history.listen(() => {
    setUserStatus({
      isLoggedIn: auth.authenticated,
    });
  });

  function checkIfLogin() {
    if (userStatus.isLoggedIn) {
      return (
        <>
          <Nav.Link>
            <li
              className="nav-links-header"
              onClick={() => {
                history.push({
                  pathname: `/home/${auth.user.username}`,
                });
              }}
            >
              Home
            </li>
          </Nav.Link>
          <NavUserSwitch />
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

          <Nav.Link as={Link} to="/connexion">
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
