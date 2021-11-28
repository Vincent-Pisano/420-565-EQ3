package com.eq3.backend.controller;

import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.service.InternshipApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eq3.backend.utils.UtilsController.InternshipApplicationControllerUrl.*;
import static com.eq3.backend.utils.UtilsController.CROSS_ORIGIN_ALLOWED;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class InternshipApplicationController {

    private final InternshipApplicationService service;

    public InternshipApplicationController(InternshipApplicationService service) {
        this.service = service;
    }

    @PostMapping(URL_APPLY_INTERNSHIP_OFFER)
    public ResponseEntity<InternshipApplication> applyInternshipOffer(@PathVariable String username,
                                                                      @RequestBody InternshipOffer internshipOffer) {
        return service.applyInternshipOffer(username, internshipOffer)
                .map(_internshipApplication -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplication))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_INTERNSHIP_APPLICATIONS_OF_STUDENT_BY_SESSION)
    public ResponseEntity<List<InternshipApplication>> getAllInternshipApplicationOfStudent(@PathVariable String session, @PathVariable String username) {
        return service.getAllInternshipApplicationOfStudent(session, username)
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_COMPLETED_INTERNSHIP_APPLICATIONS_OF_STUDENT_BY_SESSION)
    public ResponseEntity<List<InternshipApplication>> getAllCompletedInternshipApplicationOfStudent(@PathVariable String session, @PathVariable String username) {
        return service.getAllCompletedInternshipApplicationOfStudent(session, username)
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_WAITING_INTERNSHIP_APPLICATIONS_OF_STUDENT_BY_SESSION)
    public ResponseEntity<List<InternshipApplication>> getAllWaitingInternshipApplicationOfStudent(@PathVariable String session, @PathVariable String username) {
        return service.getAllWaitingInternshipApplicationOfStudent(session, username)
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_INTERNSHIP_APPLICATIONS_OF_INTERNSHIP_OFFER)
    public ResponseEntity<List<InternshipApplication>> getAllInternshipApplicationOfInternshipOffer(@PathVariable String id) {
        return service.getAllInternshipApplicationOfInternshipOffer(id)
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_ACCEPTED_INTERNSHIP_APPLICATIONS)
    public ResponseEntity<List<InternshipApplication>> getAllAcceptedInternshipApplications() {
        return service.getAllAcceptedInternshipApplications()
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value=URL_GET_ALL_ACCEPTED_INTERNSHIP_APPLICATIONS_CURRENT_AND_NEXT_SESSIONS)
    public ResponseEntity<List<InternshipApplication>> getAllAcceptedInternshipApplicationsNextSessions(){
        return service.getAllAcceptedInternshipApplicationsNextSessions()
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_VALIDATED_INTERNSHIP_APPLICATIONS)
    public ResponseEntity<List<InternshipApplication>> getAllValidatedInternshipApplications() {
        return service.getAllValidatedInternshipApplications()
                .map(_internshipApplications -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplications))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_INTERNSHIP_OFFERS_NOT_APPLIED_BY_SESSION_AND_USER)
    public ResponseEntity<List<InternshipOffer>> getAllInternshipOfferNotAppliedOfStudentBySession(@PathVariable String username, @PathVariable String session) {
        return service.getAllInternshipOfferNotAppliedOfStudentBySession(username, session)
                .map(_internshipOffers -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffers))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_UPDATE_INTERNSHIP_APPLICATION)
    public ResponseEntity<InternshipApplication> updateInternshipApplication(@RequestBody InternshipApplication internshipApplication) {
        return service.updateInternshipApplication(internshipApplication)
                .map(_internshipApplication -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipApplication))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
