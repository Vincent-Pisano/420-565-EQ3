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

    public Optional<List<InternshipOffer>> getAllUnvalidatedInternshipOffer() {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByIsValidFalseAndIsDisabledFalse();
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<List<InternshipOffer>> getAllValidatedInternshipOffer() {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByIsValidTrueAndIsDisabledFalse();
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
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.addTo(manager.getEmail());
                helper.setSubject("Un étudiant vient d'appliquer à une offre");
                helper.setText("L'étudiant " + student.getFirstName() + " " + student.getFirstName() + " vient d'appliquer à l'offre : " + "\n" + offer.getJobName() + "\n" + offer.getDescription());

                mailSender.send(message);
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
        }
    }

    private void sendEmailToGSWhenStudentGetsInterviewed(InternshipManager manager) {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of("UTC"));
        ZonedDateTime tomorrow = today.plusDays(1);
        List<InternshipApplication> internshipApplications = internshipApplicationRepository.findByInterviewDateBetweenAndIsDisabledFalse(Date.from(today.toInstant()), Date.from(tomorrow.toInstant()));
            for (InternshipApplication currentInternship : internshipApplications) {
                try {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    Student currentStudent = currentInternship.getStudent();
                    helper.addTo(manager.getEmail());
                    helper.setSubject("Convocation à une entrevue d'un étudiant");
                    helper.setText("L'étudiant " + currentStudent.getFirstName() + " " + currentStudent.getFirstName() +
                            " va être convoqué a une entrevue de stage aujourd'hui!");
                    mailSender.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    private void sendEmailToMonitorAboutEvaluation() {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of("UTC"));
        ZonedDateTime tomorrow = today.plusDays(1);
        List<Internship> internships = internshipRepository.findByStudentEvaluationNullAndIsDisabledFalse();
        for (Internship currentInternship : internships) {
            InternshipApplication internshipApplication = currentInternship.getInternshipApplication();
            Student currentStudent = internshipApplication.getStudent();
            InternshipOffer currentOffer = internshipApplication.getInternshipOffer();
            Monitor currentMonitor = currentOffer.getMonitor();
            ZonedDateTime endDateIn2Weeks = ZonedDateTime.ofInstant(currentOffer.getEndDate().toInstant(), ZoneId.of("UTC")).minusDays(14).plusMinutes(1);
            if (endDateIn2Weeks.isAfter(today) && endDateIn2Weeks.isBefore(tomorrow)) {
                try {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    helper.addTo(currentMonitor.getEmail());
                    helper.setSubject("Remise de l'évaluation de l'étudiant");
                    helper.setText("Bonjour " + currentMonitor.getFirstName() + " " + currentMonitor.getFirstName() + "\n" +
                            "vous devez remettre l'évaluation de l'étudiant : " +
                            currentStudent.getFirstName() + " " + currentStudent.getFirstName() + "\n" +
                            "d'ici deux semaines.");
                    mailSender.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendEmailToSupervisorAboutEvaluation() {
        ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of("UTC"));
        ZonedDateTime tomorrow = today.plusDays(1);
        List<Internship> internships = internshipRepository.findByEnterpriseEvaluationNullAndIsDisabledFalse();
        for (Internship currentInternship : internships) {
            InternshipApplication internshipApplication = currentInternship.getInternshipApplication();
            Student currentStudent = internshipApplication.getStudent();
            Supervisor currentSupervisor = currentStudent.getSupervisorMap().get(getSessionFromDate(Date.from(today.toInstant())));
            InternshipOffer currentOffer = internshipApplication.getInternshipOffer();
            Monitor currentMonitor = currentOffer.getMonitor();
            ZonedDateTime endDateIn2Weeks = ZonedDateTime.ofInstant(currentOffer.getEndDate().toInstant(), ZoneId.of("UTC")).minusDays(14).plusMinutes(1);
            if (endDateIn2Weeks.isAfter(today) && endDateIn2Weeks.isBefore(tomorrow)) {
                try {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    helper.addTo(currentSupervisor.getEmail());
                    helper.setSubject("Remise de l'évaluation de l'entreprise");
                    helper.setText("Bonjour " + currentSupervisor.getFirstName() + " " + currentSupervisor.getFirstName() + "\n" +
                            "vous devez remettre l'évaluation de l'entreprise : " +
                            currentMonitor.getEnterpriseName() + "\n" +
                            "d'ici deux semaines.");
                    mailSender.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Configuration
    @EnableScheduling
    @ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
    static
    class SchedulingConfiguration {

    }
}

