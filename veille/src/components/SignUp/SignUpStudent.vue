
<style scoped src="@/styles/App.css"></style>

<template>
    <div>
        <form>
        <div class="cont_tabs_login">
            <h2 class="pt-3">Inscription Étudiant</h2>
        </div>
        <div class="cont_text_inputs">
          <label>Veuillez commencer votre nom d'utilisateur par "E"</label>
          <input
          id="username"
          type="text"
          placeholder="Entrer votre nom d'utilisateur"
          class="input_form_sign d_block active_inp_sign_up"
          required/>
          
          <input
          id="password"
          type="password"
          placeholder="Entrer votre mot de passe"
          class="input_form_sign d_block active_inp_sign_up"
          required/>
          <input
          id="email"
          type="email"
          placeholder="Entrer votre courriel"
          class="input_form_sign d_block active_inp_sign_up"
          required/>
          <input
          id="firstName"
          type="text"
          placeholder="Entrer votre prénom"
          class="input_form_sign d_block active_inp_sign_up"
          required/>
          <input
          id="lastName"
          type="text"
          placeholder="Entrer votre nom de famille"
          class="input_form_sign d_block active_inp_sign_up"
          required/>
          <select id="department"
            class="select_form_sign d_block active_select "
            required>
            <option value="COMPUTER_SCIENCE">Informatique</option>
            <option value="ARCHITECTURE">Architecture</option>
            <option value="NURSING">Infirmier</option>
          </select>
        </div>
        <div className="cont_btn">
          <p>errorMessage</p>
            <button class="btn_sign">Confirmer</button>
        </div>
        </form>
    </div>
</template>