package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.eq3.backend.utils.Utils.*;

@Service
public class BackendService {

    private final StudentRepository studentRepository;
    private final MonitorRepository monitorRepository;
    private final SupervisorRepository supervisorRepository;
    private final InternshipManagerRepository internshipManagerRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final InternshipRepository internshipRepository;
    private final MongoTemplate mongoTemplate;
    private final InternshipApplicationRepository internshipApplicationRepository;

    BackendService(StudentRepository studentRepository,
                   MonitorRepository monitorRepository,
                   SupervisorRepository supervisorRepository,
                   InternshipManagerRepository internshipManagerRepository,
                   InternshipOfferRepository internshipOfferRepository,
                   InternshipRepository internshipRepository,
                   MongoTemplate mongoTemplate,
                   InternshipApplicationRepository internshipApplicationRepository) {
        this.studentRepository = studentRepository;
        this.monitorRepository = monitorRepository;
        this.supervisorRepository = supervisorRepository;
        this.internshipManagerRepository = internshipManagerRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.internshipRepository = internshipRepository;
        this.mongoTemplate = mongoTemplate;
        this.internshipApplicationRepository = internshipApplicationRepository;
    }

    public Optional<Binary> saveSignature(String username, MultipartFile signature) {
        Optional<Binary> optionalBinary = Optional.empty();
        Binary image = null;
        try {
            image = new Binary(BsonBinarySubType.BINARY, signature.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            switch (username.charAt(0)) {
                case 'G' :
                    Optional<InternshipManager> optionalInternshipManager = internshipManagerRepository.findByUsernameAndIsDisabledFalse(username);
                    if (optionalInternshipManager.isPresent()) {
                        InternshipManager internshipManager = optionalInternshipManager.get();
                        internshipManager.setSignature(image);
                        internshipManagerRepository.save(internshipManager);
                        optionalBinary = Optional.of(image);
                    }
                    break;

                case 'S' :
                    Optional<Supervisor> optionalSupervisor = supervisorRepository.findByUsernameAndIsDisabledFalse(username);
                    if (optionalSupervisor.isPresent()) {
                        Supervisor supervisor = optionalSupervisor.get();
                        supervisor.setSignature(image);
                        supervisorRepository.save(supervisor);
                        optionalBinary = Optional.of(image);
                    }
                    break;

                case 'M' :
                    Optional<Monitor> optionalMonitor = monitorRepository.findByUsernameAndIsDisabledFalse(username);
                    if (optionalMonitor.isPresent()) {
                        Monitor monitor = optionalMonitor.get();
                        monitor.setSignature(image);
                        monitorRepository.save(monitor);
                        optionalBinary = Optional.of(image);
                    }
                    break;

                case 'E' :
                    Optional<Student> optionalStudent = studentRepository.findByUsernameAndIsDisabledFalse(username);
                    if (optionalStudent.isPresent()) {
                        Student student = optionalStudent.get();
                        student.setSignature(image);
                        studentRepository.save(student);
                        optionalBinary = Optional.of(image);
                    }
                    break;
            }
        }
        return optionalBinary;
    }

    public Optional<List<Student>> getAllStudents(Department department, String session) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndDepartmentAndSessionsContains(department, session);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)));
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAllByIsDisabledFalse();
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)));
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithoutSupervisor(Department department, String session) {
        System.out.println(session);
        List<Student> students = studentRepository.
                findAllByIsDisabledFalseAndDepartmentAndSupervisorMapIsEmptyAndSessionContains(department, session);
        System.out.println(students);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)));
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithSupervisor(String idSupervisor, String session) {
        List<Student> students = studentRepository.findAllBySupervisor_IdAndIsDisabledFalse(new ObjectId(idSupervisor), session);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)));
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithoutCV() {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndCVListIsEmpty();
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)));
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<String>> getAllSessionsOfStudent(String idStudent) {
        List<String> sessions = new ArrayList<>();
        Optional<Student> optionalStudent = studentRepository.findStudentByIdAndIsDisabledFalse(idStudent);

        optionalStudent.ifPresent(student -> updateSessionsFromStudent(sessions, student));
        Collections.reverse(sessions);
        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions);
    }

    private void updateSessionsFromStudent(List<String> sessions, Student student) {
        List<InternshipApplication> internshipApplications = internshipApplicationRepository.findAllByStudentAndIsDisabledFalse(student);
        internshipApplications.forEach(internshipApplication -> {
            InternshipOffer currentOffer = internshipApplication.getInternshipOffer();
            if (!sessions.contains(currentOffer.getSession())) {
                sessions.add(currentOffer.getSession());
            }
        });
    }

    public Optional<List<Student>> getAllStudentsWithApplicationStatusWaitingAndInterviewDatePassed() {
        List<Student> studentWaitingAndInterviewDatePassed = new ArrayList<>();
        List<InternshipApplication> internshipApplicationsWithInterviewDate =
                internshipApplicationRepository.findAllByInterviewDateIsNotNull();

        internshipApplicationsWithInterviewDate.forEach(internshipApplication ->
                setStudentListWithApplicationStatusWaitingAndInterviewDatePassed(studentWaitingAndInterviewDatePassed, internshipApplication));
        System.out.println(studentWaitingAndInterviewDatePassed.size());
        return studentWaitingAndInterviewDatePassed.isEmpty() ? Optional.empty() : Optional.of(studentWaitingAndInterviewDatePassed);
    }

    private void setStudentListWithApplicationStatusWaitingAndInterviewDatePassed(List<Student> students, InternshipApplication internshipApplication) {
        if (internshipApplication.getStatus() == InternshipApplication.ApplicationStatus.WAITING &&
            internshipApplication.getInterviewDate().before( new Date()) &&
            !students.contains(internshipApplication.getStudent())) {
            students.add(internshipApplication.getStudent());
        }
    }

    public Optional<List<Student>> getAllStudentsWithoutInterviewDate() {
        List<Student> studentsWithoutInterviewDate = studentRepository.findAllByIsDisabledFalse();
        List<InternshipApplication> internshipApplicationsWithInterviewDate =
                internshipApplicationRepository.findAllByInterviewDateIsNotNull();

        internshipApplicationsWithInterviewDate.forEach(internshipApplication -> studentsWithoutInterviewDate.remove(internshipApplication.getStudent()));

        return studentsWithoutInterviewDate.isEmpty() ? Optional.empty() : Optional.of(studentsWithoutInterviewDate);
    }

    public Optional<List<Student>> getAllStudentsWithInternship() {
        List<Student> studentsWithInternship = new ArrayList<>();
        List<InternshipApplication> completedInternshipApplications = internshipApplicationRepository.findAllByIsDisabledFalse();
        completedInternshipApplications.forEach(internshipApplication -> {
            if (internshipApplication.statusIsCompleted()){
                if (!studentsWithInternship.contains(internshipApplication.getStudent())){
                    studentsWithInternship.add(internshipApplication.getStudent());
                }
            }
        });
        return studentsWithInternship.isEmpty() ? Optional.empty() : Optional.of(studentsWithInternship);
    }

    public Optional<List<Student>> getAllStudentsWaitingInterview() {
        List<Student> studentsWaitingInterview = new ArrayList<>();
        List<InternshipApplication> internshipApplicationsWithoutInterviewDate =
                internshipApplicationRepository.findAllByStatusWaitingAndInterviewDateIsAfterNowAndIsDisabledFalse();

        internshipApplicationsWithoutInterviewDate.forEach(internshipApplication -> studentsWaitingInterview.add(internshipApplication.getStudent()));

        return studentsWaitingInterview.isEmpty() ? Optional.empty() :
                Optional.of(studentsWaitingInterview.stream().distinct().collect(Collectors.toList()));
    }

    public Optional<List<Student>> getAllStudentsWithoutStudentEvaluation(){
        List<Internship> internshipListWithoutStudentEvaluation =
                internshipRepository.findByStudentEvaluationNullAndIsDisabledFalse();
        List<Student> studentList = getAllStudentsFromInternships(internshipListWithoutStudentEvaluation);
        return studentList.isEmpty() ? Optional.empty() : Optional.of(studentList);
    }

    public Optional<List<Student>> getAllStudentsWithoutEnterpriseEvaluation(){
        List<Internship> internshipListWithoutEnterpriseEvaluation =
                internshipRepository.findByEnterpriseEvaluationNullAndIsDisabledFalse();
        List<Student> studentList = getAllStudentsFromInternships(internshipListWithoutEnterpriseEvaluation);
        return studentList.isEmpty() ? Optional.empty() : Optional.of(studentList);
    }

    private List<Student> getAllStudentsFromInternships(List<Internship> internshipListWithoutStudentEvaluation){
        List<Student> studentList = new ArrayList<>();
        internshipListWithoutStudentEvaluation .forEach(internship -> {
            InternshipApplication internshipApplication = internship.getInternshipApplication();
            if(internshipApplication.statusIsCompleted())
                studentList.add(internshipApplication.getStudent());
        });
        return studentList.stream().distinct().collect(Collectors.toList());
    }

    public Optional<List<Supervisor>> getAllSupervisorsOfSession(String session) {
        List<Supervisor> supervisors = supervisorRepository.findAllByIsDisabledFalseAndSessionsContains(session);
        return supervisors.isEmpty() ? Optional.empty() : Optional.of(supervisors);
    }

    public Optional<List<String>> getAllSessionsOfMonitor(String idMonitor) {
        Query query = new Query(getCriteriaQueryGetAllSessionsOfMonitor(idMonitor));

        List<String> sessions = mongoTemplate
                .getCollection(COLLECTION_NAME_INTERNSHIP_OFFER)
                .distinct(FIELD_SESSION, query.getQueryObject() ,String.class)
                .into(new ArrayList<>());

        Collections.reverse(sessions);
        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions);
    }

    private Criteria getCriteriaQueryGetAllSessionsOfMonitor(String idMonitor) {
        List<Criteria> expression =  new ArrayList<>();
        expression.add(Criteria.where(QUERY_CRITERIA_MONITOR_ID).is(new ObjectId(idMonitor)));
        expression.add(Criteria.where(FIELD_IS_DISABLED).is(false));
        return new Criteria().andOperator(expression.toArray(expression.toArray(new Criteria[0])));
    }

    public Optional<TreeSet<String>> getAllNextSessionsOfInternshipOffers() {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByIsValidTrueAndIsDisabledFalse();
        TreeSet<String> sessions = setNextSessionsOfInternshipOffers(internshipOffers);

        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions);
    }

    private TreeSet<String> setNextSessionsOfInternshipOffers(List<InternshipOffer> internshipOffers) {
        TreeSet<String> sessions = new TreeSet<>();

        String currentSession = getSessionFromDate(new Date());
        String[] sessionComponents = currentSession.split(" ");
        String session = sessionComponents[POSITION_TAG_IN_SESSION];
        int year = Integer.parseInt(sessionComponents[POSITION_YEAR_IN_SESSION]);

        internshipOffers.forEach(internshipOffer -> {
            String internshipOfferSession = internshipOffer.getSession();
            int internshipOfferYear = Integer.parseInt(internshipOfferSession.split(" ")[POSITION_YEAR_IN_SESSION]);

            if (internshipOfferYear > year || (WINTER_SESSION.equals(session) && internshipOfferYear == year)) {
                sessions.add(internshipOffer.getSession());
            }
        });
        return sessions;
    }

    public Optional<Monitor> getMonitorByUsername(String username) {
        return monitorRepository.findByUsernameAndIsDisabledFalse(username);
    }

    public Optional<Student> assignSupervisorToStudent(String idStudent, String idSupervisor) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        Optional<Supervisor> optionalSupervisor = supervisorRepository.findById(idSupervisor);

        optionalStudent.ifPresent(student -> {
            Map<String, Supervisor> supervisorMap = student.getSupervisorMap();
            optionalSupervisor.ifPresent(supervisor -> supervisorMap.put(getNextSessionFromDate(new Date()), supervisor));
            studentRepository.save(student);
        });

        return cleanUpStudentCVList(optionalStudent);
    }

    private Optional<PDFDocument> getCVFromStudent(String idCV, Optional<Student> optionalStudent) {
        Optional<PDFDocument> optionalDocument = Optional.empty();

        if (optionalStudent.isPresent() && optionalStudent.get().getCVList() != null) {
            Student student = optionalStudent.get();
            List<CV> listCV = student.getCVList();
            for (CV cv : listCV) {
                if (cv.getId().equals(idCV))
                    optionalDocument = Optional.of(cv.getPDFDocument());
            }
            student.setCVList(listCV);
        }
        return optionalDocument;
    }

    public Optional<PDFDocument> downloadInternshipOfferDocument(String id) {
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(id);
        Optional<PDFDocument> optionalDocument = Optional.empty();

        if (optionalInternshipOffer.isPresent() && optionalInternshipOffer.get().getPDFDocument() != null)
            optionalDocument = Optional.of(optionalInternshipOffer.get().getPDFDocument());

        return optionalDocument;
    }

    public Optional<PDFDocument> downloadStudentCVDocument(String idStudent, String idCV) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        return getCVFromStudent(idCV, optionalStudent);
    }

    public Optional<PDFDocument> downloadEvaluationDocument(String documentName) {
        Optional<PDFDocument> optionalDocument = Optional.empty();
        try {
            Path pdfPath = Paths.get(ASSETS_PATH + documentName + EVALUATION_FILE_NAME);
            optionalDocument = Optional.of(PDFDocument.builder()
                    .name(documentName + EVALUATION_FILE_NAME)
                    .content(new Binary(BsonBinarySubType.BINARY, Files.readAllBytes(pdfPath)))
                    .build());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return optionalDocument;
    }

    public Optional<PDFDocument> downloadInternshipContractDocument(String idInternship) {
        Optional<Internship> optionalInternship = internshipRepository.findById(idInternship);
        return optionalInternship.map(Internship::getInternshipContract);
    }

    public Optional<PDFDocument> downloadInternshipStudentEvaluationDocument(String idInternship) {
        Optional<Internship> optionalInternship = internshipRepository.findById(idInternship);
        return optionalInternship.map(Internship::getStudentEvaluation);
    }

    public Optional<PDFDocument> downloadInternshipEnterpriseEvaluationDocument(String idInternship) {
        Optional<Internship> optionalInternship = internshipRepository.findById(idInternship);
        return optionalInternship.map(Internship::getEnterpriseEvaluation);
    }
}

