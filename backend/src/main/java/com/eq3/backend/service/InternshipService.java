package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.eq3.backend.generator.GenerateContract.*;
import static com.eq3.backend.utils.Utils.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.List;
import java.util.Optional;

@Service
public class InternshipService {

    private final Logger logger;

    private final StudentRepository studentRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final InternshipApplicationRepository internshipApplicationRepository;
    private final InternshipRepository internshipRepository;
    private final InternshipManagerRepository internshipManagerRepository;
    private final JavaMailSender mailSender;

    InternshipService(StudentRepository studentRepository,
                      InternshipOfferRepository internshipOfferRepository,
                      InternshipApplicationRepository internshipApplicationRepository,
                      InternshipRepository internshipRepository,
                      InternshipManagerRepository internshipManagerRepository,
                      JavaMailSender mailSender) {
        this.logger = LoggerFactory.getLogger(BackendService.class);
        this.studentRepository = studentRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.internshipApplicationRepository = internshipApplicationRepository;
        this.internshipRepository = internshipRepository;
        this.internshipManagerRepository = internshipManagerRepository;
        this.mailSender = mailSender;
    }

    public Optional<InternshipOffer> saveInternshipOffer(String internshipOfferJson, MultipartFile multipartFile) {
        InternshipOffer internshipOffer = null;
        try {
            internshipOffer = getInternshipOffer(internshipOfferJson, multipartFile);
        } catch (IOException e) {
            logger.error("Couldn't map the string internshipOffer to InternshipOffer.class at " +
                    "saveInternshipOffer in InternshipService : " + e.getMessage());
        }
        return internshipOffer == null ? Optional.empty() :
                Optional.of(internshipOfferRepository.save(internshipOffer));
    }

    private InternshipOffer getInternshipOffer(String InternshipOfferJson, MultipartFile multipartFile) throws IOException {
        InternshipOffer internshipOffer = mapInternshipOffer(InternshipOfferJson);
        internshipOffer.setSession(getSessionFromDate(internshipOffer.getStartDate()));

        if (multipartFile != null) {
            try {
                internshipOffer.setPDFDocument(extractDocument(multipartFile));
            } catch (IOException e) {
                logger.error("Couldn't extract the document" + multipartFile.getOriginalFilename()
                        + " at extractDocument in InternshipService : " + e.getMessage());
            }
        }
        return internshipOffer;
    }

    private InternshipOffer mapInternshipOffer(String internshipOfferJson) throws IOException {
        return new ObjectMapper().readValue(internshipOfferJson, InternshipOffer.class);
    }

    public Optional<Internship> saveInternship(Internship internship) {
        internshipApplicationRepository.save(internship.getInternshipApplication());
        internship.setInternshipContract(getContract(internship));
        return Optional.of(internshipRepository.save(internship));
    }

