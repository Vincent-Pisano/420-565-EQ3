package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
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

    public Optional<InternshipOffer> saveInternshipOffer(String internshipOfferJson, MultipartFile multipartFile){
        InternshipOffer internshipOffer = null;
        try {
            internshipOffer = getInternshipOffer(internshipOfferJson, multipartFile);
        } catch (IOException e) {
            logger.error("Couldn't map the string internshipOffer to InternshipOffer.class at " +
                    "saveInternshipOffer in BackendService : " + e.getMessage());
        }
        return internshipOffer == null ? Optional.empty() :
                Optional.of(internshipOfferRepository.save(internshipOffer));
    }

    public InternshipOffer getInternshipOffer(String InternshipOfferJson, MultipartFile multipartFile) throws IOException {
        InternshipOffer internshipOffer = mapInternshipOffer(InternshipOfferJson);
        if (multipartFile != null)
            internshipOffer.setDocument(extractDocument(multipartFile));
        return internshipOffer;
    }

    private InternshipOffer mapInternshipOffer(String internshipOfferJson) throws IOException {
        return new ObjectMapper().readValue(internshipOfferJson, InternshipOffer.class);
    }

    public Optional<Student> saveCV(String id, MultipartFile multipartFile){
        Optional<Student> optionalStudent = studentRepository.findById(id);

        return addToListCV(multipartFile, optionalStudent)
                ? Optional.of(studentRepository.save(optionalStudent.get()))
                : Optional.empty();
    }

    private Boolean addToListCV(MultipartFile multipartFile, Optional<Student> optionalStudent) {
        Boolean isPresent = optionalStudent.isPresent();
        if(isPresent) {
            Student student = optionalStudent.get();
            List<CV> listCV = student.getCVList();
            if (listCV.size() < 10) {
                listCV.add(new CV(extractDocument(multipartFile)));
                student.setCVList(listCV);
            } else {
                isPresent = false;
            }
        }
        return isPresent;
    }

    private Document extractDocument(MultipartFile multipartFile) {
        Document document = new Document();
        document.setName(multipartFile.getOriginalFilename());
        try {
            document.setContent(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
        } catch (IOException e) {
            logger.error("Couldn't extract the document" + multipartFile.getOriginalFilename()
                    + " at extractDocument in BackendService : " + e.getMessage());
        }
        return document;
    }

    public Optional<Student> deleteCV (String idStudent, String idCV) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        return deleteCVFromListCV(optionalStudent, idCV)
                ? Optional.of(studentRepository.save(optionalStudent.get()))
                : Optional.empty();
    }

    private Boolean deleteCVFromListCV(Optional<Student> optionalStudent, String idCV) {
        Boolean isPresent = optionalStudent.isPresent();
        if(isPresent) {
            Student student = optionalStudent.get();
            List<CV> listCV = student.getCVList();
            listCV.removeIf(cv -> cv.getId().equals(idCV));
            student.setCVList(listCV);
        }
        return isPresent;
    }

    public Optional<Student> updateActiveCV (String idStudent, String idCV){
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        return updateActiveCVFromListCV(optionalStudent, idCV)
                ? Optional.of(studentRepository.save(optionalStudent.get()))
                : Optional.empty();
    }

    public Boolean updateActiveCVFromListCV(Optional<Student> optionalStudent, String idCV) {
        Boolean isPresent = optionalStudent.isPresent();
        if (isPresent) {
            Student student = optionalStudent.get();
            List<CV> listCV = student.getCVList();
            for (CV cv : listCV) {
                if (cv.getIsActive())
                    cv.setIsActive(false);
                if (cv.getId().equals(idCV))
                    cv.setIsActive(true);
            }
            student.setCVList(listCV);
        }
        return isPresent;
    }

    public Optional<List<InternshipOffer>> getAllInternshipOfferByWorkField(Department workField) {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByWorkFieldAndIsValidTrue(workField);
        internshipOffers.forEach(internshipOffer -> internshipOffer.setDocument(
                internshipOffer.getDocument() != null ? new Document() : null)
        );
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<Document> downloadInternshipOfferDocument(String id) {
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(id);
        Optional<Document> optionalDocument = Optional.empty();

        if (optionalInternshipOffer.isPresent() && optionalInternshipOffer.get().getDocument() != null)
            optionalDocument = Optional.of(optionalInternshipOffer.get().getDocument());

        return optionalDocument;
    }

    public Optional<Monitor> getMonitorByUsername(String username){
        return monitorRepository.findByUsernameAndIsDisabledFalse(username);
    }
}

