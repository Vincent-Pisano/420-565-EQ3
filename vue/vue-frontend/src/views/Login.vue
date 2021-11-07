<template>
  <div class="container">
    <h1 class="title">Page de connection</h1>
    <form @submit="onSubmit" class="add-form">
      <div class="form-control">
        <label>Nom d'utilisateur</label>
        <input
          type="text"
          v-model="username"
          name="username"
          placeholder="Entrez votre nom d'utilisateur"
        />
      </div>
      <div class="form-control">
        <label>Mot de passe</label>
        <input
          type="password"
          v-model="pw"
          name="pw"
          placeholder="Entrez votre mot de passe"
        />
      </div>
       <div class="form-control">
          <p>{{errorMessage}}</p>
       </div>
      <input type="submit" value="Se connecter" class="btn btn-block" />
    </form>
  </div>
</template>

<script>
import axios from "axios";

function getUserType(username) {
  if (username.startsWith("E")) {
    return "student";
  } else if (username.startsWith("M")) {
    return "monitor";
  } else if (username.startsWith("S")) {
    return "supervisor";
  } else if (username.startsWith("G")) {
    return "internshipManager";
  }
}

export default {
  name: "Login",
  inheritAttrs: false,
  data() {
    return {
      username: "",
      pw: "",
      errorMessage: "",
    };
  },
  methods: {
    onSubmit(e) {
      e.preventDefault();
      if (!this.username || !this.pw) {
        this.errorMessage = "Erreur! Veillez remplir tous les champs!"
        return;
      } else {
        var userType = getUserType(this.username);
        axios
          .get(
            "http://localhost:9090/login/" +
              userType +
              "/" +
              this.username +
              "/" +
              this.pw
          )
          .then(function (response) {
            console.log(response.data);
          })
          .catch((error) => {
            console.log(error)
            this.errorMessage = "Erreur de connection, vérifiez le nom d'utilisateur et le mot de passe!"
          });
      }
    },
  },
};
</script>

<style>
@import "./../styles/FormStyles.css";
@import "./../styles/GeneralStyles.css";
</style>