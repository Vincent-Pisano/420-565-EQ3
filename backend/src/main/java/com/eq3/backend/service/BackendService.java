package com.eq3.backend.service;

import com.eq3.backend.model.*;
import com.eq3.backend.repository.*;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.eq3.backend.utils.Utils.*;

@Service
public class BackendService {

    private final StudentRepository studentRepository;
    private final MonitorRepository monitorRepository;
    private final SupervisorRepository supervisorRepository;
    private final InternshipManagerRepository internshipManagerRepository;
    private final MongoTemplate mongoTemplate;

    BackendService(StudentRepository studentRepository,
                   MonitorRepository monitorRepository,
                   SupervisorRepository supervisorRepository,
                   InternshipManagerRepository internshipManagerRepository,
                   MongoTemplate mongoTemplate) {
        this.studentRepository = studentRepository;
        this.monitorRepository = monitorRepository;
        this.supervisorRepository = supervisorRepository;
        this.internshipManagerRepository = internshipManagerRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<Binary> saveSignature(String username, MultipartFile signature) {
        Optional<Binary> optionalBinary = Optional.empty();
        Binary image = null;
        try {
            image = new Binary(BsonBinarySubType.BINARY, signature.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            switch (username.charAt(0)) {
                case 'G' :
                    optionalBinary = getInternshipManagerSignature(username, image);
                    break;

                case 'S' :
                    optionalBinary = getSupervisorSignature(username, image);
                    break;

                case 'M' :
                    optionalBinary = getMonitorSignature(username, image);
                    break;

                case 'E' :
                    optionalBinary = getStudentSignature(username, image);
                    break;
            }
        }
        return optionalBinary;
    }

    private Optional<Binary> getStudentSignature(String username, Binary image) {
        Optional<Binary> optionalBinary = Optional.empty();
        Optional<Student> optionalStudent = studentRepository.findByUsernameAndIsDisabledFalse(username);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setSignature(image);
            studentRepository.save(student);
            optionalBinary = Optional.of(image);
        }
        return optionalBinary;
    }

    private Optional<Binary> getMonitorSignature(String username, Binary image) {
        Optional<Binary> optionalBinary = Optional.empty();
        Optional<Monitor> optionalMonitor = monitorRepository.findByUsernameAndIsDisabledFalse(username);
        if (optionalMonitor.isPresent()) {
            Monitor monitor = optionalMonitor.get();
            monitor.setSignature(image);
            monitorRepository.save(monitor);
            optionalBinary = Optional.of(image);
        }
        return optionalBinary;
    }

    private Optional<Binary> getSupervisorSignature(String username, Binary image) {
        Optional<Binary> optionalBinary = Optional.empty();
        Optional<Supervisor> optionalSupervisor = supervisorRepository.findByUsernameAndIsDisabledFalse(username);
        if (optionalSupervisor.isPresent()) {
            Supervisor supervisor = optionalSupervisor.get();
            supervisor.setSignature(image);
            supervisorRepository.save(supervisor);
            optionalBinary = Optional.of(image);
        }
        return optionalBinary;
    }

    private Optional<Binary> getInternshipManagerSignature(String username, Binary image) {
        Optional<Binary> optionalBinary = Optional.empty();
        Optional<InternshipManager> optionalInternshipManager = internshipManagerRepository.findByUsernameAndIsDisabledFalse(username);
        if (optionalInternshipManager.isPresent()) {
            InternshipManager internshipManager = optionalInternshipManager.get();
            internshipManager.setSignature(image);
            internshipManagerRepository.save(internshipManager);
            optionalBinary = Optional.of(image);
        }
        return optionalBinary;
    }

    public Optional<List<Student>> getAllStudentsByDepartment(Department department, String session) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndDepartmentAndSessionsContains(department, session);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)));
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<TreeSet<String>> getAllSessionOfStudents() {
        TreeSet<String> sessions = new TreeSet<>();
        List<Student> students = studentRepository.findAllByIsDisabledFalse();
        students.forEach(student -> sessions.addAll(student.getSessions()));
        return sessions.isEmpty() ? Optional.empty() : Optional.of((TreeSet<String>) sessions.descendingSet());
    }

    public Optional<List<Student>> getAllStudentsByDepartment(String session) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndSessionsContains(session);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)));
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithoutSupervisor(Department department, String session) {
        List<Student> students = studentRepository.
                findAllByIsDisabledFalseAndDepartmentAndSupervisorMapIsEmptyAndSessionContains(department, session);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)));
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithSupervisor(String idSupervisor, String session) {
        List<Student> students = studentRepository.findAllBySupervisor_IdAndIsDisabledFalse(new ObjectId(idSupervisor), session);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)));
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Student>> getAllStudentsWithoutCV(String session) {
        List<Student> students = studentRepository.findAllByIsDisabledFalseAndCVListIsEmptyAndSessionsContains(session);
        students.forEach(student -> cleanUpStudentCVList(Optional.of(student)));
        return students.isEmpty() ? Optional.empty() : Optional.of(students);
    }

    public Optional<List<Supervisor>> getAllSupervisorsOfSession(String session) {
        List<Supervisor> supervisors = supervisorRepository.findAllByIsDisabledFalseAndSessionsContains(session);
        return supervisors.isEmpty() ? Optional.empty() : Optional.of(supervisors);
    }

    public Optional<List<String>> getAllSessionsOfMonitor(String idMonitor) {
        Query query = new Query(getCriteriaQueryGetAllSessionsOfMonitor(idMonitor));

        List<String> sessions = mongoTemplate
                .getCollection(COLLECTION_NAME_INTERNSHIP_OFFER)
                .distinct(FIELD_SESSION, query.getQueryObject() ,String.class)
                .into(new ArrayList<>());

        Collections.reverse(sessions);
        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions);
    }

    private Criteria getCriteriaQueryGetAllSessionsOfMonitor(String idMonitor) {
        List<Criteria> expression =  new ArrayList<>();
        expression.add(Criteria.where(QUERY_CRITERIA_MONITOR_ID).is(new ObjectId(idMonitor)));
        expression.add(Criteria.where(FIELD_IS_DISABLED).is(false));
        return new Criteria().andOperator(expression.toArray(expression.toArray(new Criteria[0])));
    }

    public Optional<Monitor> getMonitorByUsername(String username) {
        return monitorRepository.findByUsernameAndIsDisabledFalse(username);
    }

    public Optional<Student> assignSupervisorToStudent(String idStudent, String idSupervisor) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        Optional<Supervisor> optionalSupervisor = supervisorRepository.findById(idSupervisor);

        optionalStudent.ifPresent(student -> {
            Map<String, Supervisor> supervisorMap = student.getSupervisorMap();
            optionalSupervisor.ifPresent(supervisor -> supervisorMap.put(getNextSessionFromDate(new Date()), supervisor));
            studentRepository.save(student);
        });
        return cleanUpStudentCVList(optionalStudent);
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

    public Optional<PDFDocument> downloadStudentCVDocument(String idStudent, String idCV) {
        Optional<Student> optionalStudent = studentRepository.findById(idStudent);
        return getCVFromStudent(idCV, optionalStudent);
    }

    public Optional<byte[]> getSignature(String username) {
        Optional<byte[]> signature = Optional.empty();
        switch (username.charAt(0)) {
            case 'G' :
                Optional<InternshipManager> optionalInternshipManager = internshipManagerRepository.findByUsernameAndIsDisabledFalse(username);
                signature = optionalInternshipManager.map(internshipManager -> internshipManager.getSignature().getData());
                break;

            case 'M' :
                Optional<Monitor> optionalMonitor = monitorRepository.findByUsernameAndIsDisabledFalse(username);
                signature = optionalMonitor.map(monitor -> monitor.getSignature().getData());
                break;

            case 'E' :
                Optional<Student> optionalStudent = studentRepository.findByUsernameAndIsDisabledFalse(username);
                signature = optionalStudent.map(student -> student.getSignature().getData());
                break;
        }
        return signature;
    }

    public Optional<Student> deleteSignatureStudent(String username) {
        Optional<Student> optionalStudent = studentRepository.findByUsernameAndIsDisabledFalse(username);
        optionalStudent.ifPresent(student -> {
            student.setSignature(null);
            studentRepository.save(student);
        });
        return optionalStudent;
    }

    public Optional<Monitor> deleteSignatureMonitor(String username) {
        Optional<Monitor> optionalMonitor = monitorRepository.findByUsernameAndIsDisabledFalse(username);
        optionalMonitor.ifPresent(monitor -> {
            monitor.setSignature(null);
            monitorRepository.save(monitor);
        });
        return optionalMonitor;
    }
    public Optional<InternshipManager> deleteSignatureInternshipManager(String username) {
        Optional<InternshipManager> optionalInternshipManager = internshipManagerRepository.findByUsernameAndIsDisabledFalse(username);
        optionalInternshipManager.ifPresent(internshipManager -> {
            internshipManager.setSignature(null);
            internshipManagerRepository.save(internshipManager);
        });
        return optionalInternshipManager;
    }

}
