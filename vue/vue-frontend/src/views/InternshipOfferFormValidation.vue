<template>
  <div v-if="this.user != null">
    <div class="container">
      <h1 class="title">Validation de l'offre de stage</h1>
      <form @submit="onSubmit" class="add-form">
        <div v-if="this.user.username.startsWith('G')">
          <div class="form-control">
            <label>Moniteur</label>
            <input
              type="text"
              v-model="internshipOffer.monitor"
              name="monitor"
              disabled
            />
          </div>

          <div class="form-control">
            <label>Nom du poste</label>
            <input
              type="text"
              v-model="internshipOffer.jobName"
              name="jobName"
              disabled
            />
          </div>
          <div class="form-control">
            <label>Description du poste</label>
            <input
              type="text"
              v-model="internshipOffer.description"
              name="description"
              disabled
            />
          </div>
          <div class="form-control">
            <label>Date de début du stage</label>
            <input
              type="date"
              v-model="internshipOffer.startDate"
              name="startDate"
              disabled
            />
          </div>
          <div class="form-control">
            <label>Date de fin du stage</label>
            <input
              type="date"
              v-model="internshipOffer.endDate"
              name="endDate"
              disabled
            />
          </div>
          <div class="form-control">
            <label>Nombre d'heures par semaine</label>
            <input
              type="number"
              v-model="internshipOffer.weeklyWorkTime"
              name="weeklyWorkTime"
              min="0"
              max="40"
              disabled
            />
          </div>

          <h1 class="title">Jours de travail</h1>
          <br />
          <input
            type="checkbox"
            id="lundi"
            name="lundi"
            value="Lundi"
            v-model="workDaysCheckboxes.lundi"
            disabled
          />
          <label for="lundi"> Lundi</label><br />
          <input
            type="checkbox"
            id="mardi"
            name="mardi"
            value="Mardi"
            v-model="workDaysCheckboxes.mardi"
            disabled
          />
          <label for="mardi"> Mardi</label><br />
          <input
            type="checkbox"
            id="mercredi"
            name="mercredi"
            value="Mercredi"
            v-model="workDaysCheckboxes.mercredi"
            disabled
          />
          <label for="mercredi"> Mercredi</label><br />
          <input
            type="checkbox"
            id="jeudi"
            name="jeudi"
            value="Jeudi"
            v-model="workDaysCheckboxes.jeudi"
            disabled
          />
          <label for="jeudi"> Jeudi</label><br />
          <input
            type="checkbox"
            id="vendredi"
            name="vendredi"
            value="Vendredi"
            v-model="workDaysCheckboxes.vendredi"
            disabled
          />
          <label for="vendredi"> Vendredi</label><br />
          <input
            type="checkbox"
            id="samedi"
            name="samedi"
            value="Samedi"
            v-model="workDaysCheckboxes.samedi"
            disabled
          />
          <label for="samedi"> Samedi</label><br />
          <input
            type="checkbox"
            id="dimanche"
            name="dimanche"
            value="Dimanche"
            v-model="workDaysCheckboxes.dimanche"
            disabled
          />
          <label for="dimanche"> Dimanche</label><br /><br />

          <div class="form-control">
            <label>Salaire par heure</label>
            <input
              type="number"
              v-model="internshipOffer.hourlySalary"
              name="hourlySalary"
              min="0"
              max="100"
              disabled
            />
          </div>
          <div class="form-control">
            <label>L'adresse</label>
            <input
              type="text"
              v-model="internshipOffer.address"
              name="address"
              disabled
            />
          </div>
          <div class="form-control">
            <label>La ville</label>
            <input
              type="text"
              v-model="internshipOffer.city"
              name="city"
              disabled
            />
          </div>
          <div class="form-control">
            <label>Le code postal</label>
            <input
              type="text"
              v-model="internshipOffer.postalCode"
              name="postalCode"
              placeholder="Entrez le code postal"
              minLength="6"
              maxLength="6"
              disabled
            />
          </div>
          <div class="form-control">
            <label>Type d'horaire</label>
            <select
              v-model="internshipOffer.workShift"
              name="workShift"
              class="classic"
              disabled
            >
              <option value="DAY">Jour</option>
              <option value="NIGHT">Nuit</option>
              <option value="FLEXIBLE">Flexible</option>
            </select>
          </div>
          <div class="form-control">
            <label>Type d'offre de stage</label>
            <select
              v-model="internshipOffer.workField"
              name="workField"
              class="classic"
              disabled
            >
              <option value="COMPUTER_SCIENCE">Informatique</option>
              <option value="ARCHITECTURE">Architecture</option>
              <option value="NURSING">Infirmier</option>
            </select>
          </div>

          <input type="submit" value="Valider" class="btn btn-block" />
        </div>
      </form>
      <ButtonGoBackToProfile />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import router from "./../router/index";
import ButtonGoBackToProfile from "../components/ButtonGoBackToProfile.vue";

export default {
  components: { ButtonGoBackToProfile },
  name: "InternshipOfferFormValidation",
  inheritAttrs: false,
  data() {
    return {
      user: this.getUserInfo(),
      internshipOffer: [],
      errorMessage: "",
      workDaysCheckboxes: {
        lundi: "",
        mardi: "",
        mercredi: "",
        jeudi: "",
        vendredi: "",
        samedi: "",
        dimanche: "",
      },
    };
  },
  methods: {
    onSubmit(e) {
      e.preventDefault();
      axios
        .post(
          "http://localhost:9090/validate/internshipOffer/" +
            this.internshipOffer.id
        )
        .then(function (response) {
          console.log(response.data);
          router.push("/profile");
        })
        .catch((error) => {
          console.log(error);
          this.errorMessage = "Erreur de validation!";
        });
    },
    logOut() {
      router.push("/");
    },
    getUserInfo: function () {
      console.log(sessionStorage.getItem("user"));
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
    getInternshipOfferInfo() {
      if (sessionStorage.getItem("offer") !== null) {
        var offer = sessionStorage.getItem("offer");
        var offerParsed = JSON.parse(offer);
        console.log(offerParsed);
        this.internshipOffer = offerParsed;
      } else {
        this.logOut();
      }
    },
    formatDates() {
      this.internshipOffer.startDate = this.formatDate(
        this.internshipOffer.startDate
      );
      this.internshipOffer.endDate = this.formatDate(
        this.internshipOffer.endDate
      );
    },
    formatDate(dateString) {
      let date = new Date(dateString);
      let dateFormatted = date.toISOString().split("T")[0];
      return dateFormatted;
    },
    getWorkDays() {
      for (const day in this.workDaysCheckboxes) {
        if (this.internshipOffer.workDays.includes(day)) {
          this.workDaysCheckboxes[day] = true;
        }
      }
    },
  },
  created() {
    this.getUserInfo();
    this.getInternshipOfferInfo();
    this.formatDates();
    this.getWorkDays();
  },
};
</script>

<style>
@import "./../styles/FormStyles.css";
@import "./../styles/GeneralStyles.css";
</style>