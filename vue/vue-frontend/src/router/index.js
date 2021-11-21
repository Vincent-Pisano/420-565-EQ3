import { createRouter, createWebHistory } from "vue-router";
import Login from "../views/Login";
//import About from '../views/About'
import SignupStudent from "../views/SignupStudent";
import SignupMonitor from "../views/SignupMonitor";
import SignupSupervisor from "../views/SignupSupervisor";
import ProfilePage from "../views/ProfilePage";
import InternshipOfferForm from "../views/InternshipOfferForm";
import InternshipOfferListToValidate from "../views/InternshipOfferListToValidate";
import InternshipOfferFormValidation from "../views/InternshipOfferFormValidation";

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  /*{
    path: '/about',
    name: 'About',
    component: About,
  },*/
  {
    path: "/signUpStudent",
    name: "SignUpStudent",
    component: SignupStudent,
  },
  {
    path: "/signUpMonitor",
    name: "SignUpMonitor",
    component: SignupMonitor,
  },
  {
    path: "/signUpSupervisor",
    name: "SignUpSupervisor",
    component: SignupSupervisor,
  },
  {
    path: "/profile",
    name: "ProfilePage",
    component: ProfilePage,
  },
  {
    path: "/internshipOfferForm",
    name: "InternshipOfferForm",
    component: InternshipOfferForm,
  },
  {
    path: "/internshipOfferListToValidate",
    name: "InternshipOfferListToValidate",
    component: InternshipOfferListToValidate,
  },
  {
    path: "/internshipOfferValidation",
    name: "InternshipOfferFormValidation",
    component: InternshipOfferFormValidation,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
