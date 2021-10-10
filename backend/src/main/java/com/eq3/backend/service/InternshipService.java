package com.eq3.backend.service;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.model.PDFDocument;
import com.eq3.backend.model.Student;
import com.eq3.backend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.eq3.backend.utils.Utils.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class InternshipService {

    private final Logger logger;

    private final StudentRepository studentRepository;
    private final InternshipOfferRepository internshipOfferRepository;

    InternshipService(StudentRepository studentRepository,
                   InternshipOfferRepository internshipOfferRepository) {

        this.logger = LoggerFactory.getLogger(BackendService.class);
        this.studentRepository = studentRepository;
        this.internshipOfferRepository = internshipOfferRepository;
    }

    public Optional<InternshipOffer> saveInternshipOffer(String internshipOfferJson, MultipartFile multipartFile) {
        InternshipOffer internshipOffer = null;
        try {
            internshipOffer = getInternshipOffer(internshipOfferJson, multipartFile);
        } catch (IOException e) {
            logger.error("Couldn't map the string internshipOffer to InternshipOffer.class at " +
                    "saveInternshipOffer in BackendService : " + e.getMessage());
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

    public Optional<List<InternshipOffer>> getAllInternshipOfferByWorkField(Department workField) {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByWorkFieldAndIsValidTrue(workField);
        internshipOffers.forEach(internshipOffer -> internshipOffer.setPDFDocument(
                internshipOffer.getPDFDocument() != null ? new PDFDocument() : null)
        );
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<List<InternshipOffer>> getAllUnvalidatedInternshipOffer() {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByIsValidFalse();
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<Student> applyInternshipOffer(String studentUsername, InternshipOffer internshipOffer) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);

        optionalStudent.ifPresent(student -> {
            List<InternshipOffer> internshipOffersList = student.getInternshipOffers();

            if (internshipOffer.getPDFDocument() != null) {
                PDFDocument PDFDocument = internshipOffer.getPDFDocument();
                PDFDocument.setContent(null);
                internshipOffer.setPDFDocument(PDFDocument);
            }

            internshipOffersList.add(internshipOffer);
            student.setInternshipOffers(internshipOffersList);
            studentRepository.save(student);
        });

        return cleanUpStudentCVList(optionalStudent);
    }

    public Optional<InternshipOffer> validateInternshipOffer(String idOffer) {
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(idOffer);
        optionalInternshipOffer.ifPresent(internshipOffer -> {
            internshipOffer.setIsValid(true);
        });
        return optionalInternshipOffer.map(internshipOfferRepository::save);
    }
}
