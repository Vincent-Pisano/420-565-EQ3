<template>
  <div class="container">
    <h1 class="title">Page de connection</h1>
    <form @submit="onSubmit" class="add-form">
      <div class="form-control">
        <label>Nom d'utilisateur</label>
        <input
          type="text"
          v-model="username"
          name="username"
          placeholder="Entrez votre nom d'utilisateur"
        />
      </div>
      <div class="form-control">
        <label>Mot de passe</label>
        <input
          type="password"
          v-model="pw"
          name="pw"
          placeholder="Entrez votre mot de passe"
        />
      </div>
      <input type="submit" value="Save Task" class="btn btn-block" />
    </form>
  </div>
</template>

<script>
import axios from "axios";

function getUserType(username) {
  if (username.startsWith("E")) {
    return "student";
  } else if (username.startsWith("M")) {
    return "monitor";
  } else if (username.startsWith("S")) {
    return "supervisor";
  } else if (username.startsWith("G")) {
    return "internshipManager";
  }
}

export default {
  name: "Login",
  inheritAttrs: false,
  data() {
    return {
      username: "",
      pw: "",
    };
  },
  methods: {
    onSubmit(e) {
      e.preventDefault();
      if (!this.username || !this.pw) {
        alert("SVP remplissez tous les champs");
        return;
      } else {
        var userType = getUserType(this.username);
        axios
          .get(
            "http://localhost:9090/login/" +
              userType +
              "/" +
              this.username +
              "/" +
              this.pw
          )
          .then(function (response) {
            console.log(response.data);
          })
          .catch((error) => console.log(error));
      }
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 500px;
  margin: 30px auto;
  overflow: auto;
  min-height: 300px;
  border: 1px solid steelblue;
  padding: 30px;
  border-radius: 5px;
}

.title {
  text-align: center;
}

.add-form {
  margin-bottom: 40px;
}
.form-control {
  margin: 20px 0;
}
.form-control label {
  display: block;
}
.form-control input {
  width: 100%;
  height: 40px;
  margin: 5px;
  padding: 3px 7px;
  font-size: 17px;
}
.form-control-check {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.form-control-check label {
  flex: 1;
}
.form-control-check input {
  flex: 2;
  height: 20px;
}
</style>