package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.BackendService;

import org.bson.types.Binary;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3006")
public class BackendController {

    private final BackendService service;

    public BackendController(
            BackendService service) {
        this.service = service;
    }

    @PostMapping(value = "/save/signature/{username}",
            produces = "application/json;charset=utf8",
            consumes = { "multipart/form-data" })
    public ResponseEntity<Binary> saveSignature(@PathVariable String username,
                                                @RequestPart(name = "signature") MultipartFile multipartFile) {
        return service.saveSignature(username, multipartFile)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/students/{department}")
    public ResponseEntity<List<Student>> getAllStudents(@PathVariable Department department) {
        return service.getAllStudents(department)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return service.getAllStudents()
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/students/noSupervisor/{department}")
    public ResponseEntity<List<Student>> getAllStudentsWithoutSupervisor(@PathVariable Department department) {
        return service.getAllStudentsWithoutSupervisor(department)
                .map(_students ->
                   ResponseEntity.status(HttpStatus.ACCEPTED).body(_students)
                )
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/students/without/CV")
    public ResponseEntity<List<Student>> getAllStudentsWithoutCV() {
        return service.getAllStudentsWithoutCV()
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/students/without/interviewDate")
    public ResponseEntity<List<Student>> getAllStudentsWithoutInterviewDate() {
        return service.getAllStudentsWithoutInterviewDate()
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/students/waiting/interview")
    public ResponseEntity<List<Student>> getAllStudentsWaitingInterview() {
        return service.getAllStudentsWaitingInterview()
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/getAll/supervisors")
    public ResponseEntity<List<Supervisor>> getAllSupervisors(){
        return service.getAllSupervisors()
                .map(_supervisor -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_supervisor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/get/monitor/{username}")
    public ResponseEntity<Monitor> getMonitorByUsername(@PathVariable String username) {
        return service.getMonitorByUsername(username)
                .map(_monitor -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_monitor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping("/assign/supervisor/{idStudent}/{idSupervisor}")
    public ResponseEntity<Student> assignSupervisorToStudent(@PathVariable String idStudent, @PathVariable String idSupervisor) {
        return service.assignSupervisorToStudent(idStudent, idSupervisor)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value = "/get/internshipOffer/document/{id}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadInternshipOfferDocument(@PathVariable(name = "id") String id){
        return service.downloadInternshipOfferDocument(id)
                .map(this::getDownloadingDocument)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value = "/get/CV/document/{idStudent}/{idCV}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadStudentCVDocument(@PathVariable String idStudent, @PathVariable String idCV){
        return service.downloadStudentCVDocument(idStudent, idCV)
                .map(this::getDownloadingDocument)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value="/get/{typeEvaluation}/evaluation/document", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadEvaluationDocument(@PathVariable String typeEvaluation){
        return service.downloadEvaluationDocument(typeEvaluation)
                .map(this::getDownloadingDocument)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value="/get/internship/document/{id}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadInternshipContractDocument(@PathVariable String id){
        return service.downloadInternshipContractDocument(id)
                .map(this::getDownloadingDocument)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value="/get/internship/student/evaluation/document/{idInternship}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadInternshipStudentEvaluationDocument(@PathVariable String idInternship){
        return service.downloadInternshipStudentEvaluationDocument(idInternship)
                .map(this::getDownloadingDocument)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    private ResponseEntity<InputStreamResource> getDownloadingDocument(PDFDocument PDFDocument) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "inline; filename=" + PDFDocument.getName());

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .headers(headers)
                .contentLength(PDFDocument.getContent().length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(
                        new ByteArrayInputStream(PDFDocument.getContent().getData()))
                );
    }

    @GetMapping("/get/internship/student/evaluation/unvalidated/")
    public ResponseEntity<List<Student>> getAllStudentsWithoutStudentEvaluation(){
        return service.getAllStudentsWithoutStudentEvaluation()
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

}
