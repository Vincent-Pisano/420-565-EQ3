import './App.css';
import SignUp from './components/SignUp/SignUp';
import Home from './components/Home';
import Login from './components/Login/Login';
import { 
  URL_STUDENT_LIST_CV_TO_VALIDATE, 
  URL_STUDENT_LIST_FROM_DEPARTMENT, 
  URL_STUDENT_LIST_ASSIGN_SUPERVISOR, 
  URL_STUDENT_LIST_ASSIGNED_SUPERVISOR,
  URL_SUPERVISOR_LIST,
  URL_STUDENT_LIST_SUBSCRIBED,
  URL_STUDENT_LIST_WITHOUT_CV,
  URL_STUDENT_LIST_WITH_CV_WAITING_VALIDATION,
  URL_STUDENT_LIST_WITHOUT_INTERVIEW,
  URL_STUDENT_LIST_WAITING_INTERVIEW,
  URL_STUDENT_LIST_WAITING_INTERVIEW_ANSWER,
  URL_STUDENT_LIST_WITH_INTERNSHIP,
  URL_STUDENT_LIST_WITHOUT_MONITOR_EVALUATION,
  URL_STUDENT_LIST_WITHOUT_SUPERVISOR_EVALUATION
} from './Utils/URL'
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import { ProtectedRoute } from "./services/ProtectedRoute";
import StudentReportList from './components/Reports/StudentReportList/StudentReportList';

import InternshipOfferForm from './components/InternshipOffer/InternshipOfferForm'
import InternshipOfferList from './components/IntershipOfferList/InternshipOfferList';
import InternshipOfferReportList from './components/Reports/InternshipOfferReportList/InternshipOfferReportList';
import InternshipApplicationList from './components/InternshipApplicationList/InternshipApplicationList';
import InternshipApplicationReportList from './components/Reports/InternshipApplicationReportList/InternshipApplicationReportList';
import NavigationBar from "../src/components/Navbar/NavigationBar";
import ReportsHome from './components/Reports/ReportsHome';

import StudentListCVToValidate from './components/StudentList/List/StudentListCVToValidate';
import StudentListAssignSupervisor from './components/StudentList/List/StudentListAssignSupervisor';
import StudentListAssignedSupervisor from './components/StudentList/List/StudentListAssignedSupervisor';
import StudentListOfDepartment from './components/StudentList/List/StudentListOfDepartment';
import StudentListReportSubscribed from './components/StudentList/List/StudentListReportSubscribed';
import StudentListReportWithoutCV from './components/StudentList/List/StudentListReportWithoutCV';
import StudentListReportWithoutInterview from './components/StudentList/List/StudentListReportWithoutInterview';
import StudentListReportWaitingInterview from './components/StudentList/List/StudentListReportWaitingInterview';
import StudentListReportWaitingInterviewAnswer from './components/StudentList/List/StudentListReportWaitingInterviewAnswer';
import StudentListReportWithInternship from './components/StudentList/List/StudentListReportWithInternship';
import StudentListReportWithoutMonitorEvaluation from './components/StudentList/List/StudentListReportWithoutMonitorEvaluation';
import StudentListReportWithoutSupervisorEvaluation from './components/StudentList/List/StudentListReportWithoutSupervisorEvaluation';

import SupervisorList from './components/SupervisorList/List/SupervisorList';

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
          <ProtectedRoute path="/reports/studentList" exact component={StudentReportList}/>
          <ProtectedRoute path="/reports/listInternshipApplication/:username" exact component={InternshipApplicationReportList}/>
          <ProtectedRoute path="/reports/listInternshipOffer" exact component={InternshipOfferReportList}/>
          <ProtectedRoute path="/listInternshipOffer" exact component={InternshipOfferList}/>
          <ProtectedRoute path="/listInternshipApplication" exact component={InternshipApplicationList}/>
          <ProtectedRoute path="/listInternshipApplication/signature" exact component={InternshipApplicationList}/>
          <ProtectedRoute path="/listInternshipApplication/:username" exact component={InternshipApplicationList}/>

          {/* NEW ROUTES */}

          <ProtectedRoute path={URL_STUDENT_LIST_CV_TO_VALIDATE} exact component={StudentListCVToValidate}/>
          <ProtectedRoute path={URL_STUDENT_LIST_FROM_DEPARTMENT} exact component={StudentListOfDepartment}/>
          <ProtectedRoute path={URL_STUDENT_LIST_ASSIGN_SUPERVISOR} exact component={StudentListAssignSupervisor}/>
          <ProtectedRoute path={URL_STUDENT_LIST_ASSIGNED_SUPERVISOR} exact component={StudentListAssignedSupervisor}/>
          <ProtectedRoute path={URL_SUPERVISOR_LIST} exact component={SupervisorList}/>

          <ProtectedRoute path={URL_STUDENT_LIST_SUBSCRIBED} exact component={StudentListReportSubscribed}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITHOUT_CV} exact component={StudentListReportWithoutCV}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITH_CV_WAITING_VALIDATION} exact component={StudentListCVToValidate}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITHOUT_INTERVIEW} exact component={StudentListReportWithoutInterview}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WAITING_INTERVIEW} exact component={StudentListReportWaitingInterview}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WAITING_INTERVIEW_ANSWER} exact component={StudentListReportWaitingInterviewAnswer}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITH_INTERNSHIP} exact component={StudentListReportWithInternship}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITHOUT_SUPERVISOR_EVALUATION} exact component={StudentListReportWithoutSupervisorEvaluation}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITHOUT_MONITOR_EVALUATION} exact component={StudentListReportWithoutMonitorEvaluation}/>
          <Route path="*" exact component={Login}/>
        </Switch>           
      </div>
    </Router>
  );
}

export default App;
