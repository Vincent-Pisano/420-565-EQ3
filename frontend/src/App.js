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
  URL_INTERNSHIP_APPLICATION_LIST_OF_STUDENT,
  URL_INTERNSHIP_APPLICATION_LIST_ACCEPTED,
  URL_INTERNSHIP_APPLICATION_LIST_SIGNATURE,
  URL_STUDENT_LIST_WAITING_INTERVIEW_ANSWER,
  URL_STUDENT_LIST_WITH_INTERNSHIP,
  URL_STUDENT_LIST_WITHOUT_MONITOR_EVALUATION,
  URL_STUDENT_LIST_WITHOUT_SUPERVISOR_EVALUATION,
  URL_INTERNSHIP_OFFER_LIST_UNVALIDATED,
  URL_INTERNSHIP_OFFER_LIST_WAITING_VALIDATION,
  URL_INTERNSHIP_OFFER_LIST_VALIDATED,
  URL_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT,
  URL_INTERNSHIP_OFFER_LIST_OF_MONITOR,
  URL_INTERNSHIP_APPLICATION_LIST_OF_INTERNSHIP_OFFER,
  URL_INTERNSHIP_APPLICATION_LIST_OF_STUDENT_ASSIGNED,
  URL_INTERNSHIP_APPLICATION_LIST_WAITING_REPORT,
  URL_INTERNSHIP_APPLICATION_LIST_COMPLETED_REPORT
} from './Utils/URL'
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import { ProtectedRoute } from "./services/ProtectedRoute";
import StudentReportList from './components/Reports/StudentReportList/StudentReportList';

import InternshipOfferForm from './components/InternshipOffer/InternshipOfferForm'
import InternshipOfferList from './components/IntershipOfferList/InternshipOfferList';
import InternshipOfferReportList from './components/Reports/InternshipOfferReportList/InternshipOfferReportList';
import InternshipApplicationList from './components/InternshipApplicationList/InternshipApplicationList';
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

import InternshipApplicationListOfStudent from './components/InternshipApplicationList/List/InternshipApplicationListOfStudent';
import InternshipApplicationListAccepted from './components/InternshipApplicationList/List/InternshipApplicationListAccepted';
import InternshipApplicationListSignatureInternshipManager from './components/InternshipApplicationList/List/InternshipApplicationListSignatureInternshipManager';
import InternshipApplicationListOfInternshipOffer from './components/InternshipApplicationList/List/InternshipApplicationListOfInternshipOffer';
import InternshipApplicationListOfStudentAssigned from './components/InternshipApplicationList/List/InternshipApplicationListOfStudentAssigned';
import InternshipApplicationListReportWaiting from './components/InternshipApplicationList/List/InternshipApplicationListReportWaiting';
import InternshipApplicationListReportCompleted from './components/InternshipApplicationList/List/InternshipApplicationListReportCompleted';

import InternshipOfferListUnvalidated from './components/IntershipOfferList/List/InternshipOfferListUnvalidated';
import InternshipOfferListValidated from './components/IntershipOfferList/List/InternshipOfferListValidated';
import InternshipOfferListOfDepartment from './components/IntershipOfferList/List/InternshipOfferListOfDepartment';
import InternshipOfferListOfMonitor from './components/IntershipOfferList/List/InternshipOfferListOfMonitor';

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
          <ProtectedRoute path="/reports/listInternshipApplication/:username" exact component={InternshipApplicationListReportWaiting}/>
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

          <ProtectedRoute path={URL_INTERNSHIP_APPLICATION_LIST_OF_STUDENT} exact component={InternshipApplicationListOfStudent}/>
          <ProtectedRoute path={URL_INTERNSHIP_APPLICATION_LIST_ACCEPTED} exact component={InternshipApplicationListAccepted}/>
          <ProtectedRoute path={URL_INTERNSHIP_APPLICATION_LIST_SIGNATURE} exact component={InternshipApplicationListSignatureInternshipManager}/>
          <ProtectedRoute path={URL_INTERNSHIP_APPLICATION_LIST_OF_INTERNSHIP_OFFER} exact component={InternshipApplicationListOfInternshipOffer}/>
          <ProtectedRoute path={URL_INTERNSHIP_APPLICATION_LIST_OF_STUDENT_ASSIGNED + ":username"} exact component={InternshipApplicationListOfStudentAssigned}/>
          
          <ProtectedRoute path={URL_INTERNSHIP_OFFER_LIST_UNVALIDATED} exact component={InternshipOfferListUnvalidated}/>
          <ProtectedRoute path={URL_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT} exact component={InternshipOfferListOfDepartment}/>
          <ProtectedRoute path={URL_INTERNSHIP_OFFER_LIST_OF_MONITOR} exact component={InternshipOfferListOfMonitor}/>
          
          <ProtectedRoute path={URL_INTERNSHIP_OFFER_LIST_WAITING_VALIDATION} exact component={InternshipOfferListUnvalidated}/>
          <ProtectedRoute path={URL_INTERNSHIP_OFFER_LIST_VALIDATED} exact component={InternshipOfferListValidated}/>
          <ProtectedRoute path={URL_STUDENT_LIST_SUBSCRIBED} exact component={StudentListReportSubscribed}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITHOUT_CV} exact component={StudentListReportWithoutCV}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITH_CV_WAITING_VALIDATION} exact component={StudentListCVToValidate}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITHOUT_INTERVIEW} exact component={StudentListReportWithoutInterview}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WAITING_INTERVIEW} exact component={StudentListReportWaitingInterview}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WAITING_INTERVIEW_ANSWER} exact component={StudentListReportWaitingInterviewAnswer}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITH_INTERNSHIP} exact component={StudentListReportWithInternship}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITHOUT_SUPERVISOR_EVALUATION} exact component={StudentListReportWithoutSupervisorEvaluation}/>
          <ProtectedRoute path={URL_STUDENT_LIST_WITHOUT_MONITOR_EVALUATION} exact component={StudentListReportWithoutMonitorEvaluation}/>
          <ProtectedRoute path={URL_INTERNSHIP_APPLICATION_LIST_WAITING_REPORT + ":username"} exact component={InternshipApplicationListReportWaiting}/>
          <ProtectedRoute path={URL_INTERNSHIP_APPLICATION_LIST_COMPLETED_REPORT + ":username"} exact component={InternshipApplicationListReportCompleted}/>
          <Route path="*" exact component={Login}/>
        </Switch>           
      </div>
    </Router>
  );
}

export default App;
