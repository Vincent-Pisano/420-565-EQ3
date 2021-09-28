import './App.css';
import SignUp from './components/SignUp/SignUp';
import Home from './components/Home';
import Login from './components/Login/Login';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import { ProtectedRoute } from "./services/ProtectedRoute";
import StudentList from './components/StudentList';
import InternshipOfferList from './components/InternshipOfferList';
import InternshipOfferForm from './components/InternshipOfferForm'
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
          <Route path="*" exact component={Login}/>
        </Switch>           
      </div>
    </Router>
  );
}

export default App;
