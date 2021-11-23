package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.eq3.backend.utils.Utils.*;

@Service
public class BackendInternshipService {
    private final InternshipOfferRepository internshipOfferRepository;
    private final InternshipRepository internshipRepository;
    private final InternshipApplicationRepository internshipApplicationRepository;
    private final StudentRepository studentRepository;

    BackendInternshipService(
                   InternshipOfferRepository internshipOfferRepository,
                   InternshipRepository internshipRepository,
                   InternshipApplicationRepository internshipApplicationRepository,
                   StudentRepository studentRepository) {
        this.internshipOfferRepository = internshipOfferRepository;
        this.internshipRepository = internshipRepository;
        this.internshipApplicationRepository = internshipApplicationRepository;
        this.studentRepository = studentRepository;
    }

    public Optional<List<Student>> getAllStudentsWithApplicationStatusWaitingAndInterviewDatePassed(String session) {
        List<Student> studentWaitingAndInterviewDatePassed = new ArrayList<>();
        List<InternshipApplication> internshipApplicationsWithInterviewDate =
                internshipApplicationRepository.findAllByInterviewDateIsNotNull();

        internshipApplicationsWithInterviewDate.forEach(internshipApplication ->
                setStudentListWithApplicationStatusWaitingAndInterviewDatePassed(studentWaitingAndInterviewDatePassed, internshipApplication, session));
        return studentWaitingAndInterviewDatePassed.isEmpty() ? Optional.empty() : Optional.of(studentWaitingAndInterviewDatePassed);
    }

    private void setStudentListWithApplicationStatusWaitingAndInterviewDatePassed(List<Student> students, InternshipApplication internshipApplication, String session) {
        Student student = internshipApplication.getStudent();
        InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
        if (isStudentValidWithInterviewPassed(students, internshipApplication, session, student, internshipOffer))
            students.add(internshipApplication.getStudent());
    }

    private boolean isStudentValidWithInterviewPassed(List<Student> students, InternshipApplication internshipApplication, String session, Student student, InternshipOffer internshipOffer) {
        return internshipApplication.getStatus() == InternshipApplication.ApplicationStatus.WAITING &&
                internshipApplication.getInterviewDate().before(new Date()) &&
                !students.contains(internshipApplication.getStudent()) &&
                student.getSessions().contains(session) &&
                session.equals(internshipOffer.getSession());
    }

    public Optional<List<Student>> getAllStudentsWithoutInterviewDate(String session) {
        List<Student> studentsWithoutInterviewDate = studentRepository.findAllByIsDisabledFalseAndSessionsContains(session);
        List<InternshipApplication> internshipApplicationsWithInterviewDate =
                internshipApplicationRepository.findAllByInterviewDateIsNotNull();

        internshipApplicationsWithInterviewDate.forEach(internshipApplication -> {
            InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
            String internshipOfferSession = internshipOffer.getSession();
            Student student = internshipApplication.getStudent();
            if (session.equals(internshipOfferSession))
                studentsWithoutInterviewDate.remove(student);
        });
        return studentsWithoutInterviewDate.isEmpty() ? Optional.empty() : Optional.of(studentsWithoutInterviewDate);
    }

    public Optional<List<Student>> getAllStudentsWithInternship(String session) {
        List<Student> studentsWithInternship = new ArrayList<>();
        List<InternshipApplication> completedInternshipApplications = internshipApplicationRepository.findAllByIsDisabledFalse();
        completedInternshipApplications.forEach(internshipApplication -> {
            if (internshipApplication.statusIsCompleted()){
                InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
                Student student = internshipApplication.getStudent();
                String internshipOfferSession = internshipOffer.getSession();
                if (!studentsWithInternship.contains(student) && session.equals(internshipOfferSession))
                    studentsWithInternship.add(student);
            }
        });
        return studentsWithInternship.isEmpty() ? Optional.empty() : Optional.of(studentsWithInternship);
    }

