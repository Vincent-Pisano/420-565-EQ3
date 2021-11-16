import { createRouter, createWebHistory } from 'vue-router'
import Home from '../components/Home.vue'
import SignUp from '../components/SignUp/SignUp.vue'
import Login from '../components/Login/Login.vue'
import InternshipOfferForm from '../components/InternshipOffer/InternshipOfferForm.vue'
import InternshipOfferList from '../components/InternshipOffer/InternshipOfferList.vue'
import ValidateOffer from '../components/InternshipOffer/ValidateOffer.vue'
import CV from '../components/CV/CV.vue'

const routes = [
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/signUp',
    name: 'SignUp',
    component: SignUp
  },
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/internshipOffer',
    name: 'InternshipOffer',
    component: InternshipOfferForm,
  },
  {
    path: '/listInternshipOffer',
    name: 'InternshipOfferList',
    component: InternshipOfferList
  },
  {
    path: '/validatesOffer',
    name: 'ValidateOffer',
    component: ValidateOffer,
    props:true
  },
  {
    path: '/cv',
    name: 'CV',
    component: CV
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
