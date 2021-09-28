import {React, useState} from "react";
import auth from '../../services/Auth'
import { Link } from "react-router-dom"
import { useHistory } from "react-router";
import NavUserSwitch from "./NavUserSwitch";
import "../../App.css"

function Nav() {

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
      <div>
        <li className="nav-links-header"
          onClick={() => {
            history.push({
              pathname: `/home/${userStatus.user.username}`,
              state: userStatus.user 
            });
          }}
        >
          Home
        </li>
        <NavUserSwitch userStatus={userStatus}/>
        <li className="nav-links-header"
          onClick={() => {
            auth.logout(() => {
              history.push("/");
            });
          }}
        >
          Déconnexion
        </li>
      </div>
      );
    }
    else {
      return (
        <>
          <Link className="nav-links-header" to="/signUp">
              <li>Inscriptions</li>
          </Link>
          <Link className="nav-links-header" to="/">
              <li>Connection</li>
          </Link>
        </>
      );
    }
  }
  return (
    <nav className="nav-header">
        <h1>Gestionnaire de stage</h1>
        <ul className="nav-link-header">
            {checkIfLogin()}
        </ul>
    </nav>
  );
}



export default Nav;