    public Optional<List<Student>> getAllStudentsWaitingInterview(String session) {
        List<Student> studentsWaitingInterview = new ArrayList<>();
        List<InternshipApplication> internshipApplicationsWithoutInterviewDate =
                internshipApplicationRepository.findAllByStatusWaitingAndInterviewDateIsAfterNowAndIsDisabledFalse();

        internshipApplicationsWithoutInterviewDate.forEach(internshipApplication -> {
            Student student = internshipApplication.getStudent();
            InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
            String internshipOfferSession = internshipOffer.getSession();
            if (student.getSessions().contains(session) && session.equals(internshipOfferSession))
                studentsWaitingInterview.add(student);
        });
        return studentsWaitingInterview.isEmpty() ? Optional.empty() :
                Optional.of(studentsWaitingInterview.stream().distinct().collect(Collectors.toList()));
    }

    public Optional<List<Student>> getAllStudentsWithoutStudentEvaluation(String session){
        List<Internship> internshipListWithoutStudentEvaluation =
                internshipRepository.findByStudentEvaluationNullAndIsDisabledFalse();
        List<Student> studentList = getAllStudentsFromInternships(internshipListWithoutStudentEvaluation, session);
        return studentList.isEmpty() ? Optional.empty() : Optional.of(studentList);
    }

    public Optional<List<Student>> getAllStudentsWithoutEnterpriseEvaluation(String session){
        List<Internship> internshipListWithoutEnterpriseEvaluation =
                internshipRepository.findByEnterpriseEvaluationNullAndIsDisabledFalse();
        List<Student> studentList = getAllStudentsFromInternships(internshipListWithoutEnterpriseEvaluation, session);
        return studentList.isEmpty() ? Optional.empty() : Optional.of(studentList);
    }

    private List<Student> getAllStudentsFromInternships(List<Internship> internshipListWithoutStudentEvaluation, String session){
        List<Student> studentList = new ArrayList<>();
        internshipListWithoutStudentEvaluation .forEach(internship -> {
            InternshipApplication internshipApplication = internship.getInternshipApplication();
            InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
            String internshipOfferSession = internshipOffer.getSession();
            Student student = internshipApplication.getStudent();
            if(internshipApplication.statusIsCompleted() && session.equals(internshipOfferSession))
                studentList.add(student);
        });
        return studentList.stream().distinct().collect(Collectors.toList());
    }

    public Optional<TreeSet<String>> getAllNextSessionsOfInternshipOffersValidated() {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByIsValidTrueAndIsDisabledFalse();
        TreeSet<String> sessions = setNextSessionsOfInternshipOffers(internshipOffers);
        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions);
    }

    public Optional<TreeSet<String>> getAllNextSessionsOfInternshipOffersUnvalidated() {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByIsValidFalseAndIsDisabledFalse();
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

            if (internshipOfferYear > year || (WINTER_SESSION.equals(session) && internshipOfferYear == year))
                sessions.add(internshipOffer.getSession());
        });
        return sessions;
    }

    public Optional<TreeSet<String>> getAllSessionsOfInvalidInternshipOffers() {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByIsValidFalseAndIsDisabledFalse();
        TreeSet<String> sessions = setSessionsOfInternshipOffers(internshipOffers);
        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions);
    }

    public Optional<TreeSet<String>> getAllSessionsOfValidInternshipOffers() {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByIsValidTrueAndIsDisabledFalse();
        TreeSet<String> sessions = setSessionsOfInternshipOffers(internshipOffers);
        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions);
    }

    private TreeSet<String> setSessionsOfInternshipOffers(List<InternshipOffer> internshipOffers) {
        TreeSet<String> sessions = new TreeSet<>();
        internshipOffers.forEach(internshipOffer -> sessions.add(internshipOffer.getSession()));
        return (TreeSet<String>) sessions.descendingSet();
    }

    public Optional<PDFDocument> downloadInternshipOfferDocument(String id) {
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(id);
        Optional<PDFDocument> optionalDocument = Optional.empty();

        if(optionalInternshipOffer.isPresent()){
            PDFDocument pdfDocument = optionalInternshipOffer.get().getPDFDocument();
            if (pdfDocument != null)
                optionalDocument = Optional.of(optionalInternshipOffer.get().getPDFDocument());
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
}
