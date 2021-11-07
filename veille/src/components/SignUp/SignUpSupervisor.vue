<script>
import axios from "axios"
import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/components/Home.vue'
import router from '@/router'
export default{
        name: 'signUpSupervisor',
        data: function(){
            return { supervisor: {
              department: "COMPUTER_SCIENCE",
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
              if (!this.supervisor.username.startsWith("S")) {
                this.errorMessage= "Le nom d'utilisateur doit commencer par 'S'.";
                return;
              }
              axios
                .post("http://localhost:9090/signUp/supervisor", this.supervisor)
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
    <div id  = "signUpSupervisor" class="form-group">
        <div class="cont_tabs_login">
            <h2 class="pt-3">Inscription Superviseur</h2>
        </div>
        <div class="cont_text_inputs">
          <label class="discret">Veuillez commencer votre nom d'utilisateur par "S"</label>
          <input
          id="username"
          type="text"
          placeholder="Entrer votre nom d'utilisateur"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="supervisor.username"
          required/>
          
          <input
          id="password"
          type="password"
          placeholder="Entrer votre mot de passe"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="supervisor.password"
          required/>
          <input
          id="email"
          type="email"
          placeholder="Entrer votre courriel"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="supervisor.email"
          required/>
          <input
          id="firstName"
          type="text"
          placeholder="Entrer votre prénom"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="supervisor.firstName"
          required/>
          <input
          id="lastName"
          type="text"
          placeholder="Entrer votre nom de famille"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="supervisor.lastName"
          required/>
          <select id="department"
            class="select_form_sign d_block active_select "
            v-model.lazy="supervisor.department"
            required>
            <option value="COMPUTER_SCIENCE">Informatique</option>
            <option value="ARCHITECTURE">Architecture</option>
            <option value="NURSING">Infirmier</option>
          </select>
        </div>
        <div className="cont_btn">
          <p>{{errorMessage}}</p>
            <button class="btn_sign" v-on:click="onSubmit()">Confirmer</button>
        </div>
    </div>
</template>