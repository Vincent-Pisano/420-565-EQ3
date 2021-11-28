import { 
  REPORT_LINK,
  URL_STUDENT_LIST_CV_TO_VALIDATE, 
  URL_STUDENT_LIST_OF_DEPARTMENT, 
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
  URL_INTERNSHIP_APPLICATION_LIST_COMPLETED_REPORT,
  URL_INTERNSHIP_OFFER_FORM,
  URL_INTERNSHIP_APPLICATION_LIST_WAITING_ENTERPRISE_EVALUATION_REPORT,
  URL_INTERNSHIP_APPLICATION_LIST_WAITING_STUDENT_EVALUATION_REPORT,
  URL_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT_NOT_APPLIED
} from './URL'
import Home from '../components/Home';
import InternshipOfferForm from '../components/InternshipOffer/InternshipOfferForm'
import ReportsList from "../components/Reports/ReportsList"

import StudentListCVToValidate from '../components/StudentList/List/StudentListCVToValidate';
import StudentListAssignSupervisor from '../components/StudentList/List/StudentListAssignSupervisor';
import StudentListAssignedSupervisor from '../components/StudentList/List/StudentListAssignedSupervisor';
import StudentListOfDepartment from '../components/StudentList/List/StudentListOfDepartment';
import StudentListReportSubscribed from '../components/StudentList/List/StudentListReportSubscribed';
import StudentListReportWithoutCV from '../components/StudentList/List/StudentListReportWithoutCV';
import StudentListReportWithoutInterview from '../components/StudentList/List/StudentListReportWithoutInterview';
import StudentListReportWaitingInterview from '../components/StudentList/List/StudentListReportWaitingInterview';
import StudentListReportWaitingInterviewAnswer from '../components/StudentList/List/StudentListReportWaitingInterviewAnswer';
import StudentListReportWithInternship from '../components/StudentList/List/StudentListReportWithInternship';
import StudentListReportWithoutMonitorEvaluation from '../components/StudentList/List/StudentListReportWithoutMonitorEvaluation';
import StudentListReportWithoutSupervisorEvaluation from '../components/StudentList/List/StudentListReportWithoutSupervisorEvaluation';

import InternshipApplicationListOfStudent from '../components/InternshipApplicationList/List/InternshipApplicationListOfStudent';
import InternshipApplicationListAccepted from '../components/InternshipApplicationList/List/InternshipApplicationListAccepted';
import InternshipApplicationListSignatureInternshipManager from '../components/InternshipApplicationList/List/InternshipApplicationListSignatureInternshipManager';
import InternshipApplicationListOfInternshipOffer from '../components/InternshipApplicationList/List/InternshipApplicationListOfInternshipOffer';
import InternshipApplicationListOfStudentAssigned from '../components/InternshipApplicationList/List/InternshipApplicationListOfStudentAssigned';
import InternshipApplicationListReportWaiting from '../components/InternshipApplicationList/List/InternshipApplicationListReportWaiting';
import InternshipApplicationListReportCompleted from '../components/InternshipApplicationList/List/InternshipApplicationListReportCompleted';
import InternshipApplicationListReportWithoutEnterpriseEvaluation from '../components/InternshipApplicationList/List/InternshipApplicationListReportWithoutEnterpriseEvaluation';
import InternshipApplicationListReportWithoutStudentEvaluation from '../components/InternshipApplicationList/List/InternshipApplicationListReportWithoutStudentEvaluation';

import InternshipOfferListUnvalidated from '../components/IntershipOfferList/List/InternshipOfferListUnvalidated';
import InternshipOfferListValidated from '../components/IntershipOfferList/List/InternshipOfferListValidated';
import InternshipOfferListOfDepartmentNotApplied from '../components/IntershipOfferList/List/InternshipOfferListOfDepartmentNotApplied';
import InternshipOfferListOfDepartment from '../components/IntershipOfferList/List/InternshipOfferListOfDepartment';
import InternshipOfferListOfMonitor from '../components/IntershipOfferList/List/InternshipOfferListOfMonitor';

import SupervisorList from '../components/SupervisorList/List/SupervisorList';

