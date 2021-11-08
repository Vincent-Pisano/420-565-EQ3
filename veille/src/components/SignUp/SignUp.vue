<script>
import SignUpStudent from './SignUpStudent.vue'
import SignUpMonitor from './SignUpMonitor.vue'
import SignUpSupervisor from './SignUpSupervisor.vue'
import NavBar from '@/components/Navbar/NavBar.vue'

export default{
        name: 'signUp',
        data: function(){
            return { currentSignUp: 'SignUpStudent'}
        },
        components: {
            SignUpStudent,
            SignUpMonitor,
            SignUpSupervisor,
            NavBar
        },
        methods: {
            getCurrentSignUp(){
                return this.currentSignUp
            },
            setCurrentSignUp(newValue){
                this.currentSignUp = newValue
            },
            handleClick(newChoice){
                if(newChoice === "student"){
                    this.setCurrentSignUp('SignUpStudent')
                    return this.currentSignUp
                }
                if(newChoice === "monitor"){
                    this.setCurrentSignUp('SignUpMonitor')
                    return this.currentSignUp
                }
                if(newChoice === "supervisor"){
                    this.setCurrentSignUp('SignUpSupervisor')
                    return this.currentSignUp
                }
            }
        },
        mounted : function(){
            if(JSON.parse(sessionStorage.getItem("user"))!== undefined){
                sessionStorage.removeItem("user");
            }
        }
    }

</script>

<style scoped src="@/styles/Form.css"></style>
<style scoped src="@/styles/App.css"></style>

<template>
    <NavBar/>
    <div id="signUp" class="cont_principal">
      <div class="cont_central">
        <h2 class="cont_title_form">Inscription</h2>
          <div class = "link_inscriptions">
              <button :class = "getCurrentSignUp()==='SignUpStudent' ? 'btn_link_selected': 'btn_link'" v-on:click="handleClick('student')">Étudiant</button>
              <button :class = "getCurrentSignUp()==='SignUpMonitor' ? 'btn_link_selected': 'btn_link'" v-on:click="handleClick('monitor')">Moniteur</button>
              <button :class = "getCurrentSignUp()==='SignUpSupervisor' ? 'btn_link_selected': 'btn_link'" v-on:click="handleClick('supervisor')">Superviseur</button>
            </div>
            <div class="cont_login">
                <component v-bind:is="getCurrentSignUp()"></component>
            </div>
        </div>
    </div>
</template>
