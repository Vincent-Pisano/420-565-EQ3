package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.Utils.*;

@Service
public class BackendService {

    private final StudentRepository studentRepository;
    private final MonitorRepository monitorRepository;
    private final SupervisorRepository supervisorRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final InternshipRepository internshipRepository;
    private final EvaluationRepository evaluationRepository;

    BackendService(StudentRepository studentRepository,
                   MonitorRepository monitorRepository,
                   SupervisorRepository supervisorRepository,
                   InternshipOfferRepository internshipOfferRepository,
                   InternshipRepository internshipRepository,
                   EvaluationRepository evaluationRepository
    ) {
        this.studentRepository = studentRepository;
        this.monitorRepository = monitorRepository;
        this.supervisorRepository = supervisorRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.internshipRepository = internshipRepository;
        this.evaluationRepository = evaluationRepository;
    }


    public Optional<List<Student>> getAllStudents(Department department) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndDepartment(department);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)).get());
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithoutSupervisor(Department department) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndDepartmentAndSupervisorIsNull(department);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)).get());
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
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
        System.out.println(documentName + EVALUATION_EXTENSION);
        Optional<Evaluation> optionalEvaluation =
                evaluationRepository.getByDocument_NameAndIsDisabledFalse(documentName + EVALUATION_EXTENSION);
        if (optionalEvaluation.isPresent()) {
            Evaluation evaluation = optionalEvaluation.get();
            optionalDocument = Optional.of(evaluation.getDocument());
        }
        return optionalDocument;
    }

    public Optional<PDFDocument> downloadInternshipContractDocument(String idInternship) {
        Optional<Internship> optionalInternship = internshipRepository.findById(idInternship);
        return optionalInternship.map(Internship::getInternshipContract);
    }
}

