package com.pravdin.democassandra.controller;

import com.pravdin.democassandra.model.Monitor;
import com.pravdin.democassandra.model.Student;
import com.pravdin.democassandra.model.Supervisor;
import com.pravdin.democassandra.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
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

    @GetMapping("/all/student")
    public ResponseEntity<List<Student>> getAllStudent(){
        return service.getAllStudent()
                .map(_student -> ResponseEntity.status(HttpStatus.CREATED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/all/monitor")
    public ResponseEntity<List<Monitor>> getAllMonitor(){
        return service.getAllMonitor()
                .map(_monitor -> ResponseEntity.status(HttpStatus.CREATED).body(_monitor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/all/supervisor")
    public ResponseEntity<List<Supervisor>> getAllSupervisor(){
        return service.getAllSupervisor()
                .map(_supervisor -> ResponseEntity.status(HttpStatus.CREATED).body(_supervisor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
