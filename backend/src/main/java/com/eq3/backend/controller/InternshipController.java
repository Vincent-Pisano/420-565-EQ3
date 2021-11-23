package com.eq3.backend.controller;

import com.eq3.backend.model.Internship;
import com.eq3.backend.service.InternshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.eq3.backend.utils.UtilsController.InternshipControllerUrl.*;
import static com.eq3.backend.utils.UtilsController.CROSS_ORIGIN_ALLOWED;
import static com.eq3.backend.utils.UtilsController.APPLICATION_JSON_AND_CHARSET_UTF8;
import static com.eq3.backend.utils.UtilsController.MULTI_PART_FROM_DATA;
import static com.eq3.backend.utils.UtilsController.REQUEST_PART_DOCUMENT;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class InternshipController {

    private final InternshipService service;

    public InternshipController(InternshipService service) {
        this.service = service;
    }

    @PostMapping(URL_SAVE_INTERNSHIP)
    public ResponseEntity<Internship> saveInternship(@RequestBody Internship internship) {
        return service.saveInternship(internship)
                .map(_internship -> ResponseEntity.status(HttpStatus.CREATED).body(_internship))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_DEFAULT_ENGAGEMENTS)
    public ResponseEntity<Map<String, String>> getEngagements() {
        return Optional.of(Internship.DEFAULT_ENGAGEMENTS)
                .map(_mapDefaultEngagements -> ResponseEntity.status(HttpStatus.CREATED).body(_mapDefaultEngagements))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value = URL_GET_INTERNSHIP_BY_INTERNSHIP_APPLICATION,
            produces = APPLICATION_JSON_AND_CHARSET_UTF8)
    public ResponseEntity<Internship> getInternshipFromInternshipApplication(@PathVariable String idApplication) {
        return service.getInternshipFromInternshipApplication(idApplication)
                .map(_mapDefaultEngagements -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_mapDefaultEngagements))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_SIGN_INTERNSHIP_CONTRACT_MONITOR)
    public ResponseEntity<Internship> signInternshipContractByMonitor(@PathVariable String idInternship) {
        return service.signInternshipContractByMonitor(idInternship)
                .map(_internship -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internship))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_SIGN_INTERNSHIP_CONTRACT_STUDENT)
    public ResponseEntity<Internship> signInternshipContractByStudent(@PathVariable String idInternship) {
        return service.signInternshipContractByStudent(idInternship)
                .map(_internship -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internship))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_SIGN_INTERNSHIP_CONTRACT_INTERNSHIP_MANAGER)
    public ResponseEntity<Internship> signInternshipContractByInternshipManager(@PathVariable String idInternship) {
        return service.signInternshipContractByInternshipManager(idInternship)
                .map(_internship -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internship))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(value = URL_DEPOSIT_INTERNSHIP_STUDENT_EVALUATION,
            produces = APPLICATION_JSON_AND_CHARSET_UTF8,
            consumes = { MULTI_PART_FROM_DATA })
    public ResponseEntity<Internship> depositStudentEvaluation(@PathVariable String idInternship,
                                                                    @RequestPart(name = REQUEST_PART_DOCUMENT, required=false) MultipartFile multipartFile) {
        return service.depositStudentEvaluation(idInternship, multipartFile)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(value = URL_DEPOSIT_INTERNSHIP_ENTERPRISE_EVALUATION,
            produces = APPLICATION_JSON_AND_CHARSET_UTF8,
            consumes = { MULTI_PART_FROM_DATA })
    public ResponseEntity<Internship> depositEnterpriseEvaluation(@PathVariable String idInternship,
                                                               @RequestPart(name = REQUEST_PART_DOCUMENT, required=false) MultipartFile multipartFile) {
        return service.depositEnterpriseEvaluation(idInternship, multipartFile)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
