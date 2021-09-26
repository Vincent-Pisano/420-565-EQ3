import React from "react";
import {Nav} from "react-bootstrap";
import './SideBarStyles.css'
import {useState} from "react";
import auth from '../services/Auth'
import { useHistory } from "react-router";
import SideBarUserSwitch from "./SideBarUserSwitch";
import { Link } from "react-router-dom"

const SideBar = () => {

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

    function checkIfLogin(){
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
    )
  };
  export default SideBar;