export const ROUTES = [
    {
        link : "/home/:username",
        component: Home
    },
    {
        link : URL_STUDENT_LIST_CV_TO_VALIDATE,
        component: StudentListCVToValidate
    },
    {
        link : URL_STUDENT_LIST_OF_DEPARTMENT,
        component: StudentListOfDepartment
    },
    {
        link : URL_STUDENT_LIST_ASSIGN_SUPERVISOR,
        component: StudentListAssignSupervisor
    },
    {
        link : URL_STUDENT_LIST_ASSIGNED_SUPERVISOR,
        component: StudentListAssignedSupervisor
    },
    {
        link : URL_SUPERVISOR_LIST,
        component: SupervisorList
    },
    {
        link : URL_INTERNSHIP_APPLICATION_LIST_OF_STUDENT,
        component: InternshipApplicationListOfStudent
    },
    {
        link : URL_INTERNSHIP_APPLICATION_LIST_ACCEPTED,
        component: InternshipApplicationListAccepted
    },
    {
        link : URL_INTERNSHIP_APPLICATION_LIST_SIGNATURE,
        component: InternshipApplicationListSignatureInternshipManager
    },
    {
        link : URL_INTERNSHIP_APPLICATION_LIST_OF_INTERNSHIP_OFFER,
        component: InternshipApplicationListOfInternshipOffer
    },
    {
        link : URL_INTERNSHIP_APPLICATION_LIST_OF_STUDENT_ASSIGNED  + ":username",
        component: InternshipApplicationListOfStudentAssigned
    },
    {
        link : URL_INTERNSHIP_OFFER_LIST_UNVALIDATED,
        component: InternshipOfferListUnvalidated
    },
    {
        link : URL_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT,
        component: InternshipOfferListOfDepartment
    },
    {
        link : URL_INTERNSHIP_OFFER_LIST_OF_MONITOR,
        component: InternshipOfferListOfMonitor
    },
    {
        link : URL_INTERNSHIP_OFFER_FORM,
        component: InternshipOfferForm
    },
    {
        link : REPORT_LINK,
        component: ReportsList
    },
    {
        link : URL_INTERNSHIP_OFFER_LIST_WAITING_VALIDATION,
        component: InternshipOfferListUnvalidated
    },
    {
        link : URL_INTERNSHIP_OFFER_LIST_VALIDATED,
        component: InternshipOfferListValidated
    },
    {
        link : URL_INTERNSHIP_OFFER_LIST_OF_DEPARTMENT_NOT_APPLIED,
        component: InternshipOfferListOfDepartmentNotApplied
    },
    {
        link : URL_STUDENT_LIST_SUBSCRIBED,
        component: StudentListReportSubscribed
    },
    {
        link : URL_STUDENT_LIST_WITHOUT_CV,
        component: StudentListReportWithoutCV
    },
    {
        link : URL_STUDENT_LIST_WITH_CV_WAITING_VALIDATION,
        component: StudentListCVToValidate
    },
    {
        link : URL_STUDENT_LIST_WITHOUT_INTERVIEW,
        component: StudentListReportWithoutInterview
    },
    {
        link : URL_STUDENT_LIST_WAITING_INTERVIEW,
        component: StudentListReportWaitingInterview
    },
    {
        link : URL_STUDENT_LIST_WAITING_INTERVIEW_ANSWER,
        component: StudentListReportWaitingInterviewAnswer
    },
    {
        link : URL_STUDENT_LIST_WITH_INTERNSHIP,
        component: StudentListReportWithInternship
    },
    {
        link : URL_STUDENT_LIST_WITHOUT_SUPERVISOR_EVALUATION,
        component: StudentListReportWithoutSupervisorEvaluation
    },
    {
        link : URL_STUDENT_LIST_WITHOUT_MONITOR_EVALUATION,
        component: StudentListReportWithoutMonitorEvaluation
    },
    {
        link : URL_INTERNSHIP_APPLICATION_LIST_WAITING_REPORT + ":username",
        component: InternshipApplicationListReportWaiting
    },
    {
        link : URL_INTERNSHIP_APPLICATION_LIST_COMPLETED_REPORT + ":username",
        component: InternshipApplicationListReportCompleted
    },
    {
        link : URL_INTERNSHIP_APPLICATION_LIST_WAITING_ENTERPRISE_EVALUATION_REPORT + ":username",
        component: InternshipApplicationListReportWithoutEnterpriseEvaluation
    },
    {
        link : URL_INTERNSHIP_APPLICATION_LIST_WAITING_STUDENT_EVALUATION_REPORT + ":username",
        component: InternshipApplicationListReportWithoutStudentEvaluation
    }
]