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
import java.util.TreeSet;

import static com.eq3.backend.utils.UtilsController.BackendControllerUrl.*;
import static com.eq3.backend.utils.UtilsController.CROSS_ORIGIN_ALLOWED;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class BackendController {

    private final BackendService service;

    public BackendController(
            BackendService service) {
        this.service = service;
    }

    @PostMapping(value = URL_SAVE_SIGNATURE,
            produces = "application/json;charset=utf8",
            consumes = { "multipart/form-data" })
    public ResponseEntity<Binary> saveSignature(@PathVariable String username,
                                                @RequestPart(name = "signature") MultipartFile multipartFile) {
        return service.saveSignature(username, multipartFile)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_STUDENTS_FROM_DEPARTMENT_BY_SESSION)
    public ResponseEntity<List<Student>> getAllStudents(@PathVariable Department department, @PathVariable String session) {
        return service.getAllStudentsByDepartment(department, session)
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_SESSIONS_OF_STUDENTS)
    public ResponseEntity<TreeSet<String>> getAllSessionOfStudents() {
        return service.getAllSessionOfStudents()
                .map(_sessions -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_sessions))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_STUDENTS_BY_SESSION)
    public ResponseEntity<List<Student>> getAllStudents(@PathVariable String session) {
        return service.getAllStudentsByDepartment(session)
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_STUDENTS_WITHOUT_SUPERVISOR_FROM_DEPARTMENT_BY_SESSION)
    public ResponseEntity<List<Student>> getAllStudentsWithoutSupervisor(@PathVariable Department department, @PathVariable String session) {
        return service.getAllStudentsWithoutSupervisor(department, session)
                .map(_students ->
                   ResponseEntity.status(HttpStatus.ACCEPTED).body(_students)
                )
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_STUDENTS_WITH_SUPERVISOR_BY_SESSION)
    public ResponseEntity<List<Student>> getAllStudentsWithSupervisor(@PathVariable String idSupervisor, @PathVariable String session) {
        return service.getAllStudentsWithSupervisor(idSupervisor, session)
                .map(_students ->
                        ResponseEntity.status(HttpStatus.ACCEPTED).body(_students)
                )
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_STUDENTS_WITHOUT_CV_BY_SESSION)
    public ResponseEntity<List<Student>> getAllStudentsWithoutCV(@PathVariable String session) {
        return service.getAllStudentsWithoutCV(session)
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_SUPERVISORS_BY_SESSION)
    public ResponseEntity<List<Supervisor>> getAllSupervisorsOfSession(@PathVariable String session){
        return service.getAllSupervisorsOfSession(session)
                .map(_supervisors -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_supervisors))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_SESSIONS_INTERNSHIP_OFFER_MONITOR)
    public ResponseEntity<List<String>> getAllSessionsOfMonitor(@PathVariable String idMonitor){
        return service.getAllSessionsOfMonitor(idMonitor)
                .map(_sessions -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_sessions))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_MONITOR)
    public ResponseEntity<Monitor> getMonitorByUsername(@PathVariable String username) {
        return service.getMonitorByUsername(username)
                .map(_monitor -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_monitor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_ASSIGN_SUPERVISOR)
    public ResponseEntity<Student> assignSupervisorToStudent(@PathVariable String idStudent, @PathVariable String idSupervisor) {
        return service.assignSupervisorToStudent(idStudent, idSupervisor)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value = URL_DOWNLOAD_CV_DOCUMENT, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadStudentCVDocument(@PathVariable String idStudent, @PathVariable String idCV){
        return service.downloadStudentCVDocument(idStudent, idCV)
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
}
