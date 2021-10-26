import React from "react";
import "../../App.css";
import auth from "../../services/Auth";
import NavUserGs from "./NavUserGS";
import NavUserStudent from "./NavUserStudent";
import NavUserMonitor from "./NavUserMonitor";
import NavUserSupervisor from "./NavUserSupervisor";

function NavUserSwitch() {
  function checkIfGS() {
    if (auth.isInternshipManager()) {
      return <NavUserGs />;
    }
  }

  function checkIfStudent() {
    if (auth.isStudent()) {
      return <NavUserStudent />;
    }
  }

  function checkIfSupervisor() {
    if (auth.isSupervisor()) {
      return <NavUserSupervisor />;
    }
  }

  function checkIfMonitor() {
    if (auth.isMonitor()) {
      return <NavUserMonitor />;
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
