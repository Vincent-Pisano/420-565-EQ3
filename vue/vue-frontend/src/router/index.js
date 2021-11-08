import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login'
//import About from '../views/About'
//import SignUp from '../views/Signup'
import SignupStudent from '../components/SignupStudent'

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
  },
  /*{
    path: '/about',
    name: 'About',
    component: About,
  },*/
  {
    path: '/signUpStudent',
    name: 'SignUpStudent',
    component: SignupStudent,
  },
  {
    path: '/signUpMonitor',
    name: 'SignUpMonitor',
    component: SignupStudent,
  },
  {
    path: '/signUpSupervisor',
    name: 'SignUpSupervisor',
    component: SignupStudent,
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router