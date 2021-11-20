<template>
  <div v-if="this.user != null">
    <div class="container">
      <h1 class="title">Liste des offres de stages à valider</h1>
      
      <p>{{this.unvalidatedInternshipOfferList}}</p>
      <ButtonGoBackToProfile />
    </div>
  </div>
</template>

<script>

/*
<div :key="offer.id" v-for="offer in unvalidatedInternshipOfferList">
        <p>{{offer.id}}</p>
      </div>
*/
import axios from "axios";
import router from "./../router/index";
import ButtonGoBackToProfile from "../components/ButtonGoBackToProfile.vue";

export default {
  components: { ButtonGoBackToProfile },
  name: "InternshipOfferListToValidate",
  inheritAttrs: false,
  data() {
    return {
      user: this.getUserInfo(),
      unvalidatedInternshipOfferList: this.getUnvalidatedInternshipOfferList(),
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
    getUnvalidatedInternshipOfferList: function () {
      axios
        .get("http://localhost:9090/get/unvalidated/internshipOffer")
        .then(function (response) {

            /*for (const property in response.data) {
                console.log(`${property}: ${response.data[property]}`);
                this.unvalidatedInternshipOfferList.push(response.data[property]);
            }*/
            console.log(JSON.stringify(response.data))
            //this.unvalidatedInternshipOfferList = response.data;
            
          return response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created: function () {
    this.getUnvalidatedInternshipOfferList();
    console.log(this.user);
    console.log(this.unvalidatedInternshipOfferList);
  },
};
</script>

<style>
@import "./../styles/FormStyles.css";
@import "./../styles/GeneralStyles.css";
</style>