package com.pravdin.democassandra.controller;

import com.pravdin.democassandra.model.InternshipOffer;
import com.pravdin.democassandra.model.Student;
import com.pravdin.democassandra.service.InternshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
