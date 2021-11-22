package com.pravdin.democassandra.controller;

import com.pravdin.democassandra.model.InternshipApplication;
import com.pravdin.democassandra.model.InternshipOffer;
import com.pravdin.democassandra.service.InternshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
public class InternshipController {

    private final InternshipService service;

    public InternshipController(InternshipService service) {
        this.service = service;
    }

    @PostMapping("/add/internshipOffer")
    public ResponseEntity<InternshipOffer> postInternshipOffer(@RequestBody InternshipOffer internshipOffer) {
        return service.postInternshipOffer(internshipOffer)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.CREATED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/get/unvalidated/internshipOffer")
    public ResponseEntity<List<InternshipOffer>> getUnvalidatedInternshipOffers() {
        return service.getUnvalidatedInternshipOffers()
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.CREATED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/validate/internshipOffer/{id}")
    public ResponseEntity<InternshipOffer> validateInternshipOffer(@PathVariable String id) {
        return service.validateInternshipOffer(id)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.CREATED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/add/internshipApplication/{student_id}/{offer_id}")
    public ResponseEntity<InternshipApplication> postInternshipApplication(@PathVariable String student_id, @PathVariable String offer_id) {
        return service.postInternshipApplication(student_id, offer_id)
                .map(_internshipApplication -> ResponseEntity.status(HttpStatus.CREATED).body(_internshipApplication))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/get/validated/internshipOffer")
    public ResponseEntity<List<InternshipOffer>> getValidatedInternshipOffers() {
        return service.getValidatedInternshipOffers()
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.CREATED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
