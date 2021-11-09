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

    public Optional<List<Student>> getAllStudents(Department department) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndDepartment(department);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)).get());
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAllByIsDisabledFalse();
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)).get());
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithoutSupervisor(Department department) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndDepartmentAndSupervisorIsNull(department);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)).get());
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithSupervisor(String idSupervisor) {
        List<Student> students = studentRepository.findAllBySupervisor_IdAndIsDisabledFalse(idSupervisor);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)).get());
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithoutCV() {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndCVListIsEmpty();
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)).get());
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithApplicationStatusWaitingAndInterviewDatePassedToday() {
        Date currentDate = new Date();
        List<Student> allStudentsWithApplicationStatusWaitingAndInterviewDatePassedToday = new ArrayList<>();
        List<InternshipApplication> internshipApplicationsWithInterviewDate =
                internshipApplicationRepository.findAllByInterviewDateIsNotNull();
        for (InternshipApplication currentInternshipApplication: internshipApplicationsWithInterviewDate) {
            Date internshipApplicationDate = currentInternshipApplication.getInterviewDate();
            if (currentInternshipApplication.getStatus() == InternshipApplication.ApplicationStatus.WAITING) {
                allStudentsWithApplicationStatusWaitingAndInterviewDatePassedToday.add(currentInternshipApplication.getStudent());
            }
            else if (internshipApplicationDate.before(currentDate) && !allStudentsWithApplicationStatusWaitingAndInterviewDatePassedToday.contains(currentInternshipApplication.getStudent())) {
                allStudentsWithApplicationStatusWaitingAndInterviewDatePassedToday.add(currentInternshipApplication.getStudent());
            }
        }
        return allStudentsWithApplicationStatusWaitingAndInterviewDatePassedToday.isEmpty() ? Optional.empty() : Optional.of(allStudentsWithApplicationStatusWaitingAndInterviewDatePassedToday);
    }

    public Optional<List<Student>> getAllStudentsWithoutInterviewDate() {
        List<Student> studentsWithoutInterviewDate = studentRepository.findAllByIsDisabledFalse();
        List<InternshipApplication> internshipApplicationsWithInterviewDate =
                internshipApplicationRepository.findAllByInterviewDateIsNotNull();

        for (InternshipApplication internshipApplication : internshipApplicationsWithInterviewDate) {
            studentsWithoutInterviewDate.remove(internshipApplication.getStudent());
        }
        return studentsWithoutInterviewDate.isEmpty() ? Optional.empty() : Optional.of(studentsWithoutInterviewDate);
    }

    public Optional<List<Student>> getAllStudentsWithInternship() {
        List<Student> studentsWithInternship = new ArrayList<>();
        List<InternshipApplication> completedInternshipApplications = internshipApplicationRepository.findAllByIsDisabledFalse();
        for (InternshipApplication internshipApplication : completedInternshipApplications) {
            if (internshipApplication.statusIsCompleted()){
                if (!studentsWithInternship.contains(internshipApplication.getStudent())){
                    studentsWithInternship.add(internshipApplication.getStudent());
                }
            }
        }
        return studentsWithInternship.isEmpty() ? Optional.empty() : Optional.of(studentsWithInternship);
    }

    public Optional<List<Student>> getAllStudentsWaitingInterview() {
        List<Student> studentsWaitingInterview = new ArrayList<>();
        List<InternshipApplication> internshipApplicationsWithoutInterviewDate =
                internshipApplicationRepository.findAllByStatusWaitingAndInterviewDateIsAfterNowAndIsDisabledFalse();

        for (InternshipApplication internshipApplication : internshipApplicationsWithoutInterviewDate) {
            studentsWaitingInterview.add(internshipApplication.getStudent());
        }
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

    public Optional<List<Supervisor>> getAllSupervisors() {
        List<Supervisor> supervisors = supervisorRepository.findAllByIsDisabledFalse();
        return supervisors.isEmpty() ? Optional.empty() : Optional.of(supervisors);
    }

    public Optional<List<String>> getAllSessionsOfMonitor(String idMonitor) {
        Criteria criteria = new Criteria();
        List<Criteria> expression =  new ArrayList<>();
        expression.add(Criteria.where(QUERY_CRITERIA_MONITOR_ID).is(new ObjectId(idMonitor)));
        expression.add(Criteria.where("isDisabled").is(false));
        //Query query = new Query(Criteria.where(QUERY_CRITERIA_MONITOR_ID).is(new ObjectId(idMonitor)));
        Query query = new Query(criteria.andOperator(expression.toArray(expression.toArray(new Criteria[expression.size()]))));

        List<String> sessions = mongoTemplate
                .getCollection(COLLECTION_NAME_INTERNSHIP_OFFER)
                .distinct(FIELD_SESSION, query.getQueryObject() ,String.class)
                .into(new ArrayList<>());

        Collections.reverse(sessions);
        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions);
    }

    public Optional<Monitor> getMonitorByUsername(String username) {
        return monitorRepository.findByUsernameAndIsDisabledFalse(username);
    }

    public Optional<Student> assignSupervisorToStudent(String idStudent, String idSupervisor) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        Optional<Supervisor> optionalSupervisor = supervisorRepository.findById(idSupervisor);

        optionalStudent.ifPresent(student -> {
            student.setSupervisor(optionalSupervisor.orElse(null));
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
            Path pdfPath = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\assets\\" + documentName + "Evaluation.pdf");
            optionalDocument = Optional.of(PDFDocument.builder()
                    .name(documentName + "Evaluation.pdf")
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

