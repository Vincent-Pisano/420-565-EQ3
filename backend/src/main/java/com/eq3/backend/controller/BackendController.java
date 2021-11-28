package com.eq3.backend.controller;

import com.eq3.backend.model.*;
import com.eq3.backend.service.BackendService;

import com.eq3.backend.utils.UtilsController;
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
import static com.eq3.backend.utils.UtilsController.APPLICATION_JSON_AND_CHARSET_UTF8;
import static com.eq3.backend.utils.UtilsController.APPLICATION_PDF;
import static com.eq3.backend.utils.UtilsController.MULTI_PART_FROM_DATA;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class BackendController {

    private final BackendService service;

    public BackendController(
            BackendService service) {
        this.service = service;
    }

    @PostMapping(value = URL_SAVE_SIGNATURE,
            produces = APPLICATION_JSON_AND_CHARSET_UTF8,
            consumes = { MULTI_PART_FROM_DATA })
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

    @GetMapping(value = URL_DOWNLOAD_CV_DOCUMENT, produces = APPLICATION_PDF)
    public ResponseEntity<InputStreamResource> downloadStudentCVDocument(@PathVariable String idStudent, @PathVariable String idCV){
        return service.downloadStudentCVDocument(idStudent, idCV)
                .map(UtilsController::getDownloadingDocument)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value = URL_GET_SIGNATURE, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<InputStreamResource> getSignature(@PathVariable String username){
        return service.getSignature(username)
                .map(signature -> ResponseEntity
                        .status(HttpStatus.ACCEPTED)
                        .contentType(MediaType.IMAGE_PNG)
                        .body(new InputStreamResource(
                                new ByteArrayInputStream(signature))
                        ))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_DELETE_SIGNATURE_STUDENT)
    public ResponseEntity<Student> deleteSignatureStudent(@PathVariable String username) {
        return service.deleteSignatureStudent(username)
                .map(_student -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_student))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_DELETE_SIGNATURE_MONITOR)
    public ResponseEntity<Monitor> deleteSignatureMonitor(@PathVariable String username) {
        return service.deleteSignatureMonitor(username)
                .map(_monitor -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_monitor))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PostMapping(URL_DELETE_SIGNATURE_INTERNSHIP_MANAGER)
    public ResponseEntity<InternshipManager> deleteSignatureInternshipManager(@PathVariable String username) {
        return service.deleteSignatureInternshipManager(username)
                .map(_internshipManager -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_internshipManager))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }
}
