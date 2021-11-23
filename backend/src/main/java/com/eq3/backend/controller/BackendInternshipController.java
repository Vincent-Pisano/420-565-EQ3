package com.eq3.backend.controller;

import com.eq3.backend.model.PDFDocument;
import com.eq3.backend.model.Student;
import com.eq3.backend.service.BackendInternshipService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.TreeSet;

import static com.eq3.backend.utils.UtilsController.BackendInternshipControllerUrl.*;
import static com.eq3.backend.utils.UtilsController.CROSS_ORIGIN_ALLOWED;

@RestController
@CrossOrigin(CROSS_ORIGIN_ALLOWED)
public class BackendInternshipController {

    private final BackendInternshipService service;

    public BackendInternshipController(
            BackendInternshipService service) {
        this.service = service;
    }

    @GetMapping(URL_GET_ALL_STUDENTS_WITHOUT_INTERVIEW_DATE_BY_SESSION )
    public ResponseEntity<List<Student>> getAllStudentsWithoutInterviewDate(@PathVariable String session) {
        return service.getAllStudentsWithoutInterviewDate(session)
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_STUDENTS_WITH_APPLICATION_STATUS_WAITING_AND_INTERVIEW_DATE_PASSED_TODAY_BY_SESSION)
    public ResponseEntity<List<Student>> getAllStudentsWithApplicationStatusWaitingAndInterviewDatePassed(@PathVariable String session) {
        return service.getAllStudentsWithApplicationStatusWaitingAndInterviewDatePassed(session)
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_STUDENTS_WITH_INTERNSHIP_BY_SESSION)
    public ResponseEntity<List<Student>> getAllStudentsWithInternship(@PathVariable String session) {
        return service.getAllStudentsWithInternship(session)
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_STUDENTS_WAITING_INTERVIEW_BY_SESSION)
    public ResponseEntity<List<Student>> getAllStudentsWaitingInterview(@PathVariable String session) {
        return service.getAllStudentsWaitingInterview(session)
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_STUDENTS_WITHOUT_STUDENT_EVALUATION_BY_SESSION)
    public ResponseEntity<List<Student>> getAllStudentsWithoutStudentEvaluation(@PathVariable String session){
        return service.getAllStudentsWithoutStudentEvaluation(session)
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_STUDENTS_WITHOUT_ENTERPRISE_EVALUATION_BY_SESSION)
    public ResponseEntity<List<Student>> getAllStudentsWithoutEnterpriseEvaluation(@PathVariable String session){
        return service.getAllStudentsWithoutEnterpriseEvaluation(session)
                .map(_students -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_students))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_NEXT_SESSIONS_INTERNSHIP_OFFERS)
    public ResponseEntity<TreeSet<String>> getAllNextSessionsOfInternshipOffers(){
        return service.getAllNextSessionsOfInternshipOffersValidated()
                .map(_sessions -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_sessions))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_NEXT_SESSIONS_INTERNSHIP_OFFERS_UNVALIDATED)
    public ResponseEntity<TreeSet<String>> getAllNextSessionsOfInternshipOffersUnvalidated(){
        return service.getAllNextSessionsOfInternshipOffersUnvalidated()
                .map(_sessions -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_sessions))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_SESSION_OF_INVALID_INTERNSHIP_OFFERS)
    public ResponseEntity<TreeSet<String>> getAllSessionsOfInvalidInternshipOffers(){
        return service.getAllSessionsOfInvalidInternshipOffers()
                .map(_sessions -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_sessions))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(URL_GET_ALL_SESSION_OF_VALID_INTERNSHIP_OFFERS)
    public ResponseEntity<TreeSet<String>> getAllSessionsOfValidInternshipOffers(){
        return service.getAllSessionsOfValidInternshipOffers()
                .map(_sessions -> ResponseEntity.status(HttpStatus.ACCEPTED).body(_sessions))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value = URL_DOWNLOAD_INTERNSHIP_OFFER_DOCUMENT, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadInternshipOfferDocument(@PathVariable(name = "id") String id){
        return service.downloadInternshipOfferDocument(id)
                .map(this::getDownloadingDocument)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value=URL_DOWNLOAD_EVALUATION_DOCUMENT, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadEvaluationDocument(@PathVariable String typeEvaluation){
        return service.downloadEvaluationDocument(typeEvaluation)
                .map(this::getDownloadingDocument)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value=URL_DOWNLOAD_INTERNSHIP_CONTRACT, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadInternshipContractDocument(@PathVariable String id){
        return service.downloadInternshipContractDocument(id)
                .map(this::getDownloadingDocument)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value=URL_DOWNLOAD_INTERNSHIP_STUDENT_EVALUATION, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadInternshipStudentEvaluationDocument(@PathVariable String idInternship){
        return service.downloadInternshipStudentEvaluationDocument(idInternship)
                .map(this::getDownloadingDocument)
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping(value=URL_DOWNLOAD_INTERNSHIP_ENTERPRISE_EVALUATION, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadInternshipEnterpriseEvaluationDocument(@PathVariable String idInternship){
        return service.downloadInternshipEnterpriseEvaluationDocument(idInternship)
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
