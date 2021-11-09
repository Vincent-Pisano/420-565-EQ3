import { createRouter, createWebHistory } from 'vue-router'
import Home from '../components/Home.vue'
import SignUp from '../components/SignUp/SignUp.vue'
import Login from '../components/Login/Login.vue'
import InternshipOffer from '../components/InternshipOffer/InternshipOffer.vue'

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
    component: InternshipOffer
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
