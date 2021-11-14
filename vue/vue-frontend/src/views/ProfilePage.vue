<template>
  <div class="container">
    <div class="card">
      <img src="./../assets/pfp.png"  style="width: 100%" />
      <h1>Nom d'utilisateur: {{this.user.username}}</h1>
      <h2>Prénom, Nom: {{this.user.firstName}} {{this.user.lastName}}</h2>
      <p class="title">Courriel: {{this.user.email}}</p>
      <div v-if="this.user.username.startsWith('E') || this.user.username.startsWith('S')">
        <p>Department: {{this.user.department}}</p>
      </div>
      <div v-if="this.user.username.startsWith('M')">
        <p>Nom d'entreprise: {{this.user.enterpriseName}}</p>
        <p>Poste de travail: {{this.user.jobTitle}}</p>
      </div>
      <br>
      <p><button>Déconnecter</button></p>
    </div>
  </div>
</template>

<script>
import router from "./../router/index";

export default {
  name: "ProfilePage",
  inheritAttrs: false,
  methods: {
    getUserInfo: function () {
      if(sessionStorage.getItem("user")){
        var user = sessionStorage.getItem("user");
        var viewName = JSON.parse(user);
        console.log(viewName);
        return viewName;
      } else {
        router.push("/");
      }
    },
  },
  created: function () {
    this.getUserInfo();
  },
  data() {
    return {
      user: this.getUserInfo(),
    };
  },
};
</script>

<style>
@import "./../styles/GeneralStyles.css";

.card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  max-width: 500px;
  margin: auto;
  text-align: center;
}

.title {
  color: grey;
  font-size: 18px;
}

button {
  border: none;
  outline: 0;
  display: inline-block;
  padding: 8px;
  color: white;
  background-color: #000;
  text-align: center;
  cursor: pointer;
  width: 100%;
  font-size: 18px;
}

a {
  text-decoration: none;
  font-size: 22px;
  color: black;
}

button:hover, a:hover {
  opacity: 0.7;
}
</style>