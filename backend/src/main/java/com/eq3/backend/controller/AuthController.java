package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.AuthService;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import static com.eq3.backend.utils.UtilsController.AuthControllerUrl.*;
import static com.eq3.backend.utils.UtilsController.CROSS_ORIGIN_ALLOWED;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping(URL_SIGN_UP_STUDENT)
    public ResponseEntity<Student> signUpStudent(@RequestBody Student student) {
        return service.signUp(student)
                .map(_student -> ResponseEntity.status(HttpStatus.CREATED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_SIGN_UP_MONITOR)
    public ResponseEntity<Monitor> signUpMonitor(@RequestBody Monitor monitor) {
        return service.signUp(monitor)
                .map(_monitor -> ResponseEntity.status(HttpStatus.CREATED).body(_monitor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_SIGN_UP_SUPERVISOR)
    public ResponseEntity<Supervisor> signUpSupervisor(@RequestBody Supervisor supervisor) {
        return service.signUp(supervisor)
                .map(_supervisor -> ResponseEntity.status(HttpStatus.CREATED).body(_supervisor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_READMISSION_SUPERVISOR)
    public ResponseEntity<Supervisor> readmissionSupervisor(@PathVariable String id) {
        return service.readmissionSupervisor(id)
                .map(_supervisor -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_supervisor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_READMISSION_STUDENT)
    public ResponseEntity<Student> readmissionStudent(@PathVariable String id) {
        return service.readmissionStudent(id)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_LOGIN_STUDENT)
    public ResponseEntity<Student> loginStudent(@PathVariable String username, @PathVariable String password) {
        return service.loginStudent(username, password)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
    @GetMapping(URL_LOGIN_MONITOR)
    public ResponseEntity<Monitor> loginMonitor(@PathVariable String username, @PathVariable String password) {
        return service.loginMonitor(username, password)
                .map(_monitor -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_monitor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_LOGIN_SUPERVISOR)
    public ResponseEntity<Supervisor> loginSupervisor(@PathVariable String username, @PathVariable String password) {
        return service.loginSupervisor(username, password)
                .map(_supervisor -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_supervisor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_LOGIN_INTERNSHIP_MANAGER)
    public ResponseEntity<InternshipManager> loginInternshipManager(@PathVariable String username, @PathVariable String password) {
        return service.loginInternshipManager(username, password)
                .map(_internshipManager -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipManager))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
