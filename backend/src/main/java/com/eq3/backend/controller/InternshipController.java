package com.eq3.backend.controller;

import com.eq3.backend.model.Department;
import com.eq3.backend.model.Internship;
import com.eq3.backend.model.InternshipApplication;
import com.eq3.backend.model.InternshipOffer;
import com.eq3.backend.service.InternshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3006")
public class InternshipController {

    private final InternshipService service;

    public InternshipController(InternshipService service) {
        this.service = service;
    }

    @PostMapping("/save/internship")
    public ResponseEntity<Internship> saveInternship(@RequestBody Internship internship) {
        return service.saveInternship(internship)
                .map(_internship -> ResponseEntity.status(HttpStatus.CREATED).body(_internship))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/get/default/engagements")
    public ResponseEntity<Map<String, String>> getEngagements() {
        return Optional.of(Internship.DEFAULT_ENGAGEMENTS)
                .map(_mapDefaultEngagements -> ResponseEntity.status(HttpStatus.CREATED).body(_mapDefaultEngagements))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value = "/get/internship/{idApplication}",
            produces = "application/json;charset=utf8")
    public ResponseEntity<Internship> getInternshipFromInternshipApplication(@PathVariable String idApplication) {
        return service.getInternshipFromInternshipApplication(idApplication)
                .map(_mapDefaultEngagements -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_mapDefaultEngagements))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/sign/internshipContract/monitor/{idInternship}")
    public ResponseEntity<Internship> signInternshipContractByMonitor(@PathVariable String idInternship) {
        return service.signInternshipContractByMonitor(idInternship)
                .map(_internship -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internship))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/sign/internshipContract/student/{idInternship}")
    public ResponseEntity<Internship> signInternshipContractByStudent(@PathVariable String idInternship) {
        return service.signInternshipContractByStudent(idInternship)
                .map(_internship -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internship))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/sign/internshipContract/internshipManager/{idInternship}")
    public ResponseEntity<Internship> signInternshipContractByInternshipManager(@PathVariable String idInternship) {
        return service.signInternshipContractByInternshipManager(idInternship)
                .map(_internship -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internship))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(value = "/deposit/evaluation/student/{idInternship}",
            produces = "application/json;charset=utf8",
            consumes = { "multipart/form-data" })
    public ResponseEntity<Internship> depositStudentEvaluation(@PathVariable("idInternship") String idInternship,
                                                                    @RequestPart(name = "document", required=false) MultipartFile multipartFile) {
        return service.depositStudentEvaluation(idInternship, multipartFile)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(value = "/deposit/evaluation/enterprise/{idInternship}",
            produces = "application/json;charset=utf8",
            consumes = { "multipart/form-data" })
    public ResponseEntity<Internship> depositEnterpriseEvaluation(@PathVariable("idInternship") String idInternship,
                                                               @RequestPart(name = "document", required=false) MultipartFile multipartFile) {
        return service.depositEnterpriseEvaluation(idInternship, multipartFile)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
