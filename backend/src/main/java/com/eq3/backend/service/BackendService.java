package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final EvaluationRepository evaluationRepository;
    private final InternshipApplicationRepository internshipApplicationRepository;

    BackendService(StudentRepository studentRepository,
                   MonitorRepository monitorRepository,
                   SupervisorRepository supervisorRepository,
                   InternshipManagerRepository internshipManagerRepository,
                   InternshipOfferRepository internshipOfferRepository,
                   InternshipRepository internshipRepository,
                   EvaluationRepository evaluationRepository,
                   InternshipApplicationRepository internshipApplicationRepository) {
        this.studentRepository = studentRepository;
        this.monitorRepository = monitorRepository;
        this.supervisorRepository = supervisorRepository;
        this.internshipManagerRepository = internshipManagerRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.internshipRepository = internshipRepository;
        this.evaluationRepository = evaluationRepository;
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

    public Optional<List<Student>> getAllStudentsWithoutCV() {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndCVListIsEmpty();
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)).get());
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
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

    public Optional<List<Supervisor>> getAllSupervisors() {
        List<Supervisor> supervisors = supervisorRepository.findAllByIsDisabledFalse();
        return supervisors.isEmpty() ? Optional.empty() : Optional.of(supervisors);
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

    public Optional<List<Student>> getAllStudentsWithoutStudentEvaluation(){
        List<Internship> internshipListWithoutStudentEvaluation = internshipRepository.findByStudentEvaluationNull();
        Optional<List<Student>> optionalStudentList = Optional.of(getListOfStudentsWithoutStudentEvaluation(internshipListWithoutStudentEvaluation));
        return optionalStudentList;
    }

    private List<Student> getListOfStudentsWithoutStudentEvaluation(List<Internship> internshipListWithoutStudent){
        List<Student> studentList = new ArrayList<>();
        for (Internship internship : internshipListWithoutStudent){
            InternshipApplication internshipApplication = internship.getInternshipApplication();
            Student student = internshipApplication.getStudent();
            if(!studentList.contains(student)){
                studentList.add(student);
            }
        }
        return studentList;
    }
}

