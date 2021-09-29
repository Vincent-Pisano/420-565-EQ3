import {React, useState} from "react";
import auth from '../../services/Auth'
import { Link } from "react-router-dom"
import { useHistory } from "react-router";
import NavUserSwitch from "./NavUserSwitch";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import "../../styles/Navbar.css"

function NavigationBar() {

  let history = useHistory();
  const [userStatus, setUserStatus] = useState({
    isLoggedIn: auth.authenticated,
    user: auth.user
  });

  history.listen(() => {
    setUserStatus({
      isLoggedIn: auth.authenticated,
      user: auth.user
    })
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
                pathname: `/home/${userStatus.user.username}`,
                state: userStatus.user 
              });
            }}
          >
            Home
          </li>
        </Nav.Link>
        <NavUserSwitch userStatus={userStatus}/>
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
    }
    else {
      return (
        <>
        <Nav.Link>
          <Link className="nav-links-header" to="/signUp">
              <li>Inscriptions</li>
          </Link>
        </Nav.Link>
        <Nav.Link>
          <Link className="nav-links-header" to="/connexion">
              <li>Connexion</li>
          </Link>
        </Nav.Link>
        </>
      );
    }
  }
  return (
    <Navbar id="main-navbar"
        collapseOnSelect expand="lg" bg="white" variant="light">
          <Navbar.Brand className="title-navbar">GDS</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="mr">
              {checkIfLogin()}
            </Nav>
          </Navbar.Collapse>
        </Navbar>
  );
}

export default NavigationBar;
