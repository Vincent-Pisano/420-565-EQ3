<template>
  <div class="container">
    <h1 class="title">Page d'inscription étudiant</h1>
    <form @submit="onSubmit" class="add-form">
      <div class="form-control">
        <label
          >Nom d'utilisateur, Veillez commencer votre nom d'utilisateur par
          "E"</label
        >
        <input
          type="text"
          v-model="fields.username"
          name="username"
          placeholder="Entrez votre nom d'utilisateur"
          required
        />
      </div>
      <div class="form-control">
        <label>Mot de passe</label>
        <input
          type="password"
          v-model="fields.password"
          name="password"
          placeholder="Entrez votre mot de passe"
          required
        />
      </div>
      <div class="form-control">
        <label>Email</label>
        <input
          type="email"
          v-model="fields.email"
          name="email"
          placeholder="Entrez votre courriel"
          required
        />
      </div>
      <div class="form-control">
        <label>Prénom</label>
        <input
          type="text"
          v-model="fields.firstName"
          name="firstName"
          placeholder="Entrez votre prénom"
          required
        />
      </div>
      <div class="form-control">
        <label>Nom</label>
        <input
          type="text"
          v-model="fields.lastName"
          name="lastName"
          placeholder="Entrez votre nom"
          required
        />
      </div>
      <div class="form-control">
        <label>Department</label>
        <select v-model="fields.department" name="department" class="classic">
          <option value="COMPUTER_SCIENCE">Informatique</option>
          <option value="ARCHITECTURE">Architecture</option>
          <option value="NURSING">Infirmier</option>
        </select>
      </div>
      <div class="form-control">
        <p>{{ errorMessage }}</p>
      </div>
      <input type="submit" value="S'inscrire" class="btn btn-block" />
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "SignupStudent",
  inheritAttrs: false,
  data() {
    return {
      fields: {
        username: "",
        password: "",
        department: "COMPUTER_SCIENCE",
        email: "",
        firstName: "",
        lastName: "",
      },
      errorMessage: "",
    };
  },
  methods: {
    onSubmit(e) {
      e.preventDefault();
      if (!this.fields.username.startsWith("E")) {
        this.errorMessage =
          'Erreur! Le nom d\'utilisateur doit commencer par un "E"!';
        return;
      } else {
        axios
          .post("http://localhost:9090/signUp/student", this.fields)
          .then(function (response) {
            console.log(response.data);
          })
          .catch((error) => {
            console.log(error);
            this.errorMessage = "Erreur d'insription!";
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