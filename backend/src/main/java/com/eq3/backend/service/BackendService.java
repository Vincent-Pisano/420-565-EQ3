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
import java.util.List;
import java.util.Optional;

import static com.eq3.backend.utils.Utils.DOCUMENT_EXTENSION;

@Service
public class BackendService {

    private final Logger logger;

    private final StudentRepository studentRepository;
    private final MonitorRepository monitorRepository;
    private final SupervisorRepository supervisorRepository;
    private final InternshipManagerRepository internshipManagerRepository;
    private final InternshipOfferRepository internshipOfferRepository;
    private final EvaluationRepository evaluationRepository;

    BackendService(StudentRepository studentRepository,
                   MonitorRepository monitorRepository,
                   SupervisorRepository supervisorRepository,
                   InternshipManagerRepository internshipManagerRepository,
                   InternshipOfferRepository internshipOfferRepository,
                   EvaluationRepository evaluationRepository
    ) {

        this.logger = LoggerFactory.getLogger(BackendService.class);
        this.studentRepository = studentRepository;
        this.monitorRepository = monitorRepository;
        this.supervisorRepository = supervisorRepository;
        this.internshipManagerRepository = internshipManagerRepository;
        this.internshipOfferRepository = internshipOfferRepository;
        this.evaluationRepository = evaluationRepository;
    }


    public Optional<Student> signUp(Student student) {
        Optional<Student> optionalStudent = Optional.empty();
        try {
            optionalStudent = cleanUpStudentCVList(Optional.of(studentRepository.save(student)));
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in signUp (Student) : " + exception.getMessage());
        }
        return optionalStudent;
    }

    public Optional<Monitor> signUp(Monitor monitor) {
        Optional<Monitor> optionalMonitor = Optional.empty();
        try {
            optionalMonitor = Optional.of(monitorRepository.save(monitor));
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in signUp (Monitor) : " + exception.getMessage());
        }
        return optionalMonitor;
    }

    public Optional<Supervisor> signUp(Supervisor supervisor) {
        Optional<Supervisor> optionalSupervisor = Optional.empty();
        try {
            optionalSupervisor = Optional.of(supervisorRepository.save(supervisor));
        } catch (DuplicateKeyException exception) {
            logger.error("A duplicated key was found in signUp (Supervisor) : " + exception.getMessage());
        }
        return optionalSupervisor;
    }

    public Optional<Student> loginStudent(String username, String password) {
        return cleanUpStudentCVList(studentRepository.findByUsernameAndPasswordAndIsDisabledFalse(username, password));
    }