    private PDFDocument getContract(Internship internship) {
        PDFDocument pdfDocument = new PDFDocument();
        Optional<InternshipManager> optionalInternshipManager = internshipManagerRepository.findByIsDisabledFalse();

        try{
            ByteArrayOutputStream baos = generatePdfContract(internship, optionalInternshipManager);
            pdfDocument.setName(CONTRACT_FILE_NAME);
            pdfDocument.setContent(new Binary(BsonBinarySubType.BINARY, baos.toByteArray()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return pdfDocument;
    }

    public Optional<Internship> getInternshipFromInternshipApplication(String idApplication) {
        return internshipRepository.findByInternshipApplication_Id(idApplication);
    }

    public Optional<List<InternshipOffer>> getAllInternshipOfferByWorkField(Department workField, String session) {
        List<InternshipOffer> internshipOffers =
                internshipOfferRepository.findAllByWorkFieldAndSessionAndIsValidTrueAndIsDisabledFalse(workField, session);
        internshipOffers.forEach(internshipOffer -> internshipOffer.setPDFDocument(
                internshipOffer.getPDFDocument() != null ? new PDFDocument() : null)
        );
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<List<InternshipOffer>> getAllInternshipOfferOfMonitor(String session, String idMonitor) {
        List<InternshipOffer> internshipOffers =
                internshipOfferRepository.findAllBySessionAndMonitor_IdAndIsDisabledFalse(session, idMonitor);

        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<List<InternshipOffer>> getAllUnvalidatedInternshipOffer(String session) {
        List<InternshipOffer> internshipOffers =
                internshipOfferRepository.findAllByIsValidFalseAndIsDisabledFalseAndSession(session);
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<List<InternshipOffer>> getAllValidatedInternshipOffer(String session) {
        List<InternshipOffer> internshipOffers =
                internshipOfferRepository.findAllByIsValidTrueAndIsDisabledFalseAndSession(session);
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<List<InternshipApplication>> getAllInternshipApplicationOfStudent(String session, String studentUsername) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        List<InternshipApplication> internshipApplicationsOfStudent = new ArrayList<>();

        optionalStudent.ifPresent(student -> {
            List<InternshipApplication> internshipApplications = internshipApplicationRepository.findAllByStudentAndIsDisabledFalse(student);
            internshipApplications.forEach(internshipApplication -> {
                InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
                if (session.equals(internshipOffer.getSession())) {
                    internshipApplicationsOfStudent.add(internshipApplication);
                }
            });
        });

        return internshipApplicationsOfStudent.isEmpty() ? Optional.empty() : Optional.of(internshipApplicationsOfStudent);
    }

    public Optional<List<InternshipApplication>> getAllCompletedInternshipApplicationOfStudent(String session, String studentUsername) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        List<InternshipApplication> internshipApplicationsOfStudentCompleted =
                optionalStudent
                        .map(student ->
                                getAllInternshipApplicationBySessionAndStatus(student, session, InternshipApplication.ApplicationStatus.COMPLETED))
                        .orElseGet(ArrayList::new);

        return internshipApplicationsOfStudentCompleted.isEmpty() ? Optional.empty() : Optional.of(internshipApplicationsOfStudentCompleted);
    }

    public Optional<List<InternshipApplication>> getAllWaitingInternshipApplicationOfStudent(String session, String studentUsername) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        List<InternshipApplication> internshipApplicationsOfStudentWaiting =
                optionalStudent
                    .map(student ->
                        getAllInternshipApplicationBySessionAndStatus(student, session, InternshipApplication.ApplicationStatus.WAITING))
                    .orElseGet(ArrayList::new);

        return internshipApplicationsOfStudentWaiting.isEmpty() ? Optional.empty() : Optional.of(internshipApplicationsOfStudentWaiting);
    }

    private List<InternshipApplication> getAllInternshipApplicationBySessionAndStatus(Student student, String session, InternshipApplication.ApplicationStatus status) {
        List<InternshipApplication> internshipApplicationsOfStudent = new ArrayList<>();

        List<InternshipApplication> internshipApplications =
                internshipApplicationRepository.findAllByStudentAndIsDisabledFalseAndStatus(student, status);
        internshipApplications.forEach(internshipApplication -> {
            InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();
            if (session.equals(internshipOffer.getSession())) {
                internshipApplicationsOfStudent.add(internshipApplication);
            }
        });
        return internshipApplicationsOfStudent;
    }

    public Optional<List<InternshipApplication>> getAllInternshipApplicationOfInternshipOffer(String id) {
        List<InternshipApplication> internshipApplications =
                internshipApplicationRepository.findAllByInternshipOffer_IdAndStatusIsNotAcceptedAndIsDisabledFalse(id);
        return internshipApplications.isEmpty() ? Optional.empty() : Optional.of(internshipApplications);
    }

    public Optional<List<InternshipApplication>> getAllAcceptedInternshipApplications() {
        List<InternshipApplication> internshipApplications =
                internshipApplicationRepository.findAllByStatusAndIsDisabledFalse(InternshipApplication.ApplicationStatus.ACCEPTED);
        return internshipApplications.isEmpty() ? Optional.empty() : Optional.of(internshipApplications);
    }

    public Optional<List<InternshipApplication>> getAllAcceptedInternshipApplicationsNextSessions() {
        List<InternshipOffer> internshipOffers =
                internshipOfferRepository.findAllByIsValidTrueAndIsDisabledFalseAndSession(getNextSessionFromDate(new Date()));
        List<InternshipApplication> internshipApplications =
                internshipApplicationRepository.findAllByIsDisabledFalseAndInternshipOfferInAndStatus(
                        internshipOffers, InternshipApplication.ApplicationStatus.ACCEPTED
                );

        return internshipApplications.isEmpty() ? Optional.empty() : Optional.of(internshipApplications);
    }

    public Optional<List<InternshipApplication>> getAllValidatedInternshipApplications() {
        List<InternshipApplication> internshipApplications =
                internshipApplicationRepository.findAllByStatusAndIsDisabledFalse(InternshipApplication.ApplicationStatus.VALIDATED);
        return internshipApplications.isEmpty() ? Optional.empty() : Optional.of(internshipApplications);
    }

    public Optional<InternshipApplication> applyInternshipOffer(String studentUsername, InternshipOffer internshipOffer) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        return optionalStudent.map(student -> createInternshipApplication(student, internshipOffer));
    }

    private InternshipApplication createInternshipApplication(Student student, InternshipOffer internshipOffer){
        InternshipApplication internshipApplication = new InternshipApplication();
        internshipApplication.setInternshipOffer(internshipOffer);
        internshipApplication.setStudent(student);
        sendEmailWhenStudentAppliesToNewInternshipOffer(student, internshipOffer);
        return internshipApplicationRepository.save(internshipApplication);
    }

    public Optional<InternshipOffer> validateInternshipOffer(String idOffer) {
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(idOffer);
        optionalInternshipOffer.ifPresent(internshipOffer -> internshipOffer.setIsValid(true));
        return optionalInternshipOffer.map(internshipOfferRepository::save);
    }

    public Optional<InternshipApplication> updateInternshipApplication(InternshipApplication internshipApplication) {
        Optional<InternshipApplication> optionalInternshipApplication =
                internshipApplicationRepository.findById(internshipApplication.getId());

        return optionalInternshipApplication.map(_internshipApplication ->
                internshipApplicationRepository.save(internshipApplication));
    }

    public Optional<Internship> signInternshipContractByMonitor(String idInternship) {
        Optional<Internship> optionalInternship = internshipRepository.findById(idInternship);

        optionalInternship.ifPresent(_internship -> {
            _internship.setSignedByMonitor(true);
            try {
                addMonitorSignatureToInternshipContract(_internship);
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        });
        return optionalInternship.map(internshipRepository::save);
    }

    private void addMonitorSignatureToInternshipContract(Internship _internship) throws DocumentException, IOException {
        PDFDocument contract = _internship.getInternshipContract();
        Binary pdfDocumentContent = contract.getContent();
        InternshipApplication internshipApplication = _internship.getInternshipApplication();
        InternshipOffer internshipOffer = internshipApplication.getInternshipOffer();

        ByteArrayOutputStream baos = signPdfContract(internshipOffer.getMonitor(), pdfDocumentContent.getData());
        contract.setContent(new Binary(BsonBinarySubType.BINARY, baos.toByteArray()));

        _internship.setInternshipContract(contract);
    }

    public Optional<Internship> signInternshipContractByStudent(String idInternship) {
        Optional<Internship> optionalInternship = internshipRepository.findById(idInternship);

        optionalInternship.ifPresent(_internship -> {
            _internship.setSignedByStudent(true);
            try {
                addStudentSignatureToInternshipContract(_internship);
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        });
        return optionalInternship.map(internshipRepository::save);
    }

    private void addStudentSignatureToInternshipContract(Internship _internship) throws DocumentException, IOException {
        PDFDocument contract = _internship.getInternshipContract();
        Binary pdfDocumentContent = contract.getContent();
        InternshipApplication internshipApplication = _internship.getInternshipApplication();

        ByteArrayOutputStream baos = signPdfContract(internshipApplication.getStudent(), pdfDocumentContent.getData());
        contract.setContent(new Binary(BsonBinarySubType.BINARY, baos.toByteArray()));

        _internship.setInternshipContract(contract);
    }

    public Optional<Internship> signInternshipContractByInternshipManager(String idInternship) {
        Optional<Internship> optionalInternship = internshipRepository.findById(idInternship);

        optionalInternship.ifPresent(_internship -> {
            InternshipApplication internshipApplication = _internship.getInternshipApplication();
            internshipApplication.setStatus(InternshipApplication.ApplicationStatus.COMPLETED);
            _internship.setSignedByInternshipManager(true);

            try {
                addInternshipManagerSignatureToInternshipContract(_internship);
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }

            internshipApplicationRepository.save(internshipApplication);
        });
        return optionalInternship.map(internshipRepository::save);
    }

    private void addInternshipManagerSignatureToInternshipContract(Internship _internship) throws DocumentException, IOException {
        Optional<InternshipManager> optionalInternshipManager = internshipManagerRepository.findByIsDisabledFalse();
        if (optionalInternshipManager.isPresent()){
            PDFDocument contract = _internship.getInternshipContract();
            Binary pdfDocumentContent = contract.getContent();

            ByteArrayOutputStream baos = signPdfContract(optionalInternshipManager.get(), pdfDocumentContent.getData());
            contract.setContent(new Binary(BsonBinarySubType.BINARY, baos.toByteArray()));

            _internship.setInternshipContract(contract);
        }
    }

    public Optional<Internship> depositStudentEvaluation(String idInternship, MultipartFile multipartFile) {
        Optional<Internship> optionalInternship = internshipRepository.findById(idInternship);
        optionalInternship.ifPresent(internship -> {
            try {
                internship.setStudentEvaluation(extractDocument(multipartFile));
            } catch (IOException e) {
                logger.error("Couldn't extract the document" + multipartFile.getOriginalFilename()
                        + " at extractDocument in InternshipService : " + e.getMessage());
            }
        });
        return optionalInternship.map(internshipRepository::save);
    }

    public Optional<Internship> depositEnterpriseEvaluation(String idInternship, MultipartFile multipartFile) {
        Optional<Internship> optionalInternship = internshipRepository.findById(idInternship);
        optionalInternship.ifPresent(internship -> {
            try {
                internship.setEnterpriseEvaluation(extractDocument(multipartFile));
            } catch (IOException e) {
                logger.error("Couldn't extract the document" + multipartFile.getOriginalFilename()
                        + " at extractDocument in InternshipService : " + e.getMessage());
            }
        });
        return optionalInternship.map(internshipRepository::save);
    }

    private void sendEmailWhenStudentAppliesToNewInternshipOffer(Student student, InternshipOffer offer) {
        Optional<InternshipManager> optionalManager = internshipManagerRepository.findByIsDisabledFalse();
        if (optionalManager.isPresent()) {
            InternshipManager manager = optionalManager.get();
            try {
                generateEmailWhenStudentAppliesToNewInternshipOffer(student, offer, manager);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    @Scheduled(cron = "0 * * * * *")
    void sendMails(){
        Optional<InternshipManager> optionalManager = internshipManagerRepository.findByIsDisabledFalse();
        if (optionalManager.isPresent()) {
            InternshipManager manager = optionalManager.get();
            sendEmailToGSWhenStudentGetsInterviewed(manager);
            sendEmailToMonitorAboutEvaluation();
            sendEmailToSupervisorAboutEvaluation();
            sendEmailToStudentAboutInterviewOneWeekBefore();
        }
    }

    private void sendEmailToGSWhenStudentGetsInterviewed(InternshipManager manager) {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of(UTC_TIME_ZONE)).minusMinutes(1);
        ZonedDateTime tomorrow = today.plusDays(1);
        List<InternshipApplication> internshipApplications = internshipApplicationRepository.findByInterviewDateBetweenAndIsDisabledFalse(Date.from(today.toInstant()), Date.from(tomorrow.toInstant()));
        internshipApplications.forEach(internshipApplication -> {
            try {
                generateEmailWhenStudentsGetsInterviewed(manager, internshipApplication);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void sendEmailToMonitorAboutEvaluation() {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of(UTC_TIME_ZONE));
        ZonedDateTime tomorrow = today.plusDays(1);
        List<Internship> internships = internshipRepository.findByStudentEvaluationNullAndIsDisabledFalse();
        internships.forEach(internship -> {
            InternshipApplication internshipApplication = internship.getInternshipApplication();
            Student currentStudent = internshipApplication.getStudent();
            InternshipOffer currentOffer = internshipApplication.getInternshipOffer();
            Monitor currentMonitor = currentOffer.getMonitor();
            ZonedDateTime endDateIn2Weeks = ZonedDateTime.ofInstant(currentOffer.getEndDate().toInstant(), ZoneId.of(UTC_TIME_ZONE)).minusDays(14).plusMinutes(1);
            if (endDateIn2Weeks.isAfter(today) && endDateIn2Weeks.isBefore(tomorrow)) {
                try {
                    generateEmailForMonitorAboutEvaluation(currentStudent, currentMonitor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendEmailToSupervisorAboutEvaluation() {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of(UTC_TIME_ZONE));
        ZonedDateTime tomorrow = today.plusDays(1);
        List<Internship> internships = internshipRepository.findByEnterpriseEvaluationNullAndIsDisabledFalse();
        internships.forEach(internship -> {
            InternshipApplication internshipApplication = internship.getInternshipApplication();
            Student currentStudent = internshipApplication.getStudent();

            InternshipOffer currentOffer = internshipApplication.getInternshipOffer();
            Monitor currentMonitor = currentOffer.getMonitor();
            Supervisor currentSupervisor = currentStudent.getSupervisorMap().get(currentOffer.getSession());
            ZonedDateTime endDateIn2Weeks = ZonedDateTime.ofInstant(currentOffer.getEndDate().toInstant(), ZoneId.of(UTC_TIME_ZONE)).minusDays(14).plusMinutes(1);

            if (endDateIn2Weeks.isAfter(today) && endDateIn2Weeks.isBefore(tomorrow)) {
                try {
                    generateEmailForSupervisorAboutEvaluation(currentSupervisor, currentMonitor);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendEmailToStudentAboutInterviewOneWeekBefore() {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of(UTC_TIME_ZONE)).plusDays(7).minusMinutes(1);
        ZonedDateTime tomorrow = today.plusDays(1);
        List<InternshipApplication> internshipApplications = internshipApplicationRepository.findByInterviewDateBetweenAndIsDisabledFalse(Date.from(today.toInstant()), Date.from(tomorrow.toInstant()));
        internshipApplications.forEach(internshipApplication -> {
            Student currentStudent = internshipApplication.getStudent();
            InternshipOffer currentInternshipOffer = internshipApplication.getInternshipOffer();
            try {
                generateEmailToStudentAboutInterviewOneWeekBefore(currentInternshipOffer, currentStudent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void generateEmailWhenStudentAppliesToNewInternshipOffer(Student student, InternshipOffer offer, InternshipManager manager) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.addTo(manager.getEmail());
        helper.setSubject("Un étudiant vient d'appliquer à une offre");
        helper.setText("L'étudiant " + student.getFirstName() + " " + student.getLastName() + " vient d'appliquer à l'offre : " + "\n" + offer.getJobName() + "\n" + offer.getDescription());

        mailSender.send(message);
    }

    private void generateEmailToStudentAboutInterviewOneWeekBefore(InternshipOffer internshipOffer, Student student) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.addTo(student.getEmail());
        helper.setSubject(EMAIL_SUBJECT_INTERVIEW_ONE_WEEK_BEFORE_STUDENT);
        helper.setText(getEmailTextStudentAboutInterviewOneWeekBefore(internshipOffer));
        mailSender.send(message);
    }

    private void generateEmailForSupervisorAboutEvaluation(Supervisor currentSupervisor, Monitor currentMonitor) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.addTo(currentSupervisor.getEmail());
        helper.setSubject(EMAIL_SUBJECT_SUPERVISOR_ABOUT_EVALUATION);
        helper.setText(getEmailTextForSupervisorAboutEvaluation(currentSupervisor, currentMonitor));
        mailSender.send(message);
    }


    private void generateEmailForMonitorAboutEvaluation(Student currentStudent, Monitor currentMonitor) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.addTo(currentMonitor.getEmail());
        helper.setSubject(EMAIL_SUBJECT_MONITOR_ABOUT_EVALUATION);
        helper.setText(getEmailTextForMonitorAboutEvaluation(currentStudent, currentMonitor));
        mailSender.send(message);
    }

    private void generateEmailWhenStudentsGetsInterviewed(InternshipManager manager, InternshipApplication internshipApplication) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Student currentStudent = internshipApplication.getStudent();
        helper.addTo(manager.getEmail());
        helper.setSubject(EMAIL_SUBJECT_INTERVIEW_STUDENT);
        helper.setText(getEmailTextWhenStudentsGetsInterviewed(currentStudent));
        mailSender.send(message);
    }


    @Configuration
    @EnableScheduling
    @ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
    static
    class SchedulingConfiguration {

    }
}

