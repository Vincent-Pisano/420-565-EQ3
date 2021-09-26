import React from "react";
import "../App.css"
import auth from "../services/Auth";

function Home() {

  let user = auth.user

  return (
    <div className="cont_home">
      <h1>Bonjour {user.firstName} !</h1>
    </div>
  );
}

export default Home;