    public Optional<List<Student>> getAllStudents(Department department) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndDepartment(department);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)).get());
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithoutSupervisor(Department department) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndDepartmentAndSupervisorIsNull(department);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)).get());
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Supervisor>> getAllSupervisors() {
        List<Supervisor> supervisors = supervisorRepository.findAllByIsDisabledFalse();
        return supervisors.isEmpty() ? Optional.empty() : Optional.of(supervisors);
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

    public Optional<InternshipOffer> saveInternshipOffer(String internshipOfferJson, MultipartFile multipartFile) {
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
            internshipOffer.setPDFDocument(extractDocument(multipartFile));
        return internshipOffer;
    }

    private InternshipOffer mapInternshipOffer(String internshipOfferJson) throws IOException {
        return new ObjectMapper().readValue(internshipOfferJson, InternshipOffer.class);
    }

    public Optional<Student> saveCV(String id, MultipartFile multipartFile) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        optionalStudent = addToListCV(multipartFile, optionalStudent)
                ? Optional.of(studentRepository.save(optionalStudent.get()))
                : Optional.empty();

        return cleanUpStudentCVList(optionalStudent);
    }

    private Boolean addToListCV(MultipartFile multipartFile, Optional<Student> optionalStudent) {
        Boolean isPresent = optionalStudent.isPresent();
        if (isPresent) {
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

    private PDFDocument extractDocument(MultipartFile multipartFile) {
        PDFDocument PDFDocument = new PDFDocument();
        PDFDocument.setName(multipartFile.getOriginalFilename());
        try {
            PDFDocument.setContent(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
        } catch (IOException e) {
            logger.error("Couldn't extract the document" + multipartFile.getOriginalFilename()
                    + " at extractDocument in BackendService : " + e.getMessage());
        }
        return PDFDocument;
    }

    public Optional<Student> deleteCV(String idStudent, String idCV) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        optionalStudent = deleteCVFromListCV(optionalStudent, idCV)
                ? Optional.of(studentRepository.save(optionalStudent.get()))
                : Optional.empty();
        return cleanUpStudentCVList(optionalStudent);
    }

    private Boolean deleteCVFromListCV(Optional<Student> optionalStudent, String idCV) {
        Boolean isPresent = optionalStudent.isPresent();
        if (isPresent) {
            Student student = optionalStudent.get();
            List<CV> listCV = student.getCVList();
            listCV.removeIf(cv -> cv.getId().equals(idCV));
            student.setCVList(listCV);
        }
        return isPresent;
    }

    public Optional<Student> updateActiveCV(String idStudent, String idCV) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        optionalStudent = updateActiveCVFromListCV(optionalStudent, idCV)
                ? Optional.of(studentRepository.save(optionalStudent.get()))
                : Optional.empty();
        return cleanUpStudentCVList(optionalStudent);
    }

    public Boolean updateActiveCVFromListCV(Optional<Student> optionalStudent, String idCV) {
        Boolean isPresent = optionalStudent.isPresent();
        if (isPresent) {
            Student student = optionalStudent.get();
            List<CV> listCV = student.getCVList();
            for (CV cv : listCV) {
                updateCVActive(idCV, cv);
            }
            student.setCVList(listCV);
        }
        return isPresent;
    }

    private void updateCVActive(String idCV, CV cv) {
        if (cv.getIsActive()) {
            cv.setIsActive(false);
            if (cv.getStatus() == CV.CVStatus.WAITING)
                cv.setStatus(CV.CVStatus.INVALID);
        }
        if (cv.getId().equals(idCV)) {
            cv.setIsActive(true);
            cv.setStatus(CV.CVStatus.WAITING);
        }
    }

    private Optional<Student> cleanUpStudentCVList(Optional<Student> optionalStudent) {
        optionalStudent.ifPresent(student -> {
                    if (student.getCVList() != null) {
                        student.getCVList().forEach(cv -> {
                            PDFDocument PDFDocument = cv.getPDFDocument();
                            PDFDocument.setContent(null);
                        });
                    }
                }
        );
        return optionalStudent;
    }

    public Optional<List<InternshipOffer>> getAllInternshipOfferByWorkField(Department workField) {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByWorkFieldAndIsValidTrue(workField);
        internshipOffers.forEach(internshipOffer -> internshipOffer.setPDFDocument(
                internshipOffer.getPDFDocument() != null ? new PDFDocument() : null)
        );
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<PDFDocument> downloadInternshipOfferDocument(String id) {
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(id);
        Optional<PDFDocument> optionalDocument = Optional.empty();

        if (optionalInternshipOffer.isPresent() && optionalInternshipOffer.get().getPDFDocument() != null)
            optionalDocument = Optional.of(optionalInternshipOffer.get().getPDFDocument());

        return optionalDocument;
    }

    public Optional<PDFDocument> downloadStudentCVDocument(String idStudent, String idCV) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        return getCVFromStudent(idCV, optionalStudent);
    }

    private Optional<PDFDocument> getCVFromStudent(String idCV, Optional<Student> optionalStudent) {
        Optional<PDFDocument> optionalDocument = Optional.empty();

        if (optionalStudent.isPresent() && optionalStudent.get().getCVList() != null) {
            Student student = optionalStudent.get();
            List<CV> listCV = student.getCVList();
            for (CV cv : listCV) {
                if (cv.getId().equals(idCV))
                    optionalDocument = Optional.of(cv.getPDFDocument());
            }
            student.setCVList(listCV);
        }
        return optionalDocument;
    }

    public Optional<PDFDocument> getEvaluationDocument(String documentName) {
        Optional<PDFDocument> optionalDocument = Optional.empty();
        Optional<Evaluation> optionalEvaluation =
                evaluationRepository.findByName(documentName + DOCUMENT_EXTENSION);
        if (optionalEvaluation.isPresent()) {
            Evaluation evaluation = optionalEvaluation.get();
            optionalDocument = Optional.of(evaluation.getDocument());
        }
        return optionalDocument;
    }

    public Optional<List<InternshipOffer>> getAllUnvalidatedInternshipOffer() {
        List<InternshipOffer> internshipOffers = internshipOfferRepository.findAllByIsValidFalse();
        return internshipOffers.isEmpty() ? Optional.empty() : Optional.of(internshipOffers);
    }

    public Optional<List<Student>> getAllStudentWithCVActiveWaitingValidation() {
        List<Student> studentList = studentRepository.findAllByIsDisabledFalseAndActiveCVWaitingValidation();
        studentList.forEach(student -> cleanUpStudentCVList(Optional.ofNullable(student)));
        return studentList.isEmpty() ? Optional.empty() : Optional.of(studentList);
    }

    public Optional<Monitor> getMonitorByUsername(String username) {
        return monitorRepository.findByUsernameAndIsDisabledFalse(username);
    }

    public Optional<InternshipOffer> validateInternshipOffer(String idOffer) {
        Optional<InternshipOffer> optionalInternshipOffer = internshipOfferRepository.findById(idOffer);
        optionalInternshipOffer.ifPresent(internshipOffer -> {
            internshipOffer.setIsValid(true);
        });
        return optionalInternshipOffer.map(internshipOfferRepository::save);
    }

    public Optional<Student> applyInternshipOffer(String studentUsername, InternshipOffer internshipOffer) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUsernameAndIsDisabledFalse(studentUsername);
        optionalStudent.ifPresent(student -> {
            List<InternshipOffer> internshipOffersList = student.getInternshipOffers();
            if (internshipOffer.getPDFDocument() != null) {
                PDFDocument PDFDocument = internshipOffer.getPDFDocument();
                PDFDocument.setContent(null);
                internshipOffer.setPDFDocument(PDFDocument);
            }
            internshipOffersList.add(internshipOffer);
            student.setInternshipOffers(internshipOffersList);
            studentRepository.save(student);
        });
        return cleanUpStudentCVList(optionalStudent);
    }

    public Optional<Student> assignSupervisorToStudent(String idStudent, String idSupervisor) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        Optional<Supervisor> optionalSupervisor = supervisorRepository.findById(idSupervisor);

        optionalStudent.ifPresent(student -> {
            student.setSupervisor(optionalSupervisor.orElse(null));
            studentRepository.save(student);
        });

        return cleanUpStudentCVList(optionalStudent);

    }

    public Optional<Student> validateCVOfStudent(String idStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);

        optionalStudent.ifPresent(student -> {
            List<CV> CVList = student.getCVList();
            for (CV currentCV : CVList) {
                if (currentCV.getIsActive()) {
                    currentCV.setStatus(CV.CVStatus.VALID);
                }
                break;
            }
            studentRepository.save(student);
        });

        return cleanUpStudentCVList(optionalStudent);
    }
}

