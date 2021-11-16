<script>
import NavBar from '@/components/Navbar/NavBar.vue'
import router from '@/router'
import axios from "axios"
export default{
        name: 'CV',
        data: function(){
            return { 
                user: JSON.parse(sessionStorage.getItem("user")),
                errorMessage:'',
                document:undefined
            }
        },
        components:{
            NavBar
        },
        methods: {
            assignDocument(event) {
                this.document = event.target.files[0]
            },
            saveDocument(){
                if (
                    this.document !== undefined &&
                    this.document.type === "application/pdf" &&
                    this.user.cvlist.length < 10
                    ) {
                    let formData = new FormData();
                    formData.append("document", this.document);
                    axios
                        .post(`http://localhost:9090/save/CV/${this.user.id}/`, formData)
                        .then((response) => {
                            this.user = response.data;
                            sessionStorage.setItem("user", JSON.stringify(this.user));
                            router.push({path:`/cv`})
                        })
                        .catch((error) => {
                            console.log(error)
                            this.errorMessage = "Erreur d'envoi de fichier";
                        });
                    } else {
                    if (!(this.user.cvlist.length < 10)) {
                        this.errorMessage = "Erreur! Taille maximale de fichiers atteinte(10)";
                    } else {
                        this.errorMessage = "Erreur! Aucun fichier est sélectionné";
                    }
                }
            },
            deleteCV(cv){
            axios
                .delete(`http://localhost:9090/delete/CV/${this.user.id}/${cv.id}`)
                .then((response) => {
                    this.user = response.data;
                    sessionStorage.setItem("user", JSON.stringify(this.user));
                    this.errorMessage = "Le fichier a été supprimé"
                    router.push({path:`/cv`})
                })
                .catch((error) => {
                    console.log(error
                    )
                    this.errorMessage = "Erreur! Le fichier n'a pas été supprimé"
                });
            },
            formatDate(dateString){
                let date = new Date(dateString);
                return date.toISOString().split("T")[0];
            }
        }
    }

</script>

<style scoped src="@/styles/List.css"></style>
<style scoped src="@/styles/App.css"></style>
<style scoped src="@/styles/CV.css"></style>

<template>
    <NavBar/>
    <div class="cont_list_cv">
      <div class="cont_list_centrar">
        <h2 class="cont_title_form">Liste de vos CV</h2>
        <table responsive="md" striped bordered hover variant="dark">
        <thead>
            <tr>
            <th>Nom de fichier</th>
            <th>Date de dépôt</th>
            <th>Téléchargements</th>
            <th>Suppressions</th>
            </tr>
        </thead>
        <tbody>
        <tr v-if="this.user.cvlist.length === 0">
          <td colSpan="5">Pas de CV</td>
        </tr>
        <tr v-for="(cv, x) in this.user.cvlist" :key="x">
            <td>
                {{cv.pdfdocument.name}}
            </td>
            <td>
                {{formatDate(cv.depositDate)}}
            </td>
            <td>
                <a
                class="btn btn-success btn-sm"
                download
                :href="'http://localhost:9090/get/CV/document/'+this.user.id+'/'+ cv.id"
                >
                Télécharger
                </a>
            </td>
            <td>
                <button v-on:click="deleteCV(cv)">
                    Supprimer
                </button>
            </td>
        </tr>
        </tbody>
        </table>
        <div class="cont_list">
            <button class="btn_modal" v-on:click="saveDocument()">
                Déposer un CV
            </button> 
            <form class="cont_file_form">
                <input 
                type="file"
                v-on:change="assignDocument"
                accept=".pdf"/>
            </form>
        </div>
      </div>
    </div>
</template>
