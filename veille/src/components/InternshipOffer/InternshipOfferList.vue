<script>
import NavBar from '@/components/Navbar/NavBar.vue'
import axios from "axios"
import router from "@/router"
export default{
        name: 'internshipOfferList',
        data: function(){
            return { 
                offers: [],
                currentOffer: undefined,
                errorMessage:""
            }
        },
        components: {
            NavBar
        },
        methods: {
            getInternshipOffers(){
                axios
                .get(`http://localhost:9090/getAll/internshipOffer/unvalidated`)
                .then((response) => {
                    this.offers = response.data
                })
                .catch((err) => {
                    console.log(err)
                    this.errorMessage = "Aucune offre de stage à valider"
                });
            },
            handleClick(currentOffer){
                this.currentOffer = (JSON.stringify(currentOffer))
                router.push({name : 'ValidateOffer', params:{offer:this.currentOffer}})
            }
        },
        mounted: function(){
            this.getInternshipOffers()
        }
    }

</script>

<style scoped src="@/styles/App.css"></style>
<style scoped src="@/styles/List.css"></style>

<template>
    <NavBar/>
    <div id="internshipOfferList" class="cont_principal">
      <div class="cont_central">
        <h2 class="cont_title_form">Offres à valider</h2>
          <ul>
            <li v-for="(offer, x) in offers" :key="x" class="list_node" v-on:click="handleClick(offer)">
                {{offer.jobName}}, {{offer.city}}
            </li>
          </ul>
        </div>
    </div>
</template>
