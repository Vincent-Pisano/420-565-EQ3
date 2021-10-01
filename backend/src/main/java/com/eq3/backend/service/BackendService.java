package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BackendService {

    private final Logger logger;

    private final StudentRepository studentRepository;
    private final MonitorRepository monitorRepository;
    private final SupervisorRepository supervisorRepository;
    private final InternshipManagerRepository internshipManagerRepository;
    private final InternshipOfferRepository internshipOfferRepository;

    BackendService(StudentRepository studentRepository,
                   MonitorRepository monitorRepository,
                   SupervisorRepository supervisorRepository,
                   InternshipManagerRepository internshipManagerRepository,
                   InternshipOfferRepository internshipOfferRepository) {
        this.logger = LoggerFactory.getLogger(BackendService.class);
        this.studentRepository = studentRepository;
        this.monitorRepository = monitorRepository;
        this.supervisorRepository = supervisorRepository;
        this.internshipManagerRepository = internshipManagerRepository;
        this.internshipOfferRepository = internshipOfferRepository;
    }


    public Optional<Student> signUp(Student student) {
        Optional<Student> optionalStudent = Optional.empty();
        try {
            optionalStudent = Optional.of(studentRepository.save(student));
        } catch (DuplicateKeyException exception){
            logger.error("A duplicated key was found in signUp (Student) : " + exception.getMessage());
        }
        return optionalStudent;
    }

    public Optional<Monitor> signUp(Monitor monitor) {
        Optional<Monitor> optionalMonitor = Optional.empty();
        try {
            optionalMonitor = Optional.of(monitorRepository.save(monitor));
        } catch (DuplicateKeyException exception){
            logger.error("A duplicated key was found in signUp (Monitor) : " + exception.getMessage());
        }
        return optionalMonitor;
    }

    public Optional<Supervisor> signUp(Supervisor supervisor) {
        Optional<Supervisor> optionalSupervisor = Optional.empty();
        try {
            optionalSupervisor = Optional.of(supervisorRepository.save(supervisor));
        } catch (DuplicateKeyException exception){
            logger.error("A duplicated key was found in signUp (Supervisor) : " + exception.getMessage());
        }
        return optionalSupervisor;
    }

    public Optional<Student> loginStudent(String username, String password) {
        return studentRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password);
    }

    public Optional<List<Student>> getAllStudents(Department department) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndDepartment(department);
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<Supervisor> loginSupervisor(String username, String password) {
        return supervisorRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password);
    }

    public Optional<Monitor> loginMonitor(String username, String password) {
        return monitorRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password);
    }

    public Optional<InternshipManager> loginInternshipManager(String username, String password) {
        return internshipManagerRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password);
    }

    public Optional<InternshipOffer> saveInternshipOffer(String InternshipOfferJson, MultipartFile multipartFile){
        InternshipOffer internshipOffer = null;
        try {
            internshipOffer = mapInternshipOffer(InternshipOfferJson);
            if (multipartFile != null)
                internshipOffer.setDocument(extractDocument(multipartFile));
        } catch (IOException e) {
            logger.error("Couldn't map the string internshipOffer to InternshipOffer.class at " +
                    "saveInternshipOffer in BackendService");
        }
        return internshipOffer == null ? Optional.empty() :
                Optional.of(internshipOfferRepository.save(internshipOffer));
    }

    private InternshipOffer mapInternshipOffer(String InternshipOfferJson) throws IOException {
        return new ObjectMapper().readValue(InternshipOfferJson, InternshipOffer.class);
    }

    private Document extractDocument(MultipartFile multipartFile) throws IOException {
        Document document = new Document();
        document.setName(multipartFile.getOriginalFilename());
        document.setContent(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
        return document;
    }

    /*public Optional<Student> saveCV(String cv, MultipartFile document){
        CV newCV = null;
        try {
            newCV = mapCV(cv, document);
        } catch (IOException e) {
            logger.error("Couldn't map the string student to CV.class at saveCV in BackendService");
        }
        return newCV == null ? Optional.empty() :
                Optional.of(studentRepository.save(newCV));
    }

    private CV mapCV(String cv, MultipartFile document) throws IOException {
        CV newCV;
        ObjectMapper objectMapper = new ObjectMapper();
        newCV = objectMapper.readValue(cv, CV.class);
        if (document != null) {
            Document newDocument = new Document();
            newDocument.setName(document.getOriginalFilename());
            newDocument.setContent(new Binary(BsonBinarySubType.BINARY, document.getBytes()));
            newCV.setDocument(newDocument);
        }
        return newCV;
    }*/

    public Optional<List<InternshipOffer>> getAllInternshipOfferByWorkField(Department workField) {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByWorkFieldAndIsValidTrue(workField);
        internshipOffers.forEach(internshipOffer -> internshipOffer.setDocument(
                internshipOffer.getDocument() != null ? new Document() : null)
        );
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public ResponseEntity<InputStreamResource> downloadInternshipOfferDocument(String id) {
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(id);

        if (optionalInternshipOffer.isEmpty() || optionalInternshipOffer.get().getDocument() == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        Document document = optionalInternshipOffer.get().getDocument();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment; filename=" + document.getName());

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(document.getContent().length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(document.getContent().getData())));
    }
    public Optional<Monitor> getMonitorByUsername(String username){
        return monitorRepository.findByUsernameAndIsDisabledFalse(username);
    }
}

