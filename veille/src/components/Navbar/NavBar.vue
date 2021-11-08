<script>
export default{
        name: 'navbar',
        data: function(){
            return{
                user: JSON.parse(sessionStorage.getItem("user"))
            }
        },
        methods: {
            checkIfLogin() {
                if(this.user !== null){
                    return true
                } else {
                    return false
                }
            },
            logout(){
                if(JSON.parse(sessionStorage.getItem("user"))!== undefined){
                    sessionStorage.removeItem("user");
                }
            }
        }
    }

</script>

<style scoped src="@/styles/App.css"></style>

<template>
    <nav id="navbar" class="nav-header">
    <h1>GDS</h1>
      <ul v-if="checkIfLogin() === true" class="nav-link-header">
            <router-link to="/home" class="nav-links-header" v-on:click="logout()">{{this.user.username}}</router-link>
            <router-link to="/" class="nav-links-header" v-on:click="logout()">Déconnexion</router-link>
        </ul>
        <ul v-else  class="nav-link-header">
            <router-link to="/signUp" class="nav-links-header">Inscriptions</router-link>
            <router-link to="/" class="nav-links-header">Connexion</router-link>
        </ul>
    </nav>
</template>