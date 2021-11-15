<template>
  <div class="container">
    <h1 class="title">Ajout d'offre de stage</h1>
    <form @submit="onSubmit" class="add-form">
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
  </div>
</template>

<script>
import axios from "axios";
import router from "./../router/index";

export default {
  name: "InternshipOfferForm",
  inheritAttrs: false,
  data() {
    return {
      fields: {
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
      errorMessage: "",
    };
  },
  methods: {
    onSubmit(e) {
      e.preventDefault();
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
  },
};
</script>

<style>
@import "./../styles/FormStyles.css";
@import "./../styles/GeneralStyles.css";
</style>