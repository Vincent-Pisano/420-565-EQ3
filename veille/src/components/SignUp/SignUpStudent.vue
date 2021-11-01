<script>
import axios from "axios"
import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/components/Home.vue'
import router from '@/router'
export default{
        name: 'signUpStudent',
        data: function(){
            return { student: {
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
              if (!this.student.username.startsWith("E")) {
                this.errorMessage= "Le nom d'utilisateur doit commencer par 'E'.";
                return;
              }
              axios
                .post("http://localhost:9090/signUp/student", this.student)
                .then((response) => {
                  var user = response.data
                  sessionStorage.setItem("user", JSON.stringify(user));
                  createRouter({
                    history: createWebHistory,
                    routes: [{path: `/`, component: Home}]
                  })
                  router.push({path:`/`})
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
    <div id  = "signUpStudent" class="form-group">
        <div class="cont_tabs_login">
            <h2 class="pt-3">Inscription Étudiant</h2>
        </div>
        <div class="cont_text_inputs">
          <label>Veuillez commencer votre nom d'utilisateur par "E"</label>
          <input
          id="username"
          type="text"
          placeholder="Entrer votre nom d'utilisateur"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="student.username"
          required/>
          
          <input
          id="password"
          type="password"
          placeholder="Entrer votre mot de passe"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="student.password"
          required/>
          <input
          id="email"
          type="email"
          placeholder="Entrer votre courriel"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="student.email"
          required/>
          <input
          id="firstName"
          type="text"
          placeholder="Entrer votre prénom"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="student.firstName"
          required/>
          <input
          id="lastName"
          type="text"
          placeholder="Entrer votre nom de famille"
          class="input_form_sign d_block active_inp_sign_up"
          v-model.lazy="student.lastName"
          required/>
          <select id="department"
            class="select_form_sign d_block active_select "
            v-model.lazy="student.department"
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