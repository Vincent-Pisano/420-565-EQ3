package com.eq3.backend.controller;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.service.InternshipOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.eq3.backend.utils.UtilsController.InternshipOfferControllerUrl.*;
import static com.eq3.backend.utils.UtilsController.CROSS_ORIGIN_ALLOWED;
import static com.eq3.backend.utils.UtilsController.APPLICATION_JSON_AND_CHARSET_UTF8;
import static com.eq3.backend.utils.UtilsController.MULTI_PART_FROM_DATA;
import static com.eq3.backend.utils.UtilsController.REQUEST_PART_DOCUMENT;
import static com.eq3.backend.utils.UtilsController.REQUEST_PART_INTERNSHIP_OFFER;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class InternshipOfferController {

    private final InternshipOfferService service;

    public InternshipOfferController(InternshipOfferService service) {
        this.service = service;
    }

    @PostMapping(value = URL_SAVE_INTERNSHIP_OFFER,
            produces = APPLICATION_JSON_AND_CHARSET_UTF8,
            consumes = { MULTI_PART_FROM_DATA })
    public ResponseEntity<InternshipOffer> saveInternshipOffer(@RequestPart(name = REQUEST_PART_INTERNSHIP_OFFER) String internshipOfferJson,
                                                               @RequestPart(name = REQUEST_PART_DOCUMENT, required=false) MultipartFile multipartFile) {
        return service.saveInternshipOffer(internshipOfferJson, multipartFile)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.CREATED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_INTERNSHIP_OFFERS_BY_SESSION_AND_WORK_FIELD)
    public ResponseEntity<List<InternshipOffer>> getAllInternshipOfferByWorkField(@PathVariable Department workField, @PathVariable String session) {
        return service.getAllInternshipOfferByWorkField(workField, session)
                .map(_internshipOffers -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffers))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_UNVALIDATED_INTERNSHIP_OFFERS_BY_SESSION)
    public ResponseEntity<List<InternshipOffer>> getAllUnvalidatedInternshipOffer(@PathVariable String session) {
        return service.getAllUnvalidatedInternshipOffer(session)
                .map(_internshipOffers -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffers))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_VALIDATED_INTERNSHIP_OFFERS_BY_SESSION)
    public ResponseEntity<List<InternshipOffer>> getAllValidatedInternshipOffer(@PathVariable String session) {
        return service.getAllValidatedInternshipOffer(session)
                .map(_internshipOffers -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffers))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_INTERNSHIP_OFFERS_OF_MONITOR_BY_SESSION)
    public ResponseEntity<List<InternshipOffer>> getAllInternshipOfferOfMonitor(@PathVariable String session, @PathVariable String id) {
        return service.getAllInternshipOfferOfMonitor(session, id)
                .map(_internshipOffers -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffers))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_VALIDATE_INTERNSHIP_OFFER)
    public ResponseEntity<InternshipOffer> validateInternshipOffer(@PathVariable String idOffer) {
        return service.validateInternshipOffer(idOffer)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
