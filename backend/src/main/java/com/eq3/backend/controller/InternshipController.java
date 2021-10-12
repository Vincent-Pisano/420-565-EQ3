package com.eq3.backend.controller;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.service.InternshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3006")
public class InternshipController {

    private final InternshipService service;

    public InternshipController(InternshipService service) {
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

    @GetMapping("/getAll/internshipOffer/{workField}")
    public ResponseEntity<List<InternshipOffer>> getAllInternshipOfferByWorkField(@PathVariable Department workField) {
        return service.getAllInternshipOfferByWorkField(workField)
                .map(_internshipOffers -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffers))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/internshipOffer/unvalidated")
    public ResponseEntity<List<InternshipOffer>> getAllUnvalidatedInternshipOffer() {
        return service.getAllUnvalidatedInternshipOffer()
                .map(_internshipOffers -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffers))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/internshipApplication/{username}")
    public ResponseEntity<List<InternshipApplication>> getAllInternshipApplicationOfStudent(@PathVariable String username) {
        return service.getAllInternshipApplicationOfStudent(username)
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/accepted/internshipApplication")
    public ResponseEntity<List<InternshipApplication>> getAllAcceptedInternshipApplications() {
        return service.getAllAcceptedInternshipApplications()
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/apply/internshipOffer/{username}")
    public ResponseEntity<InternshipApplication> applyInternshipOffer(@PathVariable String username, @RequestBody InternshipOffer internshipOffer) {
        return service.applyInternshipOffer(username, internshipOffer)
                .map(_internshipApplication -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplication))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/save/internshipOffer/validate/{idOffer}")
    public ResponseEntity<InternshipOffer> validateInternshipOffer(@PathVariable String idOffer) {
        return service.validateInternshipOffer(idOffer)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/update/internshipApplication")
    public ResponseEntity<InternshipApplication> updateInternshipApplication(@RequestBody InternshipApplication internshipApplication) {
        return service.updateInternshipApplication(internshipApplication)
                .map(_internshipApplication -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplication))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
