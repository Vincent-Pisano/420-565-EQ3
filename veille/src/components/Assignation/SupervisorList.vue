<script>
import NavBar from '@/components/Navbar/NavBar.vue'
import axios from "axios"
import router from "@/router"
export default{
        name: 'supervisorList',
        data: function(){
            return { 
                supervisors: [],
                currentSupervisor: undefined,
                errorMessage:""
            }
        },
        components: {
            NavBar
        },
        methods: {
            getSupervisors(){
                axios
                .get(`http://localhost:9090/getAll/supervisors`)
                .then((response) => {
                    this.supervisors = response.data
                })
                .catch((err) => {
                    console.log(err)
                    this.errorMessage = "Aucun Superviseur enregistré pour le moment"
                });
            },
            handleClick(currentSupervisor){
                this.currentSupervisor = (JSON.stringify(currentSupervisor))
                router.push({name : 'AssignStudents', params:{supervisor:this.currentSupervisor}})
            }
        },
        mounted: function(){
            this.getSupervisors()
        }
    }

</script>

<style scoped src="@/styles/App.css"></style>
<style scoped src="@/styles/List.css"></style>

<template>
    <NavBar/>
    <div id="supervisorList" class="cont_principal">
      <div class="cont_centrar">
        <h2 class="cont_title_form">Liste des superviseurs de stages</h2>
          <ul>
            <li v-for="(supervisor, x) in supervisors" :key="x" class="list_node" v-on:click="handleClick(supervisor)">
                {{supervisor.firstName}} {{supervisor.lastName}}
            </li>
          </ul>
            <p>{{errorMessage}}</p>
        </div>
    </div>
</template>
