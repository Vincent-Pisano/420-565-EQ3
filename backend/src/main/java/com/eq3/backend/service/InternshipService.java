package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import com.itextpdf.text.DocumentException;
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
import java.util.Optional;

@Service
public class InternshipService {

    private final Logger logger;

    private final InternshipApplicationRepository internshipApplicationRepository;
    private final InternshipRepository internshipRepository;
    private final InternshipManagerRepository internshipManagerRepository;

    InternshipService(InternshipApplicationRepository internshipApplicationRepository,
                      InternshipRepository internshipRepository,
                      InternshipManagerRepository internshipManagerRepository) {
        this.logger = LoggerFactory.getLogger(InternshipService.class);
        this.internshipApplicationRepository = internshipApplicationRepository;
        this.internshipRepository = internshipRepository;
        this.internshipManagerRepository = internshipManagerRepository;
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
                        + " at extractDocument in InternshipService.depositStudentEvaluation : " + e.getMessage());
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
                        + " at extractDocument in InternshipService.depositEnterpriseEvaluation : " + e.getMessage());
            }
        });
        return optionalInternship.map(internshipRepository::save);
    }
}

