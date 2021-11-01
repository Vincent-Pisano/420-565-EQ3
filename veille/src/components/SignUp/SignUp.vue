<script>
import SignUpStudent from './SignUpStudent.vue'
export default{
        name: 'signUp',
        data: function(){
            return { currentSignUp: 'SignUpStudent'}
        },
        components: {
            SignUpStudent
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
    <div id="signUp" class="cont_principal">
      <div class="cont_central">
        <h2 class="cont_title_form">Inscription</h2>
          <div>
              <button :class = "getCurrentSignUp()==='SignUpStudent' ? 'btn_link_selected': 'btn_link'" v-on:click="handleClick('student')">Étudiant</button>
              <component v-bind:is="getCurrentSignUp()"></component>
            </div>
        </div>
    </div>
</template>
