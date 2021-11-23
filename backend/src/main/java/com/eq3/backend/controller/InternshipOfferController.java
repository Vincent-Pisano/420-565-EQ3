package com.eq3.backend.controller;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.service.InternshipOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3006")
public class InternshipOfferController {

    private final InternshipOfferService service;

    public InternshipOfferController(InternshipOfferService service) {
        this.service = service;
    }

    @PostMapping(value = "/save/internshipOffer",
            produces = "application/json;charset=utf8",
            consumes = { "multipart/form-data" })
    public ResponseEntity<InternshipOffer> saveInternshipOffer(@RequestPart(name = "internshipOffer") String internshipOfferJson,
                                                               @RequestPart(name = "document", required=false) MultipartFile multipartFile) {
        return service.saveInternshipOffer(internshipOfferJson, multipartFile)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.CREATED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/internshipOffer/{session}/{workField}")
    public ResponseEntity<List<InternshipOffer>> getAllInternshipOfferByWorkField(@PathVariable Department workField, @PathVariable String session) {
        return service.getAllInternshipOfferByWorkField(workField, session)
                .map(_internshipOffers -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffers))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/internshipOffer/unvalidated/{session}")
    public ResponseEntity<List<InternshipOffer>> getAllUnvalidatedInternshipOffer(@PathVariable String session) {
        return service.getAllUnvalidatedInternshipOffer(session)
                .map(_internshipOffers -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffers))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/internshipOffer/validated/{session}")
    public ResponseEntity<List<InternshipOffer>> getAllValidatedInternshipOffer(@PathVariable String session) {
        return service.getAllValidatedInternshipOffer(session)
                .map(_internshipOffers -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffers))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/internshipOffer/{session}/monitor/{id}")
    public ResponseEntity<List<InternshipOffer>> getAllInternshipOfferOfMonitor(@PathVariable String session, @PathVariable String id) {
        return service.getAllInternshipOfferOfMonitor(session, id)
                .map(_internshipOffers -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffers))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/validate/internshipOffer/{idOffer}")
    public ResponseEntity<InternshipOffer> validateInternshipOffer(@PathVariable String idOffer) {
        return service.validateInternshipOffer(idOffer)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
