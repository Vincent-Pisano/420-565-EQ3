<script>
import axios from "axios"
import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/components/Home.vue'
import NavBar from '@/components/Navbar/NavBar.vue'
import router from '@/router'
export default{
        name: 'login',
        data: function(){
            return { user: {
              username: "",
              password: "",
              },
              errorMessage: "",
              type:""
            }
        },
        components:{
            NavBar
        },
        methods: {
            onSubmit(){
              if (
                !(
                this.user.username.startsWith("E") ||
                this.user.username.startsWith("S") ||
                this.user.username.startsWith("M") ||
                this.user.username.startsWith("G")
                )
            ) {
                this.errorMessage = "Les noms d'utilisateurs commencent par 'E', 'S', 'M' ou 'G'";
                return;
            }


            switch (this.user.username.charAt(0)) {
            case "E":
                this.type = "student";
                break;
            case "S":
                this.type = "supervisor";
                break;
            case "M":
                this.type = "monitor";
                break;
            case "G":
                this.type = "internshipManager";
                break;
            default:
                this.type = "error";
            }

              axios
                .get(`http://localhost:9090/login/${this.type}/${this.user.username}/${this.user.password}`)
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
                  this.errorMessage = "Le nom d'utilisateur ou le mot de passe est incorrect.";
                  console.log(error)
                });
            }
        },
        mounted : function(){
            if(JSON.parse(sessionStorage.getItem("user"))!== undefined){
                sessionStorage.removeItem("user");
            }
        },
        computed: {
          id() {
            return this.$route.params.id
          }
        }
    }
</script>

<style scoped src="@/styles/Form.css"></style>
<style scoped src="@/styles/App.css"></style>

<template>
    <NavBar/>
    <div id="login" class="cont_principal">
      <div class="cont_central">
        <div>
            <h2 class="cont_title_form">Connexion</h2>
        </div>
        <div class="cont_inputs cont_text_inputs">
          <label class="discret">Veuillez rentrez vos informations d'utilisations</label>
            <input
            id="username"
            type="text"
            placeholder="Entrer votre nom d'utilisateur"
            class="input_form_sign d_block active_inp_sign_up"
            v-model.lazy="user.username"
            required/>
            
            <input
            id="password"
            type="password"
            placeholder="Entrer votre mot de passe"
            class="input_form_sign d_block active_inp_sign_up"
            v-model.lazy="user.password"
            required/>
            </div>
            <div className="cont_btn">
                <p>{{errorMessage}}</p>
                <button class="btn_sign" v-on:click="onSubmit()">Confirmer</button>
            </div>
      </div>
    </div>
</template>
