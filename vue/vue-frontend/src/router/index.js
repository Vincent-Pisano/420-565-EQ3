import { createRouter, createWebHistory } from "vue-router";
import Login from "../views/SignUpLogin/Login";
import SignupStudent from "../views/SignUpLogin/SignupStudent";
import SignupMonitor from "../views/SignUpLogin/SignupMonitor";
import SignupSupervisor from "../views/SignUpLogin/SignupSupervisor";
import ProfilePage from "../views/ProfilePage";
import InternshipOfferForm from "../views/InternshipOfferForm/InternshipOfferForm";
import InternshipOfferListToValidate from "../views/InternshipOfferList/InternshipOfferListToValidate";
import InternshipOfferFormValidation from "../views/InternshipOfferForm/InternshipOfferFormValidation";
import InternshipOfferListValidated from "../views/InternshipOfferList/InternshipOfferListValidated";

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
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
  {
    path: "/applyInternshipOfferForm",
    name: "InternshipOfferListValidated",
    component: InternshipOfferListValidated,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
