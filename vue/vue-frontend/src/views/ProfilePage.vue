<template>
  <div v-if="this.user != null">
    <div class="container">
      <div class="card">
        <img src="./../assets/pfp.png" style="width: 100%" />
        <h1>Nom d'utilisateur: {{ this.user.username }}</h1>
        <h2>Prénom, Nom: {{ this.user.firstName }} {{ this.user.lastName }}</h2>
        <p class="title">Courriel: {{ this.user.email }}</p>
        <div
          v-if="
            this.user.username.startsWith('E') ||
            this.user.username.startsWith('S')
          "
        >
          <p>Department: {{ this.getDepartment() }}</p>
        </div>
        <div v-if="this.user.username.startsWith('M')">
          <p>Nom d'entreprise: {{ this.user.enterpriseName }}</p>
          <p>Poste de travail: {{ this.user.jobTitle }}</p>
        </div>
        <br />
        <div
          v-if="
            this.user.username.startsWith('G') ||
            this.user.username.startsWith('M')
          "
        >
          <ButtonToInternshipOfferForm />
        </div>
        <br />
        <p><button @click="logOut()">Se déconnecter</button></p>
      </div>
    </div>
  </div>
</template>

<script>
import router from "./../router/index";
import ButtonToInternshipOfferForm from "./../components/ButtonToInternshipOfferForm.vue";

export default {
  name: "ProfilePage",
  components: {
    ButtonToInternshipOfferForm,
  },
  inheritAttrs: false,
  methods: {
    logOut() {
      router.push("/");
    },
    getDepartment() {
      if (this.user.department == "COMPUTER_SCIENCE") {
        return "Informatique";
      } else if (this.user.department == "ARCHITECTURE") {
        return "Architecture";
      } else {
        return "Infirmier";
      }
    },
    getUserInfo: function () {
      console.log(sessionStorage.getItem("user"));
      if (sessionStorage.getItem("user") !== null) {
        var user = sessionStorage.getItem("user");
        var viewName = JSON.parse(user);
        return viewName;
      } else {
        this.logOut();
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
@import "./../styles/ProfileStyles.css";
</style>