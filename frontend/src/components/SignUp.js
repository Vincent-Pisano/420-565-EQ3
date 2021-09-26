import React from "react";
import "../App.css";
import { useState } from "react";
import SignUpStudent from "./SignUpStudent";
import SignUpMonitor from "./SignUpMonitor";
import SignUpSupervisor from "./SignUpSupervisor";

const SignUp = () => {

  const [currentSignUp, setCurrentSignUp] = useState(
    "student"
  )

  function handleClick(newChoice) {
    return function () {
      setCurrentSignUp(newChoice)
   }
  }

  function chooseSignUp() {
    if (currentSignUp === "student") {
      return <SignUpStudent/>;
    }
    if (currentSignUp === "monitor") {
      return <SignUpMonitor/>;
    }
    if (currentSignUp === "supervisor") {
      return <SignUpSupervisor/>;
    }
    return <p>Not Implemented yet</p>;
  }

  return (
    <div className="cont_principal">
      <div className="cont_sign_up">
          <div className="link_inscriptions">
            <button className={currentSignUp === "student" ? 'btn_link_selected' : 'btn_link'}
              onClick={handleClick("student")}>
              Étudiant
            </button>
            <button className={currentSignUp === "supervisor" ? 'btn_link_selected' : 'btn_link'}
              onClick={handleClick("supervisor")}>
              Superviseur
            </button>
            <button className={currentSignUp === "monitor" ? 'btn_link_selected' : 'btn_link'}
              onClick={handleClick("monitor")}>
              Moniteur
            </button>
          </div>
        <div className="cont_login">
          {chooseSignUp()} 
        </div>
      </div>
    </div>
  );
};
export default SignUp;
