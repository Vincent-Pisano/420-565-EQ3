<script>
import NavBar from '@/components/Navbar/NavBar.vue'
import axios from "axios"
export default{
        name: 'assignStudents',
        props:['supervisor'],
        data: function(){
            return { 
                currentSupervisor: JSON.parse(this.supervisor),
                students: [],
                currentStudent: undefined,
                errorMessage:""
            }
        },
        components: {
            NavBar
        },
        methods: {
            getStudents(){
                axios
                .get(`http://localhost:9090/getAll/students/noSupervisor/${this.currentSupervisor.department}`)
                .then((response) => {
                    this.students = response.data
                })
                .catch((err) => {
                    console.log(err)
                    this.errorMessage = "Aucun Étudiant à assigner pour le moment"
                });
            },
            handleClick(currentStudent){
                this.currentStudent = currentStudent
                axios
                .post(
                    `http://localhost:9090/assign/supervisor/${this.currentStudent.id}/${this.currentSupervisor.id}`
                )
                .then((response) =>{
                    this.currentStudent = response.data
                    this.getStudents()
                })
                .catch((err) => {
                    console.log(err)
                    this.errorMessage = "Erreur lors de l'assignation"
                });
            }
        },
        mounted: function(){
            this.getStudents()
        }
    }

</script>

<style scoped src="@/styles/App.css"></style>
<style scoped src="@/styles/List.css"></style>

<template>
    <NavBar/>
    <div id="assignStudents" class="cont_principal">
      <div class="cont_central">
        <h2 class="cont_title_form">Liste des étudiants à assigner</h2>
          <ul>
            <li v-for="(student, x) in students" :key="x" class="list_node" v-on:click="handleClick(student)">
                {{student.firstName}} {{student.lastName}}
            </li>
          </ul>
        </div>
    </div>
</template>
