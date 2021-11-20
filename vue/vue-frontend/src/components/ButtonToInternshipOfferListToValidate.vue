<template>
    <button @click="goToList()" class="btn btn1">Valider une offre</button>
    <br />
</template>

<script>
import router from "./../router/index";

export default {
    name: 'ButtonToInternshipOfferListToValidate',
    methods: {
        goToList() {
            router.push("/internshipOfferListToValidate")
        },
    }
}
</script>

<style scoped>
.btn {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 8px 16px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 35px;
  margin: 0px;
  transition-duration: 0.4s;
  cursor: pointer;
}

.btn1:hover  {
  background-color: white; 
  color: black; 
}

.btn1{
  background-color: #008CBA;
  color: white;
  border: 2px solid #008CBA;
}
</style>