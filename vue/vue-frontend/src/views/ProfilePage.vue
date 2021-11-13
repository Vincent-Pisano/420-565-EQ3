<template>
  <div class="container">
    <div class="card">
      <img src="img.jpg"  style="width: 100%" />
      <h1>{{this.user.username}}</h1>
      <h2>{{this.user.firstName}} {{this.user.lastName}}</h2>
      <p class="title">{{this.user.email}}</p>
      <p>Harvard University</p>
      <p><button>Déconnecter</button></p>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProfilePage",
  inheritAttrs: false,
  methods: {
    getUserInfo: function () {
      var user = sessionStorage.getItem("user");
      var viewName = JSON.parse(user);
      console.log(viewName);
      return viewName;
    },
  },
  created: function () {
    this.getUserInfo();
  },
  data() {
    return {
      user: this.getUserInfo(),
    };
  },
};
</script>

<style>
@import "./../styles/GeneralStyles.css";

.card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  max-width: 300px;
  margin: auto;
  text-align: center;
}

.title {
  color: grey;
  font-size: 18px;
}

button {
  border: none;
  outline: 0;
  display: inline-block;
  padding: 8px;
  color: white;
  background-color: #000;
  text-align: center;
  cursor: pointer;
  width: 100%;
  font-size: 18px;
}

a {
  text-decoration: none;
  font-size: 22px;
  color: black;
}

button:hover, a:hover {
  opacity: 0.7;
}
</style>