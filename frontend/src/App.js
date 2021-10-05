import './App.css';
import SignUp from './components/SignUp/SignUp';
import Home from './components/Home';
import Login from './components/Login/Login';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import { ProtectedRoute } from "./services/ProtectedRoute";
import StudentList from './components/StudentList/StudentList';
import SupervisorList from './components/SupervisorList/SupervisorList';
import InternshipOfferForm from './components/InternshipOffer/InternshipOfferForm'
import InternshipOfferList from './components/IntershipOfferList/InternshipOfferList';
import NavigationBar from "../src/components/Navbar/NavigationBar";

function App () {

  return (
    <Router>
      <div className="App">  
        <NavigationBar />
        <Switch> 
          <Route path="/" exact component={Login}/>
          <Route path="/signUp" exact component={SignUp}/>
          <ProtectedRoute path="/home/:username" exact component={Home}/>
          <ProtectedRoute path="/formInternshipOffer" exact component={InternshipOfferForm}/>
          <ProtectedRoute path="/listStudents" exact component={StudentList}/>
          <ProtectedRoute path="/listInternshipOffer" exact component={InternshipOfferList}/>
          <ProtectedRoute path="/listSupervisors" exact component={SupervisorList}/>
          <Route path="*" exact component={Login}/>
        </Switch>           
      </div>
    </Router>
  );
}

export default App;
