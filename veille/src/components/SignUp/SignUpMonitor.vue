<script>
import axios from "axios"
import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/components/Home.vue'
import router from '@/router'
export default{
        name: 'signUpMonitor',
        data: function(){
            return { monitor: {
              enterpriseName: "",
              jobTitle: "",
              username: "",
              password: "",
              email: "",
              firstName: "",
              lastName: "",
              },
              errorMessage: ""
            }
        },
        methods: {
            onSubmit(){
              if (!this.monitor.username.startsWith("M")) {
                this.errorMessage= "Le nom d'utilisateur doit commencer par 'M'.";
                return;
              }
              axios
                .post("http://localhost:9090/signUp/monitor", this.monitor)
                .then((response) => {
                  var user = response.data
                  sessionStorage.setItem("user", JSON.stringify(user));
                  createRouter({
                    history: createWebHistory,
                    routes: [{path: `/home`, component: Home}]
                  })
                  router.push({path:`/home`})
                })
                .catch((error) => {
                  this.errorMessage = "Le nom d'utilisateur ou le courriel existe déjà.";
                  console.log(error)
                });
            }
        },
        computed: {
          id() {
            return this.$route.params.id
          }
        }
    }

</script>


<style scoped src="@/styles/App.css"></style>

<template>
    <div id  = "signUpMonitor" class="form-group">
        <div class="cont_tabs_login">
            <h2 class="pt-3">Inscription Moniteur</h2>
        </div>
        <div class="cont_text_inputs">
          <label class="discret">Veuillez commencer votre nom d'utilisateur par "M"</label>
          <input
          id="username"
          type="text"
          placeholder="Entrer votre nom d'utilisateur"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="monitor.username"
          required/>
          
          <input
          id="password"
          type="password"
          placeholder="Entrer votre mot de passe"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="monitor.password"
          required/>
          <input
          id="email"
          type="email"
          placeholder="Entrer votre courriel"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="monitor.email"
          required/>
          <input
          id="firstName"
          type="text"
          placeholder="Entrer votre prénom"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="monitor.firstName"
          required/>
          <input
          id="lastName"
          type="text"
          placeholder="Entrer votre nom de famille"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="monitor.lastName"
          required/>
          <input
          id="jobTitle"
          type="text"
          placeholder="Entrer votre nom de poste"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="monitor.jobTitle"
          required/>
          <input
          id="enterpriseName"
          type="text"
          placeholder="Entrer le nom de votre entreprise"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="monitor.enterpriseName"
          required/>
        </div>
        <div className="cont_btn">
          <p>{{errorMessage}}</p>
            <button class="btn_sign" v-on:click="onSubmit()">Confirmer</button>
        </div>
    </div>
</template>