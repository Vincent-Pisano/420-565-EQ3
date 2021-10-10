package com.eq3.backend.controller;

import com.eq3.backend.model.Student;
import com.eq3.backend.service.CVService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3006")
public class CVController {

    private final CVService service;

    public CVController(CVService service) {
        this.service = service;
    }

    @PostMapping(value = "/save/CV/{idStudent}",
            produces = "application/json;charset=utf8",
            consumes = { "multipart/form-data" })
    public ResponseEntity<Student> saveCV(@RequestPart(name = "document") MultipartFile multipartFile,
                                          @PathVariable(name = "idStudent") String id) {
        return service.saveCV(id, multipartFile)
                .map(_student -> ResponseEntity.status(HttpStatus.CREATED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @DeleteMapping("/delete/CV/{idStudent}/{idCV}")
    public ResponseEntity<Student> deleteCV(@PathVariable String idStudent, @PathVariable String idCV) {
        return service.deleteCV(idStudent, idCV)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/update/ActiveCV/{idStudent}/{idCV}")
    public ResponseEntity<Student> updateActiveCV(@PathVariable String idStudent, @PathVariable String idCV) {
        return service.updateActiveCV(idStudent, idCV)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/student/CVActiveNotValid")
    public ResponseEntity<List<Student>> getAllStudentsWithActiveAndNotValidCV() {
        return service.getAllStudentWithCVActiveWaitingValidation()
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/validate/CV/{idStudent}")
    public ResponseEntity<Student> validateCVOfStudent(@PathVariable String idStudent) {
        return service.validateCVOfStudent(idStudent)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
