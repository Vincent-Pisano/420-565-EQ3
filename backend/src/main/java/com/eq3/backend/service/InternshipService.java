package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.eq3.backend.generator.GenerateContract.*;
import static com.eq3.backend.utils.Utils.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    InternshipService(StudentRepository studentRepository,
                   InternshipOfferRepository internshipOfferRepository,
                   InternshipApplicationRepository internshipApplicationRepository,
                   InternshipRepository internshipRepository,
                   InternshipManagerRepository internshipManagerRepository
    ) {
        this.logger = LoggerFactory.getLogger(BackendService.class);
        this.studentRepository = studentRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.internshipApplicationRepository = internshipApplicationRepository;
        this.internshipRepository = internshipRepository;
        this.internshipManagerRepository = internshipManagerRepository;
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
            pdfDocument.setName("Contract.pdf");
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

    public Optional<List<InternshipOffer>> getAllInternshipOfferByWorkField(Department workField) {
        List<InternshipOffer> internshipOffers =
                internshipOfferRepository.findAllByWorkFieldAndIsValidTrueAndIsDisabledFalse(workField);
        internshipOffers.forEach(internshipOffer -> internshipOffer.setPDFDocument(
                internshipOffer.getPDFDocument() != null ? new PDFDocument() : null)
        );
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<List<InternshipOffer>> getAllInternshipOfferOfMonitor(String idMonitor) {
        List<InternshipOffer> internshipOffers =
                internshipOfferRepository.findAllByMonitor_IdAndIsDisabledFalse(idMonitor);

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

    public Optional<List<InternshipApplication>> getAllInternshipApplicationOfStudent(String studentUsername) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        List<InternshipApplication> internshipApplications = new ArrayList<>();

        if (optionalStudent.isPresent())
            internshipApplications = internshipApplicationRepository.findAllByStudentAndIsDisabledFalse(optionalStudent.get());

        return internshipApplications.isEmpty() ? Optional.empty() : Optional.of(internshipApplications);
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
}
