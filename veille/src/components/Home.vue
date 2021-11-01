<script>
export default{
        name: 'home',
        data: function(){
            return { 
                user: JSON.parse(sessionStorage.getItem("user")),
                dateFormatted:""
            }
        },
        methods: {
            formatDate(dateString) {
                let date = new Date(dateString);
                this.dateFormatted = date.toISOString().split("T")[0];
                return this.dateFormatted;
            }
        },
        mounted: function(){
            if(this.user !== null){
                this.dateFormatted = this.formatDate(this.user.creationDate)
            }
        }
    }

</script>

<style scoped src="@/styles/Home.css"></style>
<style scoped src="@/styles/App.css"></style>

<template>
    <div id="home" class="cont_home">
        <div v-if="this.user !== null" class="cont_central">
            <h4>Nom d'utilisateur: {{this.user.username}}</h4>
            <h5>Prénom: {{this.user.firstName}}</h5>
            <h5>Nom: {{this.user.lastName}}</h5>
            <h5>Adresse courriel: {{this.user.email}}</h5>
            <h5>Depuis {{this.dateFormatted}}</h5>
        </div>
        <div v-else class="cont_central">
             <h3>Veuillez vous inscrire</h3>
        </div>
    </div>
</template>
