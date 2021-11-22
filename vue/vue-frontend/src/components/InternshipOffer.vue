<template>
  <div>
    <button @click="goValidate()" class="btn btn1">
      {{ internshipOffer.jobName }}, {{ internshipOffer.description }}:
      {{ internshipOffer.city }}
    </button>
  </div>
  <br />
</template>

<script>
import router from "./../router/index";

export default {
  name: "InternshipOffer",
  props: {
    internshipOffer: Object,
    username: String,
  },
  methods: {
    goValidate() {
      sessionStorage.setItem("offer", JSON.stringify(this.internshipOffer));
      if(this.username.startsWith("G")){
        router.push("/internshipOfferValidation");
      }
      else if(this.username.startsWith("E")){
        router.push("/internshipOfferValidation");
      }
    },
  },
};
</script>

<style scoped>
.btn {
  background-color: #4caf50; /* Green */
  border: none;
  color: white;
  padding: 8px 16px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 20px;
  margin: 0px;
  transition-duration: 0.4s;
  cursor: pointer;
}

.btn1:hover {
  background-color: white;
  color: black;
}

.btn1 {
  background-color: #f44336;
  color: white;
  border: 2px solid #f44336;
}
</style>