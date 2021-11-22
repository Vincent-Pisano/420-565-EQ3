<template>
    <button @click="goToForm()" class="btn btn1">Appliquer à une offre</button>
    <br />
</template>

<script>
import router from "./../router/index";

export default {
    name: 'ButtonToApplyToInternshipOffer',
    methods: {
        goToForm() {
            router.push("/applyInternshipOfferForm")
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
  background-color: #4CAF50;
  color: white;
  border: 2px solid #4CAF50;
}
</style>