import './App.css';
import SignUp from './components/SignUp/SignUp';
import Home from './components/Home';
import Login from './components/Login/Login';
import { URL_STUDENT_LIST_CV_TO_VALIDATE, URL_STUDENT_LIST_FROM_DEPARTMENT } from './Utils/URL'
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import { ProtectedRoute } from "./services/ProtectedRoute";
import StudentList from './components/StudentList/StudentList';
import StudentReportList from './components/Reports/StudentReportList/StudentReportList';
import SupervisorList from './components/SupervisorList/SupervisorList';
import InternshipOfferForm from './components/InternshipOffer/InternshipOfferForm'
import InternshipOfferList from './components/IntershipOfferList/InternshipOfferList';
import InternshipOfferReportList from './components/Reports/InternshipOfferReportList/InternshipOfferReportList';
import InternshipApplicationList from './components/InternshipApplicationList/InternshipApplicationList';
import InternshipApplicationReportList from './components/Reports/InternshipApplicationReportList/InternshipApplicationReportList';
import NavigationBar from "../src/components/Navbar/NavigationBar";
import ReportsHome from './components/Reports/ReportsHome';

import StudentListCVToValidate from './components/StudentList/List/StudentListCVToValidate';
import StudentListAssignSupervisor from './components/StudentList/List/StudentListAssignSupervisor';
import StudentListOfDepartment from './components/StudentList/List/StudentListOfDepartment';

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
          <ProtectedRoute path="/reports" exact component={ReportsHome}/>
          <ProtectedRoute path="/reports/listStudents" exact component={StudentReportList}/>
          <ProtectedRoute path="/reports/listInternshipApplication/:username" exact component={InternshipApplicationReportList}/>
          <ProtectedRoute path="/reports/listInternshipOffer" exact component={InternshipOfferReportList}/>
          <ProtectedRoute path="/listStudents" exact component={StudentList}/>
          <ProtectedRoute path="/listStudents/assigned" exact component={StudentListAssignSupervisor}/>
          <ProtectedRoute path="/listInternshipOffer" exact component={InternshipOfferList}/>
          <ProtectedRoute path="/listInternshipApplication" exact component={InternshipApplicationList}/>
          <ProtectedRoute path="/listInternshipApplication/signature" exact component={InternshipApplicationList}/>
          <ProtectedRoute path="/listInternshipApplication/:username" exact component={InternshipApplicationList}/>
          <ProtectedRoute path="/listSupervisors" exact component={SupervisorList}/>

          {/* NEW ROUTES */}

          <ProtectedRoute path={URL_STUDENT_LIST_CV_TO_VALIDATE} exact component={StudentListCVToValidate}/>
          <ProtectedRoute path={URL_STUDENT_LIST_FROM_DEPARTMENT} exact component={StudentListOfDepartment}/>

          <Route path="*" exact component={Login}/>
        </Switch>           
      </div>
    </Router>
  );
}

export default App;
