<template>
  <div v-if="this.user != null">
    <div class="container">
      <h1 class="title">Liste des offres de stages à valider</h1>

      <div :key="offer.id" v-for="offer in unvalidatedInternshipOfferList">
        <InternshipOffer :internshipOffer="offer" />
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
  name: "InternshipOfferListToValidate",
  inheritAttrs: false,
  data() {
    return {
      user: this.getUserInfo(),
      unvalidatedInternshipOfferList: [],
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
        if (!viewName.username.startsWith("G")) {
          this.logOut();
        } else {
          return viewName;
        }
      } else {
        this.logOut();
      }
    },
    getUnvalidatedInternshipOfferList: function () {},
  },
  created() {
    axios
      .get("http://localhost:9090/get/unvalidated/internshipOffer")
      .then((response) => {
        this.unvalidatedInternshipOfferList = response.data;
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