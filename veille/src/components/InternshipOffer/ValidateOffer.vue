<script>
import NavBar from '@/components/Navbar/NavBar.vue'
import InternshipOfferList from './InternshipOfferList.vue'
import { createRouter, createWebHistory } from 'vue-router'
import router from '@/router'
import axios from "axios"
export default{
        name: 'validateOffer',
        props:['offer'],
        data: function(){
            return {
                currentOffer: JSON.parse(this.offer),
                errorMessage:""
            }
        },
        components: {
            NavBar
        },
        methods: {
            handleClick(){
                axios
                .post(
                    `http://localhost:9090/save/internshipOffer/validate/${this.currentOffer.id}`
                )
                .then((response) => {
                    console.log(response.id)
                    createRouter({
                        history: createWebHistory,
                        routes: [{path: `/listInternshipOffer`, component: InternshipOfferList}]
                    })
                    router.push({path:`/listInternshipOffer`})
                })
                .catch((error) => {
                    console.log(error)
                    this.errorMessage = "Erreur lors de la validation"
                });
            },
            formatDates() {
                if(this.currentOffer){
                    this.currentOffer.startDate = this.formatDate(this.currentOffer.startDate)
                    this.currentOffer.endDate = this.formatDate(this.currentOffer.endDate)
                }
            },
            formatDate(dateString) {
                let date = new Date(dateString)
                let dateFormatted = date.toISOString().split('T')[0]
                return dateFormatted;
            }
        },
        mounted: function(){
            this.formatDates()
        }
    }

</script>

<style scoped src="@/styles/App.css"></style>
<style scoped src="@/styles/List.css"></style>

<template>
    <NavBar/>
    <div id  = "currentOfferForm" class="form-group">
        <div class="cont_tabs_login">
            <h2 class="pt-3">Validation d'Offre de Stage</h2>
        </div>
        <div class="cont_text_inputs">
          <div>
            <p class="labelFields">Le nom du poste de travail</p>
          </div>
          <input
          id="jobName"
          type="text"
          placeholder="Entrer le nom du poste de travail"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="currentOffer.jobName"
          disabled/>
          <div>
            <p class="labelFields">La description du poste</p>
          </div>
          <input
          id="description"
          type="text"
          placeholder="Entrer une description du poste"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="currentOffer.description"
          disabled/>
          <div>
            <p class="labelFields">La date de début du stage</p>
          </div>
          <input
          id="startDate"
          type="date"
          placeholder="Entrer la date de début du stage"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="currentOffer.startDate"
          disabled/>
          <div>
            <p class="labelFields">La date de fin du stage</p>
          </div>
          <input
          id="endDate"
          type="date"
          placeholder="Entrer la date de fin du stage"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="currentOffer.endDate"
          disabled/>
          <div>
            <p class="labelFields">La quantité d'heures par semaine</p>
          </div>
          <input
          id="weeklyWorkTime"
          type="number"
          step=".01"
          min="0"
          placeholder="Entrer la quantité d'heures par semaine"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="currentOffer.weeklyWorkTime"
          disabled/>
          <div>
            <p class="labelFields">Le salaire par heure</p>
          </div>
          <input
          id="hourlySalary"
          type="number"
          step=".01"
          min="0"
          placeholder="Entrer le salaire par heure"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="currentOffer.hourlySalary"
          disabled/>
          <div>
            <p class="labelFields_checkbox">Les jours de travail</p>
          </div>
          <div class="workDays checkboxes">
            <label htmlFor="Monday">
                <input type="checkbox" id="Monday"
                value="Monday"
                v-model.lazy="currentOffer.workDays" disabled/>
                <span>Lundi</span></label>
            <br /> 
            <label htmlFor="Tuesday">
                <input type="checkbox" id="Tuesday"
                value="Tuesday"
                v-model.lazy="currentOffer.workDays" disabled/>
                <span>Mardi</span></label>
            <br />
            <label htmlFor="Wednesday">
                <input type="checkbox" id="Wednesday"
                value="Wednesday"
                v-model.lazy="currentOffer.workDays" disabled/>
                <span>Mercredi</span></label>
            <br />
            <label htmlFor="Thursday">
                <input type="checkbox" id="Thursday"
                value="Thursday"
                v-model.lazy="currentOffer.workDays" disabled/>
                <span>Jeudi</span></label>
            <br />
            <label htmlFor="Friday">
                <input type="checkbox" id="Friday"
                value="Friday"
                v-model.lazy="currentOffer.workDays" disabled/>
                <span>Vendredi</span></label>
            <br />
            <label htmlFor="Saturday">
                <input type="checkbox" id="Saturday"
                value="Saturday"
                v-model.lazy="currentOffer.workDays" disabled/>
                <span>Samedi</span></label>
            <br />
            <label htmlFor="Sunday">
                <input type="checkbox" id="Sunday"
                value="Sunday"
                v-model.lazy="currentOffer.workDays" disabled/>
                <span>Dimanche</span></label>
            <br />
          </div>
          <div>
            <p class="labelFields">L'adresse de l'entreprise</p>
          </div>
          <input
          id="address"
          type="text"
          placeholder="Entrer l'adresse de l'entreprise"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="currentOffer.address"
          disabled/>
          <div>
            <p class="labelFields">La ville de l'entreprise</p>
          </div>
          <input
          id="city"
          type="text"
          placeholder="Entrer la ville de l'entreprise"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="currentOffer.city"
          disabled/>
          <div>
            <p class="labelFields">Le code postal de l'entreprise</p>
          </div>
          <input
          id="postalCode"
          type="text"
          minLength="6"
          maxLength="6"
          placeholder="Entrer le code postal de l'entreprise"
          class="input_form_sign d_block active_inp_form"
          v-model.lazy="currentOffer.postalCode"
          disabled/>
          <div>
            <p class="labelFields">Le type d'horaire</p>
          </div>
          <select id="workShift"
            class="select_form_sign d_block active_select "
            v-model.lazy="currentOffer.workShift"
            disabled>
            <option value="DAY">Jour</option>
            <option value="NIGHT">Nuit</option>
            <option value="FLEXIBLE">Flexible</option>
          </select>
          <div>
            <p class="labelFields">Le type de l'offre de stage</p>
          </div>
          <select id="workField"
            class="select_form_sign d_block active_select "
            v-model.lazy="currentOffer.workField"
            disabled>
            <option value="COMPUTER_SCIENCE">Informatique</option>
            <option value="ARCHITECTURE">Architecture</option>
            <option value="NURSING">Infirmier</option>
          </select>
        </div>
        <div class="cont_btn">
          <p>{{errorMessage}}</p>
            <button class="btn_sign" v-on:click="handleClick()">Valider</button>
        </div>
    </div>
</template>
