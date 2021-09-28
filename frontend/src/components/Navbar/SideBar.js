import React from "react";
import { Nav } from "react-bootstrap";
import { withRouter } from "react-router";
import "../../styles/SideBarStyles.css";
import { useState } from "react";
import auth from "../../services/Auth";
import { useHistory } from "react-router";
import SideBarUserSwitch from "./SideBarUserSwitch";
import { Link } from "react-router-dom";

const Side = () => {
  let history = useHistory();
  const [userStatus, setUserStatus] = useState({
    isLoggedIn: auth.authenticated,
    user: auth.user,
  });

  history.listen(() => {
    setUserStatus({
      isLoggedIn: auth.authenticated,
      user: auth.user,
    });
  });

  /*function checkIfLogin(){
        if (userStatus.isLoggedIn) {
            return (
                <>
                <Nav.Item>
                <li className=""
                onClick={() => {
                    history.push({
                    pathname: `/home/${userStatus.user.username}`,
                    state: userStatus.user 
                    });
                }}
                >
                Home
                </li>
                </Nav.Item>
                <SideBarUserSwitch userStatus={userStatus}/>
                <Nav.Item>
                <li className=""
                onClick={() => {
                    auth.logout(() => {
                    history.push("/");
                    });
                }}
                >
                Déconnexion
                </li>
                </Nav.Item>
                </>
            );
        }
        else {
            return (
                <>
                <Nav.Item>
                    <Link className="link_li" to="/signUp">
                        <li>Inscriptions</li>
                    </Link>
                </Nav.Item>
                <Nav.Item>
                    <Link className="link_li" to="/">
                        <li>Connection</li>
                    </Link>
                </Nav.Item>
                </>
            );
        }
    }
    return (
        <Nav className="col-md-12 d-none d-md-block bg sidebar">
            <div className="sidebar-sticky"></div>
            {checkIfLogin()}
        </Nav>
    )*/

  return (
    <>
      <header>
        <nav
          id="sidebarMenu"
          className="collapse d-lg-block sidebar collapse bg-white"
        >
          <div className="position-sticky">
            <div className="list-group list-group-flush mx-3 mt-4">
              <a
                href="#"
                className="list-group-item list-group-item-action py-2 ripple"
                aria-current="true"
              >
                <i className="fas fa-tachometer-alt fa-fw me-3"></i>
                <span>Main dashboard</span>
              </a>
            </div>
          </div>
        </nav>
        <nav
       id="main-navbar"
       class="navbar navbar-expand-lg navbar-light bg-white fixed-top"
       >
    <div class="container-fluid">
      <button
              class="navbar-toggler"
              type="button"
              data-mdb-toggle="collapse"
              data-mdb-target="#sidebarMenu"
              aria-controls="sidebarMenu"
              aria-expanded="false"
              aria-label="Toggle navigation"
              >
        <i class="fas fa-bars"></i>
      </button>
      <a class="navbar-brand" href="#">
        <img
             src="https://www.kanadakulturmerkezi.com/wp-content/uploads/2020/09/Cegep-Andre-Laurendeau.jpg"
             height="25"
             alt=""
             loading="lazy"
             />
      </a>
    </div>
  </nav>
      </header>
    </>
  );
};
const Sidebar = withRouter(Side);
export default Sidebar;
