package com.eq3.backend.service;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.model.PDFDocument;
import com.eq3.backend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.Utils.extractDocument;
import static com.eq3.backend.utils.Utils.getSessionFromDate;

@Service
public class InternshipOfferService {

    private final Logger logger;
    private final InternshipOfferRepository internshipOfferRepository;

    InternshipOfferService(InternshipOfferRepository internshipOfferRepository) {
        this.logger = LoggerFactory.getLogger(InternshipOfferService.class);
        this.internshipOfferRepository = internshipOfferRepository;
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

    public Optional<InternshipOffer> validateInternshipOffer(String idOffer) {
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(idOffer);
        optionalInternshipOffer.ifPresent(internshipOffer -> internshipOffer.setIsValid(true));
        return optionalInternshipOffer.map(internshipOfferRepository::save);
    }

}