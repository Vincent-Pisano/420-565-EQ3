package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.BackendService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3006")
public class BackendController {

    private final BackendService service;

    public BackendController(BackendService service) {
        this.service = service;
    }

    @PostMapping("/signUp/student")
    public ResponseEntity<Student> signUpStudent(@RequestBody Student student) {
        return service.signUp(student)
                .map(_student -> ResponseEntity.status(HttpStatus.CREATED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/signUp/monitor")
    public ResponseEntity<Monitor> signUpMonitor(@RequestBody Monitor monitor) {
        return service.signUp(monitor)
                .map(_monitor -> ResponseEntity.status(HttpStatus.CREATED).body(_monitor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/signUp/supervisor")
    public ResponseEntity<Supervisor> signUpSupervisor(@RequestBody Supervisor supervisor) {
        return service.signUp(supervisor)
                .map(_supervisor -> ResponseEntity.status(HttpStatus.CREATED).body(_supervisor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/login/student/{username}/{password}")
    public ResponseEntity<Student> loginStudent(@PathVariable String username, @PathVariable String password) {
        return service.loginStudent(username, password)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/students/{department}")
    public ResponseEntity<List<Student>> getAllStudents(@PathVariable Department department) {
        return service.getAllStudents(department)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/login/monitor/{username}/{password}")
    public ResponseEntity<Monitor> loginMonitor(@PathVariable String username, @PathVariable String password) {
        return service.loginMonitor(username, password)
                .map(_monitor -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_monitor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/login/supervisor/{username}/{password}")
    public ResponseEntity<Supervisor> loginSupervisor(@PathVariable String username, @PathVariable String password) {
        return service.loginSupervisor(username, password)
                .map(_supervisor -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_supervisor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/login/internshipManager/{username}/{password}")
    public ResponseEntity<InternshipManager> loginInternshipManager(@PathVariable String username, @PathVariable String password) {
        return service.loginInternshipManager(username, password)
                .map(_internshipManager -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipManager))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(value = "/save/internshipOffer",
            produces = "application/json;charset=utf8",
            consumes = { "multipart/form-data" })
    public ResponseEntity<InternshipOffer> saveInternshipOffer( @RequestPart(name = "internshipOffer") String internshipOfferJson,
                                                                @RequestPart(name = "document", required=false) MultipartFile multipartFile) {

        return service.saveInternshipOffer(internshipOfferJson, multipartFile)
                    .map(internshipOffer1 -> ResponseEntity.status(HttpStatus.CREATED).body(internshipOffer1))
                    .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(value = "/save/CV",
            produces = "application/json;charset=utf8",
            consumes = { "multipart/form-data" })
    public ResponseEntity<Student> saveCV( @RequestPart(name = "student") String student,
                                           @RequestPart(name = "document") MultipartFile document) {

        return service.saveCV(student, document)
                .map(_student -> ResponseEntity.status(HttpStatus.CREATED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value = "/get/internshipOffer/document/{id}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadInternshipOfferDocument(@PathVariable(name = "id") String id){
        return service.downloadInternshipOfferDocument(id);
    }

    @GetMapping("/getAll/internshipOffer/{workField}")
    public ResponseEntity<List<InternshipOffer>> getAllInternshipOfferByWorkField(@PathVariable Department workField) {
        return service.getAllInternshipOfferByWorkField(workField)
                .map(_internshipOffer -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipOffer))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/get/monitor/{username}")
    public ResponseEntity<Monitor> getMonitorByUsername(@PathVariable String username) {
        return service.getMonitorByUsername(username)
                .map(_monitor -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_monitor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
