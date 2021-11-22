<template>
  <div v-if="this.user != null">
    <div class="container">
      <h1 class="title">Liste des offres de stages disponibles</h1>

      <div :key="offer.id" v-for="offer in validatedInternshipOfferList">
        <InternshipOffer :internshipOffer="offer" :username="user.username" />
      </div>

      <ButtonGoBackToProfile />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "./../router/index";
import ButtonGoBackToProfile from "../components/ButtonGoBackToProfile.vue";
import InternshipOffer from "../components/InternshipOffer.vue";

export default {
  components: { ButtonGoBackToProfile, InternshipOffer },
  name: "InternshipOfferListValidated",
  inheritAttrs: false,
  data() {
    return {
      user: this.getUserInfo(),
      validatedInternshipOfferList: [],
      errorMessage: "",
    };
  },
  methods: {
    logOut() {
      router.push("/");
    },
    getUserInfo: function () {
      if (sessionStorage.getItem("user") !== null) {
        var user = sessionStorage.getItem("user");
        var viewName = JSON.parse(user);
        if (!viewName.username.startsWith("E")) {
          this.logOut();
        } else {
          return viewName;
        }
      } else {
        this.logOut();
      }
    },
  },
  created() {
    axios
      .get("http://localhost:9090/get/validated/internshipOffer")
      .then((response) => {
        this.validatedInternshipOfferList = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style>
@import "./../styles/FormStyles.css";
@import "./../styles/GeneralStyles.css";
</style>