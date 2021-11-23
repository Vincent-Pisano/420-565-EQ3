package com.eq3.backend.controller;

import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.service.InternshipApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eq3.backend.utils.UtilsController.InternshipOfferControllerUrl.*;
import static com.eq3.backend.utils.UtilsController.CROSS_ORIGIN_ALLOWED;
import static com.eq3.backend.utils.UtilsController.APPLICATION_JSON_AND_CHARSET_UTF8;
import static com.eq3.backend.utils.UtilsController.MULTI_PART_FROM_DATA;
import static com.eq3.backend.utils.UtilsController.REQUEST_PART_DOCUMENT;
import static com.eq3.backend.utils.UtilsController.REQUEST_PART_INTERNSHIP_OFFER;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class InternshipApplicationController {

    private final InternshipApplicationService service;

    public InternshipApplicationController(InternshipApplicationService service) {
        this.service = service;
    }

    @GetMapping("/getAll/internshipApplication/{session}/student/{username}")
    public ResponseEntity<List<InternshipApplication>> getAllInternshipApplicationOfStudent(@PathVariable String session, @PathVariable String username) {
        return service.getAllInternshipApplicationOfStudent(session, username)
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/internshipApplication/completed/{session}/student/{username}")
    public ResponseEntity<List<InternshipApplication>> getAllCompletedInternshipApplicationOfStudent(@PathVariable String session, @PathVariable String username) {
        return service.getAllCompletedInternshipApplicationOfStudent(session, username)
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/internshipApplication/waiting/{session}/student/{username}")
    public ResponseEntity<List<InternshipApplication>> getAllWaitingInternshipApplicationOfStudent(@PathVariable String session, @PathVariable String username) {
        return service.getAllWaitingInternshipApplicationOfStudent(session, username)
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/internshipApplication/internshipOffer/{id}")
    public ResponseEntity<List<InternshipApplication>> getAllInternshipApplicationOfInternshipOffer(@PathVariable String id) {
        return service.getAllInternshipApplicationOfInternshipOffer(id)
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/accepted/internshipApplication")
    public ResponseEntity<List<InternshipApplication>> getAllAcceptedInternshipApplications() {
        return service.getAllAcceptedInternshipApplications()
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value="/getAll/accepted/internshipApplications/current/and/next/sessions")
    public ResponseEntity<List<InternshipApplication>> getAllAcceptedInternshipApplicationsNextSessions(){
        return service.getAllAcceptedInternshipApplicationsNextSessions()
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/validated/internshipApplication")
    public ResponseEntity<List<InternshipApplication>> getAllValidatedInternshipApplications() {
        return service.getAllValidatedInternshipApplications()
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/apply/internshipOffer/{username}")
    public ResponseEntity<InternshipApplication> applyInternshipOffer(@PathVariable String username,
                                                                      @RequestBody InternshipOffer internshipOffer) {
        return service.applyInternshipOffer(username, internshipOffer)
                .map(_internshipApplication -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplication))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/update/internshipApplication")
    public ResponseEntity<InternshipApplication> updateInternshipApplication(@RequestBody InternshipApplication internshipApplication) {
        return service.updateInternshipApplication(internshipApplication)
                .map(_internshipApplication -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplication))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
