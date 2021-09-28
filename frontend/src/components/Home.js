import React from "react";
import auth from "../services/Auth";
import "../App.css"

function Home() {

  let user = auth.user

  return (
    <div className="cont_home">
      <h1>Bonjour {user.firstName} !</h1>
    </div>
  );
}

export default Home;
