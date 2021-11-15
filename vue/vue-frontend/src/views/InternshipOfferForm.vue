<template>
  <div class="container">
    <h1 class="title">Ajout d'offre de stage</h1>
    <form @submit="onSubmit" class="add-form">
      <div v-if="this.user.username.startsWith('G')">
        <div class="form-control">
          <label>Moniteur</label>
          <input
            type="text"
            v-model="fields.monitor"
            name="monitor"
            placeholder="Entrez le nom d'utilisateur du moniteur"
            required
          />
        </div>
      </div>

      <div class="form-control">
        <label>Nom du poste</label>
        <input
          type="text"
          v-model="fields.jobName"
          name="jobName"
          placeholder="Entrez le nom du poste"
          required
        />
      </div>
      <div class="form-control">
        <label>Description du poste</label>
        <input
          type="text"
          v-model="fields.description"
          name="description"
          placeholder="Entrez la description du poste"
          required
        />
      </div>
      <div class="form-control">
        <label>Date de début du stage</label>
        <input
          type="date"
          v-model="fields.startDate"
          name="startDate"
          required
        />
      </div>
      <div class="form-control">
        <label>Date de fin du stage</label>
        <input type="date" v-model="fields.endDate" name="endDate" required />
      </div>
      <div class="form-control">
        <label>Nombre d'heures par semaine</label>
        <input
          type="number"
          v-model="fields.weeklyWorkTime"
          name="weeklyWorkTime"
          min="0"
          max="40"
          required
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
      />
      <label for="lundi"> Lundi</label><br />
      <input
        type="checkbox"
        id="mardi"
        name="mardi"
        value="Mardi"
        v-model="workDaysCheckboxes.mardi"
      />
      <label for="mardi"> Mardi</label><br />
      <input
        type="checkbox"
        id="mercredi"
        name="mercredi"
        value="Mercredi"
        v-model="workDaysCheckboxes.mercredi"
      />
      <label for="mercredi"> Mercredi</label><br />
      <input
        type="checkbox"
        id="jeudi"
        name="jeudi"
        value="Jeudi"
        v-model="workDaysCheckboxes.jeudi"
      />
      <label for="jeudi"> Jeudi</label><br />
      <input
        type="checkbox"
        id="vendredi"
        name="vendredi"
        value="Vendredi"
        v-model="workDaysCheckboxes.vendredi"
      />
      <label for="vendredi"> Vendredi</label><br />
      <input
        type="checkbox"
        id="samedi"
        name="samedi"
        value="Samedi"
        v-model="workDaysCheckboxes.samedi"
      />
      <label for="samedi"> Samedi</label><br />
      <input
        type="checkbox"
        id="dimanche"
        name="dimanche"
        value="Dimanche"
        v-model="workDaysCheckboxes.dimanche"
      />
      <label for="dimanche"> Dimanche</label><br /><br />

      <div class="form-control">
        <label>Salaire par heure</label>
        <input
          type="number"
          v-model="fields.hourlySalary"
          name="hourlySalary"
          min="0"
          max="100"
          required
        />
      </div>
      <div class="form-control">
        <label>Entrez l'adresse</label>
        <input
          type="text"
          v-model="fields.address"
          name="address"
          placeholder="Entrez l'adresse"
          required
        />
      </div>
      <div class="form-control">
        <label>Entrez la ville</label>
        <input
          type="text"
          v-model="fields.city"
          name="city"
          placeholder="Entrez la ville"
          required
        />
      </div>
      <div class="form-control">
        <label>Entrez le code postal</label>
        <input
          type="text"
          v-model="fields.postalCode"
          name="postalCode"
          placeholder="Entrez le code postal"
          minLength="6"
          maxLength="6"
          required
        />
      </div>
      <div class="form-control">
        <label>Type d'horaire</label>
        <select v-model="fields.workShift" name="workShift" class="classic">
          <option value="DAY">Jour</option>
          <option value="NIGHT">Nuit</option>
          <option value="FLEXIBLE">Flexible</option>
        </select>
      </div>
      <div class="form-control">
        <label>Type d'offre de stage</label>
        <select v-model="fields.workField" name="workField" class="classic">
          <option value="COMPUTER_SCIENCE">Informatique</option>
          <option value="ARCHITECTURE">Architecture</option>
          <option value="NURSING">Infirmier</option>
        </select>
      </div>
      <div class="form-control">
        <p>{{ errorMessage }}</p>
      </div>
      <input type="submit" value="Ajouter" class="btn btn-block" />
    </form>
    <ButtonGoBackToProfile />
  </div>
</template>

<script>
import axios from "axios";
import router from "./../router/index";
import ButtonGoBackToProfile from '../components/ButtonGoBackToProfile.vue';

export default {
  components: { ButtonGoBackToProfile },
  name: "InternshipOfferForm",
  inheritAttrs: false,
  data() {
    return {
      fields: {
        monitor: "",
        jobName: "",
        description: "",
        startDate: "",
        endDate: "",
        weeklyWorkTime: "",
        hourlySalary: "",
        workDays: [],
        address: "",
        city: "",
        postalCode: "",
        workShift: "DAY",
        workField: "COMPUTER_SCIENCE",
      },
      workDaysCheckboxes: {
        lundi: "",
        mardi: "",
        mercredi: "",
        jeudi: "",
        vendredi: "",
        samedi: "",
        dimanche: "",
      },
      user: this.getUserInfo(),
      errorMessage: "",
    };
  },
  methods: {
    onSubmit(e) {
      e.preventDefault();

      if (this.user.username.startsWith("M")) {
        this.fields.monitor = this.user.username;
      }
      var daysFromCheckboxes = [];
      for (const day in this.workDaysCheckboxes) {
        console.log(`${day}: ${this.workDaysCheckboxes[day]}`);
        if (this.workDaysCheckboxes[day]) {
          daysFromCheckboxes.push(day);
        }
      }
      this.fields.workDays = daysFromCheckboxes;
      axios
        .post("http://localhost:9090/add/internshipOffer", this.fields)
        .then(function (response) {
          console.log(response.data);
          router.push("/profile");
        })
        .catch((error) => {
          console.log(error);
          this.errorMessage = "Erreur de création!";
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
        if (
          !(
            viewName.username.startsWith("G") ||
            viewName.username.startsWith("M")
          )
        ) {
          this.logOut();
        } else {
          return viewName;
        }
      } else {
        this.logOut();
      }
    },
  },
  created: function () {
    this.getUserInfo();
  },
};
</script>

<style>
@import "./../styles/FormStyles.css";
@import "./../styles/GeneralStyles.css";
</style>