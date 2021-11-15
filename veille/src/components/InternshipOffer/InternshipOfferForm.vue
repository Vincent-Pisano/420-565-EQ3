<script>
import NavBar from '@/components/Navbar/NavBar.vue'
import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/components/Home.vue'
import router from '@/router'
import axios from "axios"
export default{
        name: 'internshipOffer',
        data: function(){
            return { internshipOffer: {
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
                monitor: {},
              },
              user: JSON.parse(sessionStorage.getItem("user")),
              monitorUsername: "",
              errorMessage: ""
            }
        },
        components: {
            NavBar
        },
        methods: {
            onSubmit() {
                if (this.internshipOffer.workDays.length > 0) {
                    if (this.user.username.charAt(0)==="M"){
                        this.internshipOffer.monitor = this.user
                    } else if (this.user.username.charAt(0)==="G") {
                        axios
                            .get(`http://localhost:9090/get/monitor/${this.monitorUsername}`)
                            .then((response) => {
                            this.internshipOffer.monitor = response.data;
                            })
                            .catch((error) => {
                            console.log(error)
                            this.errorMessage = "Erreur! Le moniteur est inexistant"
                        });
                    }
                    if (this.internshipOffer.monitor.username !== undefined){
                        this.formatDates()
                        axios
                            .post("http://localhost:9090/save/internshipOffer", this.internshipOffer)
                            .then((response) => {
                                this.errorMessage = "L'offre de stage a été sauvegardé"
                                console.log(response.data);
                                createRouter({
                                    history: createWebHistory,
                                    routes: [{path: `/home`, component: Home}]
                                })
                                router.push({path:`/home`})
                            }
                        ).catch((error) => {
                            console.log(error)
                            this.errorMessage = "Erreur! L'offre de stage est invalide"
                        });
                    } else {
                        this.errorMessage = "Erreur! Le moniteur est inexistant"
                    }
                    } else {
                        this.errorMessage = "Veuillez entrez au moins une journée de travail"
                    }
                },
                formatDates() {
                    if(this.internshipOffer){
                        this.internshipOffer.startDate = this.formatDate(this.internshipOffer.startDate)
                        this.internshipOffer.endDate = this.formatDate(this.internshipOffer.endDate)
                    }
                },
                formatDate(dateString) {
                    let date = new Date(dateString)
                    let dateFormatted = date.toISOString().split('T')[0]
                    return dateFormatted;
                }
        }
    }

</script>

<style scoped src="@/styles/Form.css"></style>
<style scoped src="@/styles/App.css"></style>

<template>
    <NavBar/>
    <div id  = "internshipOfferForm" class="form-group">
        <div class="cont_tabs_login">
            <h2 class="pt-3">Offre de Stage</h2>
        </div>
        <div class="cont_text_inputs">
          <input
          id="jobName"
          type="text"
          placeholder="Entrer le nom du poste de travail"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="internshipOffer.jobName"
          required/>
          <input
          v-if="this.user.username.charAt(0) === 'G'"
          id="monitorUsername"
          type="text"
          placeholder="Entrer le nom d'utilisateur du moniteur"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="monitorUsername"
          required/>
          <input
          id="description"
          type="text"
          placeholder="Entrer une description du poste"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="internshipOffer.description"
          required/>
          <div>
            <p class="labelFields">La date de début du stage</p>
          </div>
          <input
          id="startDate"
          type="date"
          placeholder="Entrer la date de début du stage"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="internshipOffer.startDate"
          required/>
          <div>
            <p class="labelFields">La date de fin du stage</p>
          </div>
          <input
          id="endDate"
          type="date"
          placeholder="Entrer la date de fin du stage"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="internshipOffer.endDate"
          required/>
          <input
          id="weeklyWorkTime"
          type="number"
          step=".01"
          min="0"
          placeholder="Entrer la quantité d'heures par semaine"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="internshipOffer.weeklyWorkTime"
          required/>
          <input
          id="hourlySalary"
          type="number"
          step=".01"
          min="0"
          placeholder="Entrer le salaire par heure"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="internshipOffer.hourlySalary"
          required/>
          <div>
            <p className="labelFields_checkbox">Les jours de travail</p>
          </div>
          <div class="workDays checkboxes">
            <label htmlFor="Monday">
                <input type="checkbox" id="Monday"
                value="Monday"
                v-model.lazy="internshipOffer.workDays"/>
                <span>Lundi</span></label>
            <br /> 
            <label htmlFor="Tuesday">
                <input type="checkbox" id="Tuesday"
                value="Tuesday"
                v-model.lazy="internshipOffer.workDays"/>
                <span>Mardi</span></label>
            <br />
            <label htmlFor="Wednesday">
                <input type="checkbox" id="Wednesday"
                value="Wednesday"
                v-model.lazy="internshipOffer.workDays"/>
                <span>Mercredi</span></label>
            <br />
            <label htmlFor="Thursday">
                <input type="checkbox" id="Thursday"
                value="Thursday"
                v-model.lazy="internshipOffer.workDays"/>
                <span>Jeudi</span></label>
            <br />
            <label htmlFor="Friday">
                <input type="checkbox" id="Friday"
                value="Friday"
                v-model.lazy="internshipOffer.workDays"/>
                <span>Vendredi</span></label>
            <br />
            <label htmlFor="Saturday">
                <input type="checkbox" id="Saturday"
                value="Saturday"
                v-model.lazy="internshipOffer.workDays"/>
                <span>Samedi</span></label>
            <br />
            <label htmlFor="Sunday">
                <input type="checkbox" id="Sunday"
                value="Sunday"
                v-model.lazy="internshipOffer.workDays"/>
                <span>Dimanche</span></label>
            <br />
          </div>
          <input
          id="address"
          type="text"
          placeholder="Entrer l'adresse de l'entreprise"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="internshipOffer.address"
          required/>
          <input
          id="city"
          type="text"
          placeholder="Entrer la ville de l'entreprise"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="internshipOffer.city"
          required/>
          <input
          id="postalCode"
          type="text"
          minLength="6"
          maxLength="6"
          placeholder="Entrer le code postal de l'entreprise"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="internshipOffer.postalCode"
          required/>
          <div>
            <p className="labelFields">Le type d'horaire</p>
          </div>
          <select id="workShift"
            class="select_form_sign d_block active_select "
            v-model.lazy="internshipOffer.workShift"
            required>
            <option value="DAY">Jour</option>
            <option value="NIGHT">Nuit</option>
            <option value="FLEXIBLE">Flexible</option>
          </select>
          <div>
            <p className="labelFields">Le type de l'offre de stage</p>
          </div>
          <select id="workField"
            class="select_form_sign d_block active_select "
            v-model.lazy="internshipOffer.workField"
            required>
            <option value="COMPUTER_SCIENCE">Informatique</option>
            <option value="ARCHITECTURE">Architecture</option>
            <option value="NURSING">Infirmier</option>
          </select>
        </div>
        <div class="cont_btn">
          <p>{{errorMessage}}</p>
            <button class="btn_sign" v-on:click="onSubmit()">Confirmer</button>
        </div>
    </div>
</template>